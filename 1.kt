class Room(val name: String, val desc: String, val over: Boolean = false) {
    private val exits: MutableMap<String, Room> = mutableMapOf()

    fun exit(room: Room) {
        exits[room.name] = room
    }
    fun getExit(roomName: String): Room? {
        return exits[roomName]
    }
    fun describe() {
        println("Вы находитесь в комнате: $name")
        println(desc)
        if (exits.isNotEmpty()) {
            println("Есть выход из этой комнаты: ${exits.keys.joinToString(", ")}")
        } else {
            println("Нет выходов из этой комнаты")
        }
    }
}
fun main() {
    val room1 = Room("Комната 1", "белая комната без потолка")
    val room2 = Room("Комната 2", "просто обычная комнота")
    val room3 = Room("Комната 3", "темная комната в которой ничего не видно", over = true)  
    room1.exit(room2)
    room2.exit(room1)
    room2.exit(room3)
    
    var currentRoom: Room = room1
    while (true) {
        currentRoom.describe()
        if (currentRoom.over) {
            println("Игра окончена")
            break
        }
        println("Введите название комнаты в которую хотите перейти или 'выход' для выхода:")
        val input = readLine()

        when {
            input == "выход" -> {
                println("Вы вышли")
                return
            }
            currentRoom.getExit(input ?: "")?.let { nextRoom ->
                currentRoom = nextRoom
            } ?: println("Вы не можете войти в эту комнату")
        }
    }
}