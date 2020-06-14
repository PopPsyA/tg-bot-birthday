package common

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

fun Bot.sendMessage(update: Update, msg: String){
    sendMessage(
        chatId = update.chatId(),
        text = msg
    )
}

fun Update.userId() = message!!.from!!.id
fun Update.chatId() = message!!.chat.id
fun Update.textMessage() = message!!.text
fun Update.chatType() = message!!.chat.type