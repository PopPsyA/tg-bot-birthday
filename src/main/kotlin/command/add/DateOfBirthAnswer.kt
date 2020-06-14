package command.add

import common.sendMessage
import database.TelegramUser
import me.ivmg.telegram.HandleUpdate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import questionstatemachine.answer.Answer
import questionstatemachine.answer.Validator

class DateOfBirthAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        update.message?.from?.apply {
            transaction {
                TelegramUser.insertIgnore { userRow ->
                    userRow[id] = this@apply.id
                    userRow[name] = firstName
                    userRow[dateOfBirth] = update.message!!.text.orEmpty()
                    if (lastName != null) userRow[secondName] = lastName
                    if (username != null) userRow[login] = "@$username"
                }
            }
            bot.sendMessage(
                update,
                msg = "Записал ваш день рождения. Ожидайте поздравлений :)"
            )
        }
    }

    override fun validator(): Validator = DateOfBirthValidator()
}