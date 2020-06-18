package database.model

import database.TelegramUser
import org.jetbrains.exposed.sql.ResultRow

data class TelegramUserModel(
    val id: Long,
    val name: String,
    val secondName: String,
    val login: String,
    val dateOfBirth: String
){
    companion object{
        fun fromTelegramUser(resultRow: ResultRow) = TelegramUserModel(
            id = resultRow[TelegramUser.id],
            name = resultRow[TelegramUser.name],
            secondName = resultRow[TelegramUser.secondName].orEmpty(),
            login = resultRow[TelegramUser.login].orEmpty(),
            dateOfBirth = resultRow[TelegramUser.dateOfBirth].orEmpty()
        )
    }

    fun availableName(): String{
        return if (login.isNotEmpty()) login
        else if (name.isNotEmpty() && secondName.isNotEmpty()) "$name $secondName"
        else name
    }
}