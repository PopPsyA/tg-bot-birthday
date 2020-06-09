package database

import org.jetbrains.exposed.sql.Table

object TelegramUser: Table("telegram_users") {
    val id = long("tg_id")
    val name = text("tg_name")
    val secondName = text("tg_second_name")
    val login = text("tg_login")
    val dateOfBirth = varchar("tg_date_of_birth", 10).nullable()

    override val primaryKey = PrimaryKey(id)
}