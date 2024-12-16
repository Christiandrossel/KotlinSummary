import java.time.Duration
import java.time.Instant

/**
 * Returns the next possible date when the next licence will be free.
 * A list of lends is fetched in the process.
 * This is sorted and searched for the return date that is closest to the current date.
 * Then the number of reservations is multiplied by the loan period and added to the next available date.
 * @param licences a list of active licences of a product. See also {@link getActiveLicences(List<Licence)}.
 * @param productId ID of the product to find all loans of this product and the licence.
 * @param numberOfReservations number of reservations of this product. if there are none, the number must be zero.
 * @param lendingPeriod Lending period calculated on the basis of the reservations made for this purpose
 * @return A date when the next product should be available as Instant
 */
fun getTheNextAvailabilityDate( //TODO OB3-291 calculate next availability date
    licences: List<Licence>,
    productId: String,
    numberOfReservations: Int,
    lendingPeriod: Duration,
    onleihe: Onleihe,
    availabilityInformation: AvailabilityInformation
): Instant {
//        val endDateFromLend = lendService.getNextEndDateFromLend(productId, licences)
//        if (endDateFromLend != null) {
//            return endDateFromLend.plus(lendingPeriod.multipliedBy(numberOfReservations.toLong()))
//        }
//        return null

    //get all active lends
    val lends = lendService.getAllLendsByProductIdAndLicences(productId, licences = licences, actual = true)

    if (availabilityInformation.availability == 0 && lends.isNotEmpty()){
        // get all active reservations
        val reservations = reservationService.getReservationsByOnleiheAndProductIdAndState(onleihe, productId, ReservationState.QUEUED)
        // calculate reservation
        if (lends.size > reservations.size) {
            val nextLend = lends.size - reservations.size +1
            return lends[nextLend].endDate
        }
        if (lends.size < reservations.size) {
            // ermittle anzahl der durchläufe
            val runs = reservations.size / lends.size

            /**
             * Da anzahl der durchläufe keine gerade Zahl sein kann muss die Positoon der Ausleihe bestimmt werden
             */
            val position = reservations.size % lends.size
            val selectedLend = lends[position]

            //rechne für jede lend endtate die anzahl der durchläufe mal die ausleihdauer
            /**
             * Das nächste verfügbare Datum wäre:
             * Anzahl der Reservierung / maximale Anzahl aktiver Ausleihen = Durchläufe
             * Aktuelle Ausleihe + (Durchläufe * Ausleihdauer)
             */
            val nextAvailabilityDate = selectedLend.endDate.plus(lendingPeriod.multipliedBy(runs.toLong()))
            return nextAvailabilityDate
        }
    }
    return Instant.now()
}
