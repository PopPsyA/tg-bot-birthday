package database

import org.jetbrains.exposed.sql.Table

object TelegramChat: Table("telegram_chats") {
    val id = long("tg_chat_id")
    val userId = reference("tg_user_id", TelegramUser.id).nullable()
    val chatType = text("tg_chat_type")
    override val primaryKey = PrimaryKey(
        long("tg_primary_auto_chat_id").autoIncrement()
    )
}