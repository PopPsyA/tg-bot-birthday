package questionstatemachine.answer

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

class CallbackValidator: Validator {

    override fun isValidAnswer(strAnswer: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendValidateMessage(bot: Bot, update: Update) {
        bot.sendMessage(
            chatId = update.message!!.chat.id,
            text = "Нажмите одну из кнопок"
        )
    }
}