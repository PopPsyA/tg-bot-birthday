package questionstatemachine.answer

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

interface Validator {
    fun isValidAnswer(bot: Bot, update: Update): Boolean
    fun sendValidateMessage(bot: Bot, update: Update)
}