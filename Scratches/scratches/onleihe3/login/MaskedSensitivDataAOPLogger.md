# Sensible Daten in Logger Anonymisieren

# Fazit
Diese nachfolgenden Ideen stammen von Copilot
Die beste Idee kommt on https://www.baeldung.com/logback-mask-sensitive-data
Hier wird ein PatternLayout verwendet

## 1. Verwendung von Maskierungsbibliotheken
   Es gibt Bibliotheken, die speziell für das Maskieren von sensiblen Daten in Logs entwickelt wurden. Eine solche Bibliothek ist logback-mask.
   Beispiel:
````kotlin
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.spi.FilterReply
import ch.qos.logback.core.filter.Filter
import org.slf4j.LoggerFactory

class SensitiveDataFilter : Filter<ILoggingEvent>() {
    private val sensitiveDataPattern = Regex("(password=\\w+|clientSecret=\\w+|privateKey=\\w+)")

    override fun decide(event: ILoggingEvent): FilterReply {
        val sanitizedMessage = sensitiveDataPattern.replace(event.formattedMessage) { matchResult ->
            matchResult.value.split("=").first() + "=****"
        }
        event.message = sanitizedMessage
        return FilterReply.NEUTRAL
    }
}

fun configureLogging() {
    val context = LoggerFactory.getILoggerFactory() as LoggerContext
    val encoder = PatternLayoutEncoder()
    encoder.context = context
    encoder.pattern = "%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n"
    encoder.start()

    val appender = ConsoleAppender<ILoggingEvent>()
    appender.context = context
    appender.encoder = encoder
    appender.start()

    val rootLogger = context.getLogger("ROOT")
    rootLogger.addAppender(appender)
    rootLogger.addFilter(SensitiveDataFilter())
}
````

## 2. Verwendung von AOP (Aspect-Oriented Programming)

Mit AOP können Sie Aspekte definieren, die vor oder nach bestimmten Methoden ausgeführt werden, um sensible Daten zu maskieren.
Beispiel:

````kotlin
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.JoinPoint
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val sensitiveDataPattern = Regex("(password=\\w+|clientSecret=\\w+|privateKey=\\w+)")

    @Before("execution(* net.avgl.ekz.onleihe.userapplication.user_authentication_service.service.AuthenticationService.*(..))")
    fun logBefore(joinPoint: JoinPoint) {
        val args = joinPoint.args.map { arg ->
            sensitiveDataPattern.replace(arg.toString()) { matchResult ->
                matchResult.value.split("=").first() + "=****"
            }
        }
        logger.info("Method: ${joinPoint.signature.name}, Args: $args")
    }
}
````


# 3. Anpassung der Logging-Konfiguration
In application.properties oder logback.xml können Sie spezifische Logger-Konfigurationen vornehmen, um sensible Daten zu maskieren.
Beispiel in application.properties:

````properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n
logging.level.net.avgl.ekz.onleihe.userapplication=INFO
````

Beispiel in logback.xml:
`````xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
        <filter class="ch.qos.logback.core.filter.EvaluatorFilter">
            <evaluator class="ch.qos.logback.classic.boolex.JaninoEventEvaluator">
                <expression>message.contains("password") || message.contains("clientSecret") || message.contains("privateKey")</expression>
            </evaluator>
            <OnMatch>DENY</OnMatch>
            <OnMismatch>NEUTRAL</OnMismatch>
        </filter>
    </appender>
    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
`````

Diese Ansätze bieten zusätzliche Möglichkeiten, um sicherzustellen, dass sensible Daten nicht in den Logs erscheinen.