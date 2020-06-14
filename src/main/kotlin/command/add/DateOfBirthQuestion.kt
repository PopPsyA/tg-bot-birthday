package command.add

import handler.TextHandleUpdate
import questionstatemachine.Question
import questionstatemachine.answer.Answer

class DateOfBirthQuestion(private val answer: Answer): Question {

    override fun question() = TextHandleUpdate(
        text = "Когда у вас день рождение?\n" +
                "Формат: день.месяц.год\n" +
                "Например: 04.10.1998"
    )

    override fun answer() = answer
}