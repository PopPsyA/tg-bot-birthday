package command.add

import database.TelegramChat
import database.TelegramUser
import me.ivmg.telegram.HandleUpdate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import questionstatemachine.answer.Answer
import questionstatemachine.answer.Validator

class DateOfBirthAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        update.message?.from?.apply {
            val chatId = update.message!!.chat.id
            val tgUserId = this@apply.id
            transaction {
                TelegramUser.insertIgnore { userRow ->
                    userRow[id] = tgUserId
                    userRow[name] = firstName
                    userRow[dateOfBirth] = update.message!!.text.orEmpty()
                    if (lastName != null) userRow[secondName] = lastName
                    if (username != null) userRow[login] = "@$username"
                }
                TelegramChat.replace {
                    it[id] = chatId
                    it[userId] = tgUserId
                    it[chatType] = update.message!!.chat.type
                }
            }
            bot.sendMessage(
                chatId = chatId,
                text = "Записал ваш день рождения. Ожидайте поздравлений :)"
            )
        }
    }

    override fun validator(): Validator = DateOfBirthValidator()
}