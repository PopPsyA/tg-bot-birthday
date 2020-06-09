package command.start

import command.AbstractTelegramCommand
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand

import common.sendMessage
import logDebug

class StartCommand: AbstractTelegramCommand() {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val message = update.message!!
        bot.sendMessage(
            update,
            msg = "Привет ${message.from?.firstName.orEmpty()} ${message.from?.lastName.orEmpty()}"
        )
        logDebug("Start with languageCode = ${message.from?.languageCode}")
    }

    override fun botCommand() = BotCommand(
        command = "start",
        description =  "Запустить бота"
    )
}