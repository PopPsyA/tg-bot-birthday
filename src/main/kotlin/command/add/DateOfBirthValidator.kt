package command.add

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update
import questionstatemachine.answer.Validator
import java.text.ParseException
import java.text.SimpleDateFormat

class DateOfBirthValidator: Validator {

    private val sdf = SimpleDateFormat("dd.MM.yyyy").apply {
        isLenient = false
    }

    override fun isValidAnswer(strAnswer: String): Boolean {
        return isValidDate(strAnswer)
    }

    override fun sendValidateMessage(bot: Bot, update: Update) {
        bot.sendMessage(
            chatId = update.message!!.chat.id,
            text = "Вы ввели неправильный формат даты"
        )
    }

    private fun isValidDate(date: String): Boolean {
        try {
            sdf.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
        return true
    }
}