package command.add

import database.TelegramUser
import me.ivmg.telegram.HandleUpdate
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.transactions.transaction
import questionstatemachine.answer.Answer
import questionstatemachine.answer.Validator

class DateOfBirthAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        update.message?.from?.apply {
            transaction {
                TelegramUser.replace {
                    it[id] = this@apply.id
                    it[name] = firstName
                    it[secondName] = "$lastName"
                    it[login] = if (username != null) "@${username}" else "null"
                    it[dateOfBirth] = update.message!!.text.orEmpty()
                }
            }
            bot.sendMessage(
                chatId = update.message!!.chat.id,
                text = "Записал ваш день рождения. Ожидайте поздравлений :)"
            )
        }
    }

    override fun validator(): Validator = DateOfBirthValidator()
}