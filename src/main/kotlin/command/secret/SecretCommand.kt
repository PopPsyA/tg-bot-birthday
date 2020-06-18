package command.secret

import command.TelegramCommand
import common.logDebug
import common.userId
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.handlers.CommandHandler
import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.BotCommand
import me.ivmg.telegram.entities.Update

abstract class SecretCommand(secretCommand: String): TelegramCommand {

    private val withSecretCommand = "secret_$secretCommand"

    abstract fun secretCommandRun(bot: Bot, update: Update)

    override fun handlerCommand(): Handler = CommandHandler(withSecretCommand){ bot, update ->
        secretCommandRun(bot, update)
        logDebug("${update.userId()} run secret command $withSecretCommand")
    }

    override fun botCommand(): BotCommand {
        error("$withSecretCommand is secret command!")
    }

}