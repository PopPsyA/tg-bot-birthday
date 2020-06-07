package questionstatemachine

import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.Dispatcher
import me.ivmg.telegram.dispatcher.handlers.CallbackQueryHandler
import me.ivmg.telegram.dispatcher.handlers.Handler
import questionstatemachine.answer.Answer

class CallbackQuestion(private val question: Question, private val dispatcher: Dispatcher) : Question {

    private val callbackQueryHandler: Handler by lazy {
        CallbackQueryHandler(
            handler = question
                .answer()
                .handlerUpdate()
        )
    }

    override fun question(): HandleUpdate {
        dispatcher.addHandler(callbackQueryHandler)
        return question.question()
    }

    override fun answer(): Answer {
        dispatcher.removeHandler(callbackQueryHandler)
        return question.answer()
    }
}