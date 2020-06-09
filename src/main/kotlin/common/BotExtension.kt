package common

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

fun Bot.sendMessage(update: Update, msg: String){
    sendMessage(
        chatId = update.message!!.chat.id,
        text = msg
    )
}