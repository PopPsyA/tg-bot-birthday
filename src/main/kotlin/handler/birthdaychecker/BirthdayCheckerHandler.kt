package handler.birthdaychecker

import common.sendMessage
import handler.TelegramHandler
import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class BirthdayCheckerHandler: Handler(BirthdayHandlerUpdate()), TelegramHandler {

    override fun handler(): Handler = BirthdayCheckerHandler()

    override val groupIdentifier: String = "BirthdayHandler"

    override fun checkUpdate(update: Update): Boolean {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 23
    }

    private class BirthdayHandlerUpdate: HandleUpdate{
        override fun invoke(bot: Bot, update: Update) {
            bot.sendMessage(update, "С днем рожденья!")
            transaction {

            }
        }
    }
}