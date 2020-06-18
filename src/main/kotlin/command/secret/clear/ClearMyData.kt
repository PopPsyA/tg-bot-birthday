package command.secret.clear

import command.secret.SecretCommand
import common.userId
import database.TelegramChat
import database.TelegramUser
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class ClearMyData: SecretCommand("clear_my_data") {
    override fun secretCommandRun(bot: Bot, update: Update) {
        transaction {
            TelegramChat.deleteWhere{
                TelegramChat.userId.eq(update.userId())
            }
            TelegramUser.deleteWhere {
                TelegramUser.id.eq(update.userId())
            }
        }
    }
}