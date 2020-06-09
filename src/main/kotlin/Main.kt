import command.TelegramCommand
import command.add.BirthdayCommand
import command.start.StartCommand
import command.test.TestCommand
import command.whenmybirthday.WhenMyBirthday
import database.TelegramUser
import handler.UnknownCommandHandler
import me.ivmg.telegram.bot
import me.ivmg.telegram.dispatch
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
                BirthdayCommand(this),
                TestCommand(),
                WhenMyBirthday()
            ).onEach { telegramCommand ->
                addHandler(telegramCommand.handlerCommand())
            })
            listOf(UnknownCommandHandler(telegramCommands)).forEach { telegramCommandHandler ->
                addHandler(telegramCommandHandler.handler())
            }
        }
    }.apply {
        startPolling()
        setMyCommands(
            telegramCommands.map { telegramCommand ->
                telegramCommand.botCommand()
            }
        )
    }
}

private fun initDatabase(){
    PropertyConfigurator.configure(Properties().apply {
        load(FileInputStream("src/log4j.properties"))
    })
    Database.connect(
        url = System.getenv("CLEARDB_DATABASE_URL"),
        user = System.getenv("USER"),
        password = System.getenv("PASSWORD"),
        driver = "com.mysql.cj.jdbc.Driver"
    )
    transaction {
        SchemaUtils.createMissingTablesAndColumns(TelegramUser)
        addLogger(Slf4jSqlDebugLogger)
    }
}