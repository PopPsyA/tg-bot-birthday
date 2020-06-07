package handler

import command.TelegramCommand
import me.ivmg.telegram.dispatcher.handlers.TextHandler

class UnknownCommandHandler(private val telegramCommands: List<TelegramCommand>):
    TelegramHandler {

    companion object {
        private const val CHAT_PRIVATE = "private"
    }

    override fun handler() = TextHandler{ bot, update ->
        val userText = update.message?.text.orEmpty()
        if (update.message?.chat?.type == CHAT_PRIVATE && userText.startsWith(prefix = "/") && !telegramCommands
                .map { telegramCommand -> telegramCommand.botCommand().command }
                .contains(userText.drop(1))){
            bot.sendMessage(update.message!!.chat.id, "Неизвестная команда")
        }
    }
}