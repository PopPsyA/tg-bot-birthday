import command.TelegramCommand
import command.add.AddBirthdayCommand
import command.change.ChangeBirthdayCommand
import command.start.StartCommand
import command.whenmybirthday.WhenMyBirthdayCommand
import common.BirthdayCheckerService
import common.logDebug
import common.logException
import database.TelegramChat
import database.TelegramUser
import handler.UnknownCommandHandler
import handler.UserChatBinderHandler
import me.ivmg.telegram.bot
import me.ivmg.telegram.dispatch
import me.ivmg.telegram.dispatcher.telegramError
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.log4j.PropertyConfigurator
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.FileInputStream
import java.util.*

fun main() {
    initDatabase()
    val telegramCommands = mutableListOf<TelegramCommand>()
    bot {
        token = System.getenv("TOKEN")
        logLevel = HttpLoggingInterceptor.Level.NONE
        dispatch {
            telegramCommands.addAll(listOf(
                StartCommand(),
                AddBirthdayCommand(this),
                WhenMyBirthdayCommand(),
                ChangeBirthdayCommand(this)
            ).onEach { telegramCommand ->
                addHandler(telegramCommand.handlerCommand())
            })
            listOf(UnknownCommandHandler(telegramCommands), UserChatBinderHandler()).forEach { telegramCommandHandler ->
                addHandler(telegramCommandHandler.handler())
            }
            telegramError { _, telegramError ->
                logDebug("Telegram error, response = ${telegramError.getErrorMessage()}")
            }
        }
    }.apply {
        startPolling()
        setMyCommands(
            telegramCommands.map { telegramCommand ->
                telegramCommand.botCommand()
            }
        )
        BirthdayCheckerService.listen(this)
    }
}

private fun initDatabase(){
    PropertyConfigurator.configure(Properties().apply {
        load(FileInputStream("src/log4j.properties"))
    })
    Database.connect(
        url = System.getenv("CLEARDB_DATABASE_URL"),
        user = System.getenv("DB_USER"),
        password = System.getenv("DB_PASSWORD")
    )
    transaction {
        SchemaUtils.createMissingTablesAndColumns(TelegramUser, TelegramChat)
        addLogger(Slf4jSqlDebugLogger)
    }
}