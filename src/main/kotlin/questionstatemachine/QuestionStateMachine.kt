package questionstatemachine

import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.dispatcher.handlers.TextHandler
import me.ivmg.telegram.entities.Update

class QuestionStateMachine(private val dispatcher: Dispatcher,
                           private val questions: List<Question>) {

    private var textHandler: TextHandler? = null
    private var questionIndex = 0

    fun start(parentBot: Bot, parentUpdate: Update) {
        if (textHandler == null) {
            askQuestion(parentBot, parentUpdate)
            dispatcher.addHandler(TextHandler{ bot, update ->
                askQuestion(bot, update)
            }.also { textHandler = it })
        }
    }

    private fun askQuestion(bot: Bot, update: Update){
        if (questionIndex > 0){
            answer(bot, update)
        }
        if (questionIndex != questions.size) {
            questions[questionIndex]
                .question()
                .invoke(bot, update)
            questionIndex++
        } else {
            dispatcher.removeHandler(textHandler!!)
            questionIndex = 0
            textHandler = null
        }
    }

    private fun answer(bot: Bot, update: Update) {
        val question = questions[questionIndex - 1]
        val answerValidator = question.answer().validator()
        if (!answerValidator.isValidAnswer(bot, update)){
            answerValidator.sendValidateMessage(bot, update)
            questionIndex--
        } else {
            question
                .answer()
                .handlerUpdate()
                .invoke(bot, update)
        }
    }
}