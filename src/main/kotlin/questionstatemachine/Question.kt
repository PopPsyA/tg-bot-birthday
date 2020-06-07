package questionstatemachine

import me.ivmg.telegram.HandleUpdate
import questionstatemachine.answer.Answer

interface Question{
    fun question(): HandleUpdate
    fun answer(): Answer
}