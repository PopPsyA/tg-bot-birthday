package command.change

import command.add.DateOfBirthValidator
import common.sendMessage
import common.textMessage
import common.userId
import database.TelegramUser
import me.ivmg.telegram.HandleUpdate
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import questionstatemachine.answer.Answer

class ChangeBirthdayAnswer: Answer {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        transaction {
            TelegramUser.update({ TelegramUser.id.eq(update.userId()) }) {
                it[dateOfBirth] = update.textMessage()
            }
        }
        bot.sendMessage(
            update,
            msg = "Изменил ваш день рождения. Ожидайте поздравлений :)"
        )
    }

    override fun validator() = DateOfBirthValidator()
}