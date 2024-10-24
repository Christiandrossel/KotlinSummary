package coroutines.playground.data

object ParticipantCreator {

    fun createParticipantList(count: Int = 10): List<Participant> {
        return (1..count).map {
            Participant("Participant $it", it * 10)
        }
    }

}

data class Participant(
    val name: String,
    val age: Int
)