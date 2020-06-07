package command.test

import command.AbstractTelegramCommand
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand

class TestCommand: AbstractTelegramCommand() {
    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        bot.sendMessage(
            chatId = update.message!!.chat.id,
            text = "Test command"
        )
    }

    override fun botCommand() = BotCommand(command = "test", description = "Тестовая команда")
}