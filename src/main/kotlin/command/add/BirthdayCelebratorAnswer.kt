package command.add

import me.ivmg.telegram.HandleUpdate
import questionstatemachine.answer.Answer
import questionstatemachine.answer.EmptyValidator
import questionstatemachine.answer.Validator

class BirthdayCelebratorAnswer: Answer {
    override fun handlerUpdate(): HandleUpdate = { bot, update ->
        println("1: ${update.message!!.text}")
    }

    override fun validator(): Validator = EmptyValidator()
}