package command.add

import common.sendMessage
import database.TelegramUser
import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import questionstatemachine.answer.Answer

class DateOfBirthAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        saveSelf(bot, update, update.message!!.text.orEmpty())
    }

    override fun validator() = DateOfBirthValidator()


    fun saveSelf(bot: Bot, update: Update, dateOfBirth: String){
        update.message?.from?.apply {
            transaction {
                TelegramUser.insertIgnore { userRow ->
                    userRow[id] = this@apply.id
                    userRow[name] = firstName
                    userRow[TelegramUser.dateOfBirth] = dateOfBirth
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
}