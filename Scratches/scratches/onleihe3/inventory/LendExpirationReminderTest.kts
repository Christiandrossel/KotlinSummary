import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Test LendExpirationReminder
 */
LendExpirationReminderTest()


class LendExpirationReminderTest {

    init {
        val now = Instant.now()
        println("Now: $now")
        testReminderWith10Minutes(now)
        testReminderWitn1Hours(now)
        testReminderWith1Day(now)
    }

    fun testReminderWith10Minutes(now: Instant) {
        val startDate = now
        val endDate = Instant.now().plus(10, ChronoUnit.MINUTES)
        val reminder = LendExpirationReminder(startDate, endDate)
        println("10 minutes endDate: ${endDate}")
        println("10 minutes: ${reminder.date}")
    }

    fun testReminderWitn1Hours(now: Instant) {
        val startDate = now
        val endDate = Instant.now().plus(1, ChronoUnit.HOURS)
        val reminder = LendExpirationReminder(startDate, endDate)
        println("1 hour: ${reminder.date}")
    }

    fun testReminderWith1Day(now: Instant) {
        val startDate = now
        val endDate = Instant.now().plus(1, ChronoUnit.DAYS)
        val reminder = LendExpirationReminder(startDate, endDate)
        println("1 day: ${reminder.date}")
    }
}




/**
 * Represents a reminder for a lend that is about to expire.
 * @param date The date when the reminder should be sent.
 * @param isSent Whether the reminder has already been sent.
 */
class LendExpirationReminder(
    val date: Instant,
    var isSent: Boolean
) {

    companion object {
        private const val REMINDER_THRESHOLD_PERCENTAGE_DEFAULT = 0.1

        /**
         * Calculates the threshold in milliseconds.
         * @param startDate The start date of the lend period.
         * @param endDate The end date of the lend period.
         * @param reminderThresholdPercentage The percentage of the lend period that should be left when the reminder is sent.
         * @return The threshold in milliseconds.
         */
        private fun calculateThresholdMillis(startDate: Instant, endDate: Instant, reminderThresholdPercentage: Double): Long {
            val lendPeriod = endDate.toEpochMilli() - startDate.toEpochMilli()
            return (lendPeriod * reminderThresholdPercentage).toLong()
        }
    }

    /**
     * Constructor that takes the lend period and calculates the date when the reminder should be sent.
     * @param startDate The start date of the lend period.
     * @param endDate The end date of the lend period.
     * @param reminderThresholdPercentage The percentage of the lend period that should be left when the reminder is sent.
     *        Default is 10%.
     * @return The date when the reminder should be sent.
     */
    constructor(
        startDate: Instant,
        endDate: Instant,
        reminderThresholdPercentage: Double = REMINDER_THRESHOLD_PERCENTAGE_DEFAULT
    ) : this(
        endDate.minusMillis(calculateThresholdMillis(startDate, endDate, reminderThresholdPercentage)), false
    )
}