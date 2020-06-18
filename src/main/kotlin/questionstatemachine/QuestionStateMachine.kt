package questionstatemachine

import common.textMessage
import common.userId
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.dispatcher.handlers.TextHandler
import me.ivmg.telegram.entities.Update

class QuestionStateMachine(private val dispatcher: Dispatcher,
                           private val questions: List<Question>) {

    private var textHandler: TextHandler? = null
    private val knownUserMap = mutableMapOf<Long, IndexUpdate>()

    fun start(parentBot: Bot, parentUpdate: Update) {
        knownUserMap.putIfAbsent(parentUpdate.userId(), IndexUpdate(0, parentUpdate))
        if (textHandler == null) {
            askQuestion(parentBot, parentUpdate)
            dispatcher.addHandler(TextHandler{ bot, update ->
                if (knownUserMap.containsKey(update.userId()) && knownUserMap.getValue(update.userId()).update.userId() == update.userId()){
                    askQuestion(bot, update)
                }
            }.also { textHandler = it })
        }
    }

    private fun askQuestion(bot: Bot, update: Update){
        val questionIndex = knownUserMap.getValue(update.userId()).index
        if (questionIndex > 0){
            answer(bot, update)
        }
        if (questionIndex != questions.size) {
            questions[questionIndex]
                .question()
                .invoke(bot, update)
            knownUserMap[update.userId()] = IndexUpdate(questionIndex + 1, update)
        } else {
            knownUserMap.remove(update.userId())
        }
        if (knownUserMap.isEmpty()){
            dispatcher.removeHandler(textHandler!!)
            textHandler = null
        }
    }

    private fun answer(bot: Bot, update: Update) {
        val question = questions[knownUserMap.getValue(update.userId()).index - 1]
        val answerValidator = question.answer().validator()
        if (!answerValidator.isValidAnswer(update.textMessage().orEmpty())){
            answerValidator.sendValidateMessage(bot, update)
        } else {
            question
                .answer()
                .handlerUpdate()
                .invoke(bot, update)
        }
    }

    data class IndexUpdate(var index: Int, val update: Update)
}