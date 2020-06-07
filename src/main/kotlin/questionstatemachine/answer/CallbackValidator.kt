package questionstatemachine.answer

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

class CallbackValidator: Validator {

    override fun isValidAnswer(bot: Bot, update: Update) = update.callbackQuery?.data != null

    override fun sendValidateMessage(bot: Bot, update: Update) {
        bot.sendMessage(
            chatId = update.message!!.chat.id,
            text = "Нажмите одну из кнопок"
        )
    }
}