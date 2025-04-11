class Room(val description: String, val options: List<OP> = emptyList()) {
    fun show() {
        println(description)
        if (options.isNotEmpty()) {
            options.forEach { println("${it.number}. ${it.description}") }
            println("Enter your choice (${options.joinToString(", ") { it.number.toString() }}): ")
        } else {
            println("Game over!")
        }
    }
}
class OP(val number: Int, val description: String, val nextRoom: Int)
class Game {
    private var currentRoom = 1
    private val rooms = mapOf(
        1 to Room(
            "You are in a dark room, you see 3 doors (left, right, middle)",
            listOf(
                OP(1, "Go left", 2),
                OP(2, "Go right", 3),
                OP(3, "Go middle", 4)
            )
        ),
        2 to Room(
            "You entered the left room",
            listOf(
                OP(1, "Go left", -1),
                OP(2, "Go back", 1)
            )
        ),
        3 to Room("You entered the middle room and see a white room and a dark portal"),
        4 to Room("You entered the middle room, the door slammed shut. You smell a pungent odor and pass out"),
        5 to Room("You entered the white room, your memory fails you and you fall asleep"),
        6 to Room(
            "You've entered the dark portal. You can either try to escape or stay here forever.",
            listOf(
                OP(1, "Try to escape", 1),
                OP(2, "Stay here forever", -1)
            )
        )
    )
    fun start() {
        while (currentRoom != -1) {
            val room = rooms[currentRoom] ?: break
            room.show()
            
            if (room.options.isEmpty()) {
                currentRoom = -1
                continue
            }
            when (val choice = readLine()?.toIntOrNull()) {
                in room.options.map { it.number } -> {
                    val option = room.options.first { it.number == choice }
                    
                    if (currentRoom == 3) {
                        DBportal()
                    } else {
                        currentRoom = option.nextRoom
                    }
                }
                else -> println("Invalid choice, try again")
            }
        }
    }
    private fun DBportal() {
        println("You approach a dark portal and are asked: What are the best 2 items in Dota?")
        if (readLine()?.equals("Radiance and Boots of Travel", ignoreCase = true) == true) {
            println("Correct! You enter the portal.")
            currentRoom = 6
        } else {
            println("Wrong answer! You have been swallowed by darkness.")
            currentRoom = -1
        }
    }
}
fun main() {
    Game().start()
}
