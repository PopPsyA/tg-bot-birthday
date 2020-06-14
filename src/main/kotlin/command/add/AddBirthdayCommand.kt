package command.add

import command.AbstractTelegramCommand
import command.whenmybirthday.userBirthday
import common.sendMessage
import common.userId
import questionstatemachine.QuestionStateMachine
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.entities.BotCommand

class AddBirthdayCommand(dispatcher: Dispatcher) : AbstractTelegramCommand() {

    private val questionStateMachine = QuestionStateMachine(
        dispatcher,
        questions = listOf(
            DateOfBirthQuestion(answer = DateOfBirthAnswer())
        )
    )

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val userBirthday = userBirthday(update.userId())
        if (userBirthday != null){
            bot.sendMessage(
                update,
                msg = "Вы уже ввели свой день рождения - $userBirthday"
            )
        } else {
            questionStateMachine.start(bot, update)
        }
    }

    override fun botCommand() = BotCommand(command = "add", description = "Добавить день рождение")

}