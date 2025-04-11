class Room(val description: String, val options: List<Option>) {
    fun enter() {
        println(description)
        options.forEach { option ->
            println("${option.number}. ${option.description}")
        }
        println("Enter your choice (${options.joinToString("; ") { it.number.toString() }}): ")
    }
}

class Option(val number: Int, val description: String, val nextRoom: Int)

class Game {
    private var currentRoom: Int = 1
    private val rooms: Map<Int, Room>

    init {
        rooms = mapOf(
            1 to Room(
                "You are in a dark room, you see 3 doors (left, right, midle)",
                listOf(
                    Option(1, "Go left", 2),
                    Option(2, "Go right", 3),
                    Option(3, "Go midle", 4)
                )
            ),
            2 to Room(
                "You entered the left room",
                listOf(
                    Option(1, "Go left", -1), // -1 indicates game over
                    Option(2, "Go right", 1)
                )
            ),
            3 to Room(
                "You entered the midle room and see a white room and a dark portal",
                listOf(
                    Option(1, "Go white room", 5),
                    Option(2, "Go to dark portal", 6), // 6 is the portal room
                    Option(3, "Go back", 1)
                )
            ),
            4 to Room(
                "You entered the midle room, the door slammed shut and you smelled a pungent odor and you passed out",
                emptyList() // Game over
            ),
            5 to Room(
                "You entered the white room, you feel your memory failing you and you fall asleep",
                emptyList() // Game over
            ),
            6 to Room(
                "You've entered the dark portal. You can either try to escape or stay here forever.",
                listOf(
                    Option(1, "Try to escape", 1),
                    Option(2, "Stay here forever", -1) 
                )
            )
        )
    }

    fun start() {
        while (true) {
            val room = rooms[currentRoom]
            room?.enter()

            if (currentRoom == -1) {
                println("Game over!")
                return
            }
            val choice = readLine()
            val option = room?.options?.find { it.number.toString() == choice }
            if (option != null) {
                currentRoom = option.nextRoom
                if (currentRoom == 6) {
                    println("You've entered the dark portal.")
                    // Handle the portal logic here
                } else if (currentRoom == -1) {
                    println("Game over!")
                    return
                } else if (currentRoom == 3) {
                    println("You approach a dark portal and are asked a question. Best 2 items in Dota")
                    val password = readLine()
                    if (password == "Radience and boost of travel") {
                        println("Correct! You enter the portal.")
                        currentRoom = 6
                    } else {
                        println("Wrong password! You have been swallowed by darkness.")
                        println("Game over!")
                        return
                    }
                }
            } else {
                println("Error choice, try again")
            }
        }
    }
}

fun main() {
    val game = Game()
    game.start()
}
