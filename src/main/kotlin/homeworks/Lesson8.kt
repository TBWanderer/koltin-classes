package main.kotlin.homeworks
import kotlin.random.Random

class Lesson8 {
    fun run() {
        val user = User(id = 1, firstName = "John", lastName = "Doe", email = "john.doe@example.com")
        val task = createSampleTask(user)

        displayTaskInfo(task)
    }

    data class User(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val email: String
    )

    enum class TaskPriority {
        LOW, MEDIUM, HIGH
    }

    sealed class TaskStatus {
        data object ToDo : TaskStatus()
        data object InProgress : TaskStatus()
        data object Done : TaskStatus()
    }

    data class Task(
        val id: Int,
        val title: String,
        val description: String,
        val assignedTo: User,
        val priority: TaskPriority,
        var status: TaskStatus
    )

    private fun displayTaskInfo(task: Task) {
        println("Task ID: ${task.id}")
        println("Title: ${task.title}")
        println("Description: ${task.description}")
        println("Assigned to: ${task.assignedTo.firstName} ${task.assignedTo.lastName}")
        println("Priority: ${task.priority}")
        println("Status: ${task.status::class.simpleName}")
    }

    private fun createSampleTask(user: User): Task {
        val randomId = Random.nextInt(1000)
        val randomTitle = "Sample Task $randomId"
        val randomDescription = "This is a description for task $randomId."
        val randomPriority = TaskPriority.entries[Random.nextInt(TaskPriority.entries.size)]
        val randomStatus = when (Random.nextInt(3)) {
            0 -> TaskStatus.ToDo
            1 -> TaskStatus.InProgress
            else -> TaskStatus.Done
        }

        return Task(randomId, randomTitle, randomDescription, user, randomPriority, randomStatus)
    }
}