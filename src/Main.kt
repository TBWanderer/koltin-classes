import jdk.jfr.Description
import kotlin.math.PI
import java.security.MessageDigest

fun main() {
    val hw = Lesson5HW()
    hw.run()
}

class Lesson5HW {
    fun run() {
        val system = System()
        val get = Get()

        val adminLevel = 7

        var currentUser: User? = null

        val helpCommand = Command("help", "Show available commands", -1)
        val loginCommand = Command("login", "Authenticate user access", -1)
        val logoutCommand = Command("logout", "End user session", 0)
        val addCommand = Command("add", "Adding something new", adminLevel)
        val exitCommand = Command("exit", "Close the application", -1)
        system.addCommand(helpCommand, loginCommand, logoutCommand, addCommand, exitCommand)

        val admin = User("General", "Admin", -1, "admin@system.com", "61ec299297407c2f668786b780c634b21a9520e5eaf4c6c2ab57750ebb8f4fa7", adminLevel)
        system.addUser(admin)

        while (true) {
            var prompt = "> "
            if (currentUser != null) {
                prompt = "$ "
                if (currentUser.level >= adminLevel) {
                    prompt = "# "
                }
            }

            val inputArgs = get.str(prompt).split(" ")
            val command = system.getCommand(inputArgs[0])
            if (command != null) {
                var currentLevel = -1
                if (currentUser != null) {
                    currentLevel = currentUser.level
                }
                if (currentLevel >= command.level) {
                    when (command.name) {
                        "help" -> {
                            for (commandObject in system.commands) {
                                if (commandObject.level <= currentLevel) {
                                    val someBeauty = 10
                                    var line = commandObject.name + " "
                                    for (temp in 1..someBeauty - commandObject.name.length) {
                                        line += "."
                                    }
                                    line += " " + commandObject.description
                                    println(line)
                                }
                            }
                        }

                        "login" -> {
                            val email = get.str("Enter email: ")
                            val password = get.str("Enter password: ")
                            currentUser = system.logInUser(email, password)
                        }

                        "logout" -> if (currentUser != null) {
                            system.logOutUser(currentUser)
                            currentUser = null
                        } else {
                            println("You are not authorized")
                        }

                        "add" -> if (currentUser != null) {
                            if (inputArgs.size == 2) {
                                if (inputArgs[1] == "user") {
                                    val name = get.str("Enter name: ")
                                    val surname = get.str("Enter surname: ")
                                    val age = get.int("Enter age: ", min = 0, max = 127)
                                    val email = get.str("Enter email: ")
                                    val password = get.str("Enter password for new user: ")
                                    var passwordAgain = get.str("Enter password again: ")
                                    while (passwordAgain != password) {
                                        passwordAgain = get.str("Incorrect! Enter password again: ")
                                    }
                                    val userLevel = get.int("Enter user level: ", min = 0, max = adminLevel)
                                    val newUser = User(name, surname, age, email, hashPassword(password), userLevel)
                                    system.addUser(newUser)
                                } else if (inputArgs[1] == "command") {
                                    val name = get.str("Enter command name: ")
                                    val description = get.str("Enter command description: ")
                                    val level = get.int("Enter command level: ")

                                    val newCommand = Command(name, description, level)
                                    system.addCommand(newCommand)
                                } else {
                                    println("I can't do this =/")
                                }
                            } else if (inputArgs.size > 2) {
                                println("There are too many arguments")
                            } else {
                                println("There are too few arguments")
                            }
                        } else {
                            println("You are not authorized")
                        }

                        "exit" -> {
                            if (currentUser != null) {
                                system.logOutUser(currentUser)
                            }
                            break
                        }
                    }
                } else {
                    println("Operation does not permitted")
                }
            } else {
                println("This command does not exists")
            }
        }
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { String.format("%02x", it) }
    }

    data class Command(
        val name: String,
        val description: String,
        val level: Int,
    )

    data class User(
        val firstName: String,
        val lastName: String,
        val age: Int,
        val email: String,
        private val hashedPassword: String,
        val level: Int,
        var isAuthorized: Boolean = false,
    ) {
        fun checkPassword(password: String): Boolean {
            return hashedPassword == hashPassword(password)
        }

        private fun hashPassword(password: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashedBytes = digest.digest(password.toByteArray())
            return hashedBytes.joinToString("") { String.format("%02x", it) }
        }
    }

    class System {
        val commands = mutableListOf<Command>()
        private val users = mutableListOf<User>()

        fun addUser(user: User) {
            if (users.any { it.email == user.email }) {
                println("User with gotten email already exists")
            } else {
                users.add(user)
                println("User ${user.firstName} ${user.lastName} added to the System")
            }
        }

        fun logInUser(email: String, password: String): User? {
            val user = users.find { it.email == email }
            return if (user != null && !user.isAuthorized && user.checkPassword(password)) {
                user.isAuthorized = true
                println("User ${user.firstName} ${user.lastName} authorized")
                user
            } else if (user == null) {
                println("Authorization Error. Gotten user does not exists")
                null
            } else if (user.isAuthorized){
                println("Authorization Error. Gotten user already authorized")
                null
            } else {
                println("Authorization Error. Wrong Password")
                null
            }
        }

        fun logOutUser(user: User) {
            if (user.isAuthorized) {
                user.isAuthorized = false
                println("User ${user.firstName} ${user.lastName} logged out")
            } else {
                println("User ${user.firstName} ${user.lastName} not authorized")
            }
        }

        fun addCommand(vararg commandList: Command) {
            for (command in commandList) {
                commands.add(command)
            }
        }

        fun getCommand(name: String): Command? {
            for (command in commands) {
                if (command.name == name) {
                    return command
                }
            }
            return null
        }
    }
}

class Lesson4HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val r = get.float("Enter radius (may to enter float point num): ")
                println("S of circle with gotten radius: ${circleArea(r)}")
            }
            2 -> {
                val numbers = get.str("Enter nums in one line: ").split(" ").mapNotNull {
                    try {
                        it.toInt()
                    } catch (e: NumberFormatException) {
                        null
                    }
                }

                println("The number of even numbers: ${evenCount(numbers)}")
            }
            3 -> {
                val n = get.int("Enter N to get N!: ")
                println("$n! equals ${fact(n)}")
            }
        }
    }

    private fun circleArea(r: Float) : Double {
        return PI * r * r
    }

    private fun evenCount(numbers: List<Int>) : Int {
        return numbers.count { it % 2 == 0 }
    }

    private fun fact(n: Int) : Int {
        if (n == 1) {5
            return 1
        }

        if (n <= 0 ) {
            println("This num can't realized by this func to fact")
            return -1
        }

        return n * fact(n-1)
    }
}

class Lesson3HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val N = get.int("Enter N to get Σ(i=1, n=N) 1/i: ")
                var sum = 0.0

                for (i in 1..N) {
                    sum += 1 / i
                }
                println("Σ equals: $sum")
            }
            2 -> {
                val N = get.int("Enter N to get factorial: ")
                var factorial = 1

                for (i in 1..N) {
                    factorial *= i
                }

                println("Факториал числа $N: $factorial")
            }
            3 -> {

                val M = get.int("Enter M (count of friends): ")
                val friends = mutableListOf<Map<String, Any>>()

                for (i in 1..M) {
                    val name = get.str("Enter friend's #$i name: ")
                    val age = get.int("Enter friend's #$i age: ")

                    friends.add(mapOf("name" to name, "age" to age))
                }

                val youngestFriend = friends.minByOrNull { it["age"] as Int }
                println("The youngest friend: ${youngestFriend?.get("name")}")
            }
            4 -> {
                val n = get.int("Enter count of apps")

                val batteryConsumption = get.str("Enter data about apps: ").split(" ").map { it.toInt() }

                val totalConsumption = batteryConsumption.sum()

                val fullHours = 100 / totalConsumption

                println(fullHours)
            }
        }
    }
}

class Lesson2HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val a = get.int("Enter section 1: ")
                val b = get.int("Enter section 2: ")
                val c = get.int("Enter section 3: ")

                if (a < b + c && b < a + c && c < a + b) {
                    println("Triangle with gotten sections exists")
                } else {
                    println("Triangle with gotten sections doesn't exists")
                }
            }
            2 -> {
                val hours = get.int("Enter count of hours: ")

                when (hours) {
                    in 5..11 -> {
                        println("Morning")
                    }
                    in 12..17 -> {
                        println("Day")
                    }
                    in 18..22 -> {
                        println("Evening")
                    }
                    in 23..24, in 0..4 -> {
                        println("Night")
                    }
                    else -> {
                        println("Error")
                    }
                }
            }
            3 -> {
                val year = get.int("Enter year: ")

                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    println("The gotten year is Leap Year")
                } else {
                    println("The gotten year isn't Leap Year")
                }
            }
            4 -> {
                val score = get.int("Enter score: ")

                var scoreInRF = ""
                var scoreInECTS = ""

                when (score) {
                    in 0..30 -> {
                        scoreInRF = "2"
                        scoreInECTS = "F"
                    }

                    in 31..50 -> {
                        scoreInRF = "2"
                        scoreInECTS = "FX"
                    }

                    in 51..60 -> {
                        scoreInRF = "3"
                        scoreInECTS = "E"
                    }

                    in 61..68 -> {
                        scoreInRF = "3"
                        scoreInECTS = "D"
                    }

                    in 69..85 -> {
                        scoreInRF = "4"
                        scoreInECTS = "C"
                    }

                    in 86..94 -> {
                        scoreInRF = "5"
                        scoreInECTS = "B"
                    }

                    in 95..100 -> {
                        scoreInRF = "5"
                        scoreInECTS = "A"
                    }

                    else -> {
                        println("Incorrect score")

                    }
                }

                println("Score in RF: $scoreInRF")
                println("Score in ECTS: $scoreInECTS")
            }
            else -> {
                println("Incorrect mode")
            }
        }
    }
}

class Lesson1HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val name = get.int("Enter name: ")
                val age = get.int("Enter age: ")
                val email = get.str("Enter email: ")

                println("Your name: $name. You are $age years old. Email: $email")
            }
            2 -> {
                val weight = get.int("Enter weight(kg): ")
                val height = get.int("Enter height(cm): ") / 100.0
                val ibm = weight * 1.0 / (height * height)
                println("Index Body Mass: $ibm")

                if (ibm > 30) {
                    println("Bad")
                } else if (ibm > 25) {
                    println("Not good")
                } else if (ibm > 20){
                    println("Normal")
                } else {
                    println("Less than normal")
                }
            }
            3 -> {
                val kids = get.int("Enter count of kids: ")
                val apples = get.int("Enter count of apples: ")
                val integer = apples / kids
                val remainder = apples - kids * integer
                println("Each student will get $integer apples")
                println("There will be $remainder apples left")
            }
            else -> {
                println("Incorrect mode")
            }
        }
    }
}

class Get {
    fun int(prompt: String, min: Int? = null, max: Int? = null) : Int {
        print(prompt)
        val res = readln().toIntOrNull()
        if (res != null) {
            if (max != null && res > max || min != null && res < min) {
                println("Out of limits! Limits: $min (min), $max (max)")
                return int(prompt, min = min, max = max)
            }
            return res
        } else {
            println("Input must be an integer")
            return int(prompt, min = min, max = max)
        }
    }

    fun float(prompt: String) : Float {
        print(prompt)
        val res = readln().toFloatOrNull()

        if (res != null) {
            return res
        } else {
            println("Input must be floating point number")
            return float(prompt)
        }
    }

    fun str(prompt: String) : String {
        print(prompt)
        return readln()
    }
}