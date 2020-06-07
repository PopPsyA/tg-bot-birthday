package command.add

import command.AbstractTelegramCommand
import handler.TextHandleUpdate
import questionstatemachine.QuestionStateMachine
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.entities.BotCommand
import questionstatemachine.SimpleQuestion

class BirthdayCommand(dispatcher: Dispatcher) : AbstractTelegramCommand() {

    private val questionStateMachine = QuestionStateMachine(
        dispatcher,
        questions = listOf(
            SimpleQuestion(
                question = TextHandleUpdate(
                    text = "Когда у вас день рождение?\n" +
                            "Формат: день.месяц.год\n" +
                            "Например: 04.10.1998"
                ),
                answer = DateOfBirthAnswer()
            )
        )
    )

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        println("handlerUpdate message ${update.message?.text}")
        questionStateMachine.start(bot, update)
    }

    override fun botCommand() = BotCommand(command = "add", description = "Добавить день рождение")

}