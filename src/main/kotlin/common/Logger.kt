package common

import com.mysql.cj.log.Slf4JLogger

private val logger = Slf4JLogger(Slf4JLogger.LOGGER_INSTANCE_NAME)

fun logDebug(any: Any?){
    logger.logDebug(any)
}

fun logException(msg: Any, thrown: Throwable){
    logger.logError(msg, thrown)
}
