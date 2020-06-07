package command.stop

import command.TelegramCommand
import me.ivmg.telegram.dispatcher.handlers.CommandHandler
import me.ivmg.telegram.entities.BotCommand

class StopCommand: TelegramCommand {

    override fun handlerCommand() = CommandHandler("stop"){ bot, update ->
        bot.sendMessage(chatId = update.message!!.chat.id, text = "Bye!")
        bot.stopPolling()
    }

    override fun botCommand(): BotCommand {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}