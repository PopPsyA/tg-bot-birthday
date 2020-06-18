package command.add

import common.sendMessage
import common.textMessage
import database.TelegramUser
import database.model.TelegramUserModel.Companion.fromTelegramUser
import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import questionstatemachine.answer.Answer

class DateOfBirthAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        saveSelf(bot, update, update.textMessage())
    }

    override fun validator() = DateOfBirthValidator()

    fun saveSelf(bot: Bot, update: Update, dateOfBirth: String){
        update.message?.from?.apply {
            transaction {
                val telegramUserModel = fromTelegramUser(
                    TelegramUser.insertIgnore { userRow ->
                        userRow[id] = this@apply.id
                        userRow[name] = firstName
                        userRow[TelegramUser.dateOfBirth] = dateOfBirth
                        if (lastName != null) userRow[secondName] = lastName
                        if (username != null) userRow[login] = "@$username"
                    }.resultedValues?.firstOrNull { it[TelegramUser.id] == this@apply.id }!!
                )
                bot.sendMessage(
                    update,
                    msg = "Записал день рождения ${telegramUserModel.availableName()}. Ожидайте поздравлений :)"
                )
            }

        }
    }
}