package command

import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.BotCommand


interface TelegramCommand {
    fun handlerCommand(): Handler
    fun botCommand(): BotCommand
}