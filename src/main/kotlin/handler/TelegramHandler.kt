package handler

import me.ivmg.telegram.dispatcher.handlers.Handler

interface TelegramHandler {
    fun handler(): Handler
}