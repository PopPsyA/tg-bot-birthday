package command.whenmybirthday

import command.AbstractTelegramCommand
import database.TelegramUser
import logDebug
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class WhenMyBirthday: AbstractTelegramCommand(){

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val dateOfBirth = transaction {
            TelegramUser
                .selectAll()
                .forEach {
                    logDebug("Login ${it[TelegramUser.login]}, ${it[TelegramUser.name]}, ${it[TelegramUser.id]}, ${it[TelegramUser.dateOfBirth]}")
                }
        }
        bot.sendMessage(
            chatId = update.message!!.chat.id,
            text = "Вы родились $dateOfBirth"
        )
    }

    override fun botCommand() = BotCommand("when_my_birthday", "Когда у меня день рождение?")
}