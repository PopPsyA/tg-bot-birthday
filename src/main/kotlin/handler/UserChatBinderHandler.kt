package handler

import common.*
import database.TelegramChat
import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.Update
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserChatBinderHandler: AnyHandler(UserChatBinderHandleUpdate()) {

    private class UserChatBinderHandleUpdate: HandleUpdate{
        override fun invoke(bot: Bot, update: Update) {
            try{
                transaction {
                    if (TelegramChat
                            .select { TelegramChat.userId.eq(update.userId()).and(TelegramChat.id.eq(update.chatId())) }
                            .firstOrNull() == null){
                        TelegramChat.insertIgnore {
                            it[id] = update.chatId()
                            it[userId] = update.userId()
                            it[chatType] = update.message!!.chat.type
                        }
                    }
                }
            } catch (e: Exception) {
                logException("Exception on UserChatBinderHandleUpdate", e)
            }
        }
    }
}