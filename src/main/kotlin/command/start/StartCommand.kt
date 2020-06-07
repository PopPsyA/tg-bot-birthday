package command.start

import command.AbstractTelegramCommand
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.BotCommand

class StartCommand: AbstractTelegramCommand() {

    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        val message = update.message!!
        bot.sendMessage(
            chatId = message.chat.id,
            text = "Привет ${message.from?.firstName.orEmpty()} ${message.from?.lastName.orEmpty()}"
        )
    }

    override fun botCommand() = BotCommand(
        command = "start",
        description =  "Запустить бота"
    )
}