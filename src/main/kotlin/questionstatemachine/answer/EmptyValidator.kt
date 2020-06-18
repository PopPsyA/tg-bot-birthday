package questionstatemachine.answer

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

class EmptyValidator: Validator {
    override fun isValidAnswer(strAnswer: String): Boolean {
        return true
    }

    override fun sendValidateMessage(bot: Bot, update: Update) {
        // empty
    }
}