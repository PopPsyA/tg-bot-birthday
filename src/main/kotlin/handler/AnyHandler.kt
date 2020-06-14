package handler

import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.handlers.Handler
import me.ivmg.telegram.entities.Update

abstract class AnyHandler(handleUpdate: HandleUpdate): Handler(handleUpdate), TelegramHandler {
    override val groupIdentifier = "AnyHandler"
    override fun checkUpdate(update: Update) = true
    override fun handler(): Handler {
        return this
    }
}