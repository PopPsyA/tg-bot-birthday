package common

object ChatType {
    const val PRIVATE = "private"
    const val GROUP = "group"
    const val SUPER_GROUP = "supergroup"

    fun isGroup(chatType: String) = chatType == GROUP || chatType == SUPER_GROUP
}