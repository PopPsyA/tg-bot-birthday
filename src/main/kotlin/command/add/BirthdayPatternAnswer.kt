package command.add

import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.InlineKeyboardButton
import questionstatemachine.answer.Answer
import questionstatemachine.answer.CallbackValidator
import questionstatemachine.answer.Validator

class BirthdayPatternAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        bot.sendMessage(
            chatId = update.callbackQuery?.message?.chat?.id ?: 0,
            text = "Поздравляю, вы выбрали ${update.callbackQuery?.data}"
        )
        println("Callback data = " + update.callbackQuery?.data)
    }

    override fun validator(): Validator = CallbackValidator()

    companion object {
        fun buttons(): List<List<InlineKeyboardButton>> {
            return listOf(
                listOf(InlineKeyboardButton(text = "Test Inline Button", callbackData = "first")),
                listOf(InlineKeyboardButton(text = "Show alert", callbackData = "second"))
            )
        }
    }
}