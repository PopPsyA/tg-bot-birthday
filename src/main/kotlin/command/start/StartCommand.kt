package command.start

import command.AbstractTelegramCommand
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand

import common.sendMessage

class StartCommand: AbstractTelegramCommand() {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val message = update.message!!
        bot.sendMessage(
            update,
            "Привет ${message.from?.firstName.orEmpty()} ${message.from?.lastName.orEmpty()}"
        )
    }

    override fun botCommand() = BotCommand(
        command = "start",
        description =  "Запустить бота"
    )
}