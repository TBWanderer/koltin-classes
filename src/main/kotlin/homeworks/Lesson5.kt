package main.kotlin.homeworks

import java.security.MessageDigest
import main.kotlin.utils.Get

class Lesson5 {
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
                            if (currentUser != null) {
                                println("Firstly log out from account")
                            } else {
                                val email = get.str("Enter email: ")
                                val password = get.str("Enter password: ")
                                currentUser = system.logInUser(email, password)
                            }
                        }

                        "logout" -> if (currentUser != null) {
                            system.logOutUser(currentUser)
                            currentUser = null
                        } else {
                            println("You are not authorized =(")
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

                        else -> println("Just nothing =]")
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