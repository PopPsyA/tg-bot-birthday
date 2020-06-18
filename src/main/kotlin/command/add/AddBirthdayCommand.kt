package command.add

import command.TelegramCommand
import command.whenmybirthday.userBirthday
import common.sendMessage
import common.userId
import questionstatemachine.QuestionStateMachine
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.dispatcher.handlers.CommandHandler
import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.BotCommand

class AddBirthdayCommand(dispatcher: Dispatcher) : TelegramCommand {

    private val questionStateMachine = QuestionStateMachine(
        dispatcher,
        questions = listOf(
            DateOfBirthQuestion(answer = DateOfBirthAnswer())
        )
    )

    override fun handlerCommand(): Handler = CommandHandler(botCommand().command){ bot, update, arguments ->
        val userBirthday = userBirthday(update.userId())
        if (userBirthday != null){
            bot.sendMessage(
                update,
                msg = "Вы уже ввели свой день рождения - $userBirthday"
            )
        } else {
            if (arguments.isEmpty()){
                questionStateMachine.start(bot, update)
            } else {
                val dateOfBirthAnswer = DateOfBirthAnswer()
                val dateOfBirthValidator = dateOfBirthAnswer.validator()
                if (dateOfBirthValidator.isValidAnswer(arguments[0])){
                    dateOfBirthAnswer.saveSelf(bot, update, arguments[0])
                } else {
                    dateOfBirthValidator.sendValidateMessage(bot, update)
                }
            }
        }
    }

    override fun botCommand() = BotCommand(command = "add", description = "Добавить день рождение")

}