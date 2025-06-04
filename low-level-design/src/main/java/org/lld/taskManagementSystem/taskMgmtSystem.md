### Task Management System

## Requirements

1. The task management system should allow users to create, update, and delete tasks.
2. Each task should have a title, description, due date, priority, and status (e.g., pending, in progress, completed).
3. Users should be able to assign tasks to other users and set reminders for tasks.
4. The system should support searching and filtering tasks based on various criteria (e.g., priority, due date, assigned user).
5. Users should be able to mark tasks as completed and view their task history.
6. The system should handle concurrent access to tasks and ensure data consistency.
7. The system should be extensible to accommodate future enhancements and new features.

## Entities
* User
* Task
* TaskStatus (enum)
* TaskPriority (enum)

## Actors
* User
* System

## Attribute
* User : id, name, email, phone
* Task : id, Title, description, dueDate, Priority, TaskStatus, assignedTo (User), creationDate, modificationDate, taskStatus, taskPriority

## Mapping
* User -> Task : 1 : m

## Method/ Action
* User Action
1. createTask()
2. updateTask()
3. deleteTask()
4. assignTask()

* System Action
1. searchTask()
2. filterTask()
3. createUser()
4. getAllTask()

## Design pattern
1. strategyPattern for filtering task
2. 



