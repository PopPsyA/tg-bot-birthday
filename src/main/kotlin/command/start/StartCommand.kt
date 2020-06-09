package command.start

import command.AbstractTelegramCommand
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand

import common.sendMessage
import logDebug
import java.util.*

class StartCommand: AbstractTelegramCommand() {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val message = update.message!!
        bot.sendMessage(
            update,
            "Привет ${message.from?.firstName.orEmpty()} ${message.from?.lastName.orEmpty()}"
        )
        logDebug("Current hour ${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}")
    }

    override fun botCommand() = BotCommand(
        command = "start",
        description =  "Запустить бота"
    )
}