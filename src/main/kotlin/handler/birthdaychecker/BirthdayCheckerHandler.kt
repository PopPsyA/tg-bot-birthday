package handler.birthdaychecker

import common.birthdayTemplates
import common.randomBirthdayTemplate
import common.sendMessage
import database.TelegramUser
import handler.TelegramHandler
import logDebug
import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class BirthdayCheckerHandler: Handler(BirthdayHandlerUpdate()), TelegramHandler {

    override fun handler(): Handler = BirthdayCheckerHandler()

    override val groupIdentifier: String = "BirthdayHandler"

    override fun checkUpdate(update: Update): Boolean {
        logDebug("checkupdate ${update.message?.chat?.id}")
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
                        bot.sendMessage(update, "С днем рожденья!")
                        it[TelegramUser.dateOfBirth]?.let { dateOfBirth ->
                            val parsedDateOfBirth = LocalDate.parse(dateOfBirth, dtf)
                            val currentDate = LocalDate.now()
                            if (currentDate.monthValue == parsedDateOfBirth.monthValue
                                && currentDate.dayOfMonth == parsedDateOfBirth.dayOfMonth){
                                bot.sendMessage(update,
                                    randomBirthdayTemplate()
                                )
                                bot.sendMessage(chatId = it[TelegramUser.id], text = randomBirthdayTemplate())
                            }
                        }
                    }
            }
        }
    }
}