package command.change

import command.AbstractTelegramCommand
import command.add.DateOfBirthQuestion
import command.whenmybirthday.userBirthday
import common.userId
import common.sendMessage
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.entities.BotCommand
import questionstatemachine.QuestionStateMachine

class ChangeBirthdayCommand(dispatcher: Dispatcher): AbstractTelegramCommand(){

    private val questionStateMachine = QuestionStateMachine(
        dispatcher,
        questions = listOf(
            DateOfBirthQuestion(answer = ChangeBirthdayAnswer())
        )
    )

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        if (userBirthday(update.userId()) == null){
            bot.sendMessage(update, msg = "Чтобы изменить день рождения, вы должны указать его с помощью команды /add")
        } else {
            questionStateMachine.start(bot, update)
        }
    }

    override fun botCommand() = BotCommand(command = "change", description = "Изменить дату рождения")

}