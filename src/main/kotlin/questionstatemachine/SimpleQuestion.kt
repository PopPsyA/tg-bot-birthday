package questionstatemachine

import me.ivmg.telegram.HandleUpdate
import questionstatemachine.answer.Answer

class SimpleQuestion(private val question: HandleUpdate,
                     private val answer: Answer): Question {
    override fun question() = question

    override fun answer() = answer
}