package command

import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.handlers.CommandHandler
import me.ivmg.telegram.dispatcher.handlers.Handler

abstract class AbstractTelegramCommand: TelegramCommand {

    override fun handlerCommand(): Handler = CommandHandler(command = botCommand().command, handler = handlerUpdate())

    abstract fun handlerUpdate(): HandleUpdate
}