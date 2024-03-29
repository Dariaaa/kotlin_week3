package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter { driver -> !trips.groupBy { it.driver }.containsKey(driver) }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers
                .filter {
                    this.trips
                            .filter { trip: Trip -> it in trip.passengers }
                            .count() >= minTrips
                }
                .toSet()


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers
                .filter {
                    trips
                            .filter { trip ->
                                it in trip.passengers &&
                                        trip.driver == driver
                            }
                            .size > 1
                }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers
                .filter { p: Passenger ->

                    trips.filter { p in it.passengers && it.discount != null }.size >
                    trips.filter { p in it.passengers && it.discount == null }.size

                }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) return null


    val maxDuration = trips.map{ it.duration }.max() ?: 0
    var result:IntRange? = null
    var maxCount = 0
    for(i in 0 until maxDuration step 10) {
        val range = IntRange(i, i + 9)
        val count = this.trips.filter { it.duration in range }.count()
        if (count>maxCount) {
            maxCount = count
            result = range
        }

    }
    return result
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalCost = this.trips.map { it.cost }.sum()
    val map = trips.groupBy { it.driver }
            .mapValues { (_, trips) -> trips.sumByDouble { it.cost }}
            .toList()
            .sortedByDescending { (_, value) -> value}.toMap()
    var countDrivers = 0
    var sum = 0.0
    for(v in map.values){
        countDrivers++
        sum+=v
        if (sum>=totalCost*0.8) break
    }
    return countDrivers <=(allDrivers.size*0.2)


}