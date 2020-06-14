package handler.birthdaychecker

import database.TelegramUser
import handler.TelegramHandler
import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BirthdayCheckerHandler: Handler(BirthdayHandlerUpdate()), TelegramHandler {

    override fun handler(): Handler = BirthdayCheckerHandler()

    override val groupIdentifier: String = "BirthdayHandler"

    override fun checkUpdate(update: Update): Boolean {
        return everyDay()
    }

    private fun everyDay() = true

    private class BirthdayHandlerUpdate: HandleUpdate{
        private val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        override fun invoke(bot: Bot, update: Update) {
            transaction {
                TelegramUser
                    .selectAll()
                    .forEach {
                        it[TelegramUser.dateOfBirth]?.let { dateOfBirth ->
                            val parsedDateOfBirth = LocalDate.parse(dateOfBirth, dtf)
                            val currentDate = LocalDate.now()
                            if (currentDate.monthValue == parsedDateOfBirth.monthValue
                                && currentDate.dayOfMonth == parsedDateOfBirth.dayOfMonth){
                            }
                        }
                    }
            }
        }
    }
}