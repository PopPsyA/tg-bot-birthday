package questionstatemachine.answer

import me.ivmg.telegram.HandleUpdate

interface Answer{
    fun handlerUpdate(): HandleUpdate
    fun validator(): Validator
}