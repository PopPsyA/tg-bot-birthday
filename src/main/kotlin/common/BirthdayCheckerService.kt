package common

import database.TelegramChat
import database.TelegramUser
import me.ivmg.telegram.Bot
import org.jetbrains.exposed.sql.Join
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer

object BirthdayCheckerService {

    private val sdf = SimpleDateFormat("dd.MM")

    fun listen(bot: Bot) {
        fixedRateTimer(daemon = true, period = TimeUnit.HOURS.toMillis(24)) {
            transaction {
                Join(TelegramUser, TelegramChat)
                    .select { TelegramUser.id.eq(TelegramChat.userId) }
                    .forEach { joinedChatUseRow ->
                        if (sdf.format(sdf.parse(joinedChatUseRow[TelegramUser.dateOfBirth])) == sdf.format(Date())) {
                            val birthdayTemplateBuilder = StringBuilder()
                            if (ChatType.isGroup(joinedChatUseRow[TelegramChat.chatType])) {
                                birthdayTemplateBuilder.append("Сегодня празднует день рождение, ")
                                when {
                                    joinedChatUseRow[TelegramUser.login]?.isNotEmpty() == true -> {
                                        birthdayTemplateBuilder.append(joinedChatUseRow[TelegramUser.login])
                                    }
                                    joinedChatUseRow[TelegramUser.secondName]?.isNotEmpty() == true -> {
                                        birthdayTemplateBuilder.append("${joinedChatUseRow[TelegramUser.name]} ${joinedChatUseRow[TelegramUser.secondName]}")
                                    }
                                    else -> {
                                        birthdayTemplateBuilder.append(joinedChatUseRow[TelegramUser.name])
                                    }
                                }
                            }
                            birthdayTemplateBuilder.append("\n\n${randomBirthdayTemplate()}")
                            bot.sendMessage(
                                chatId = joinedChatUseRow[TelegramChat.id],
                                text = birthdayTemplateBuilder.toString()
                            )
                        }
                    }
            }
        }
    }
}