package command.whenmybirthday

import command.AbstractTelegramCommand
import database.TelegramUser
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

import common.sendMessage

class WhenMyBirthday: AbstractTelegramCommand(){

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val dateOfBirth = transaction {
            TelegramUser
                .select{ TelegramUser.id eq update.message!!.from!!.id }
                .firstOrNull()
                ?.get(TelegramUser.dateOfBirth)
        }
        bot.sendMessage(update,
            if (dateOfBirth != null) "Ваш день рождения $dateOfBirth"
            else "Укажите ваш день рождение командой /add"
        )
    }

    override fun botCommand() = BotCommand("when_my_birthday", "Когда у меня день рождение?")
}