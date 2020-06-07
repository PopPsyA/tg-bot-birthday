package handler

import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.ReplyMarkup
import me.ivmg.telegram.entities.Update

class TextHandleUpdate(private val text: String,
                       private val builder: Builder = Builder()
): HandleUpdate {
    override fun invoke(bot: Bot, update: Update) {
        bot.sendMessage(
            chatId = update.message!!.chat.id,
            text = text,
            replyMarkup = builder.replyMarkup
        )
    }

    class Builder(
        val replyMarkup: ReplyMarkup? = null
    )
}