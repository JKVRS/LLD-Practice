package org.lld.taskManagementSystem;



import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class User{
     private UUID id;
     private String userName;
     private String email;
     private String phone;

    public User(UUID id, String userName, String email, String phone) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
class Task{
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private TaskPriority priority;
    private TaskStatus status;
    private User assignedTo;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public Task(String id,
                String title,
                String description,
                String dueDate,
                TaskPriority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.creationDate = LocalDateTime.now();
        this.priority = priority;
        this.status = TaskStatus.OPEN;
        this.assignedTo = null;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }


    // setter method
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", assignedTo=" + assignedTo +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
enum TaskPriority {
    LOW,MEDIUM, HIGH
}
enum TaskStatus{
    OPEN,INPROGRESS, COMPLETED
}
interface Filterization {
    List<Task> filter(List<Task> tasks);
}
class PriorityFilterization implements Filterization{
  //  private UserAction userAction;
      private TaskPriority priority;
    public PriorityFilterization(TaskPriority priority){
     //  this.userAction = userAction;
        this.priority = priority;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
      // List<Task> tasks = userAction.getAllTask();
       return tasks.stream().filter(t->t.getPriority()==priority).collect(Collectors.toList());
    }
}
class AssignedFilterization implements Filterization{

   // private UserAction userAction;
     private User user;
    public AssignedFilterization(User user){
        this.user = user;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
         return tasks.stream().filter(task -> task.getAssignedTo()==user).collect(Collectors.toList());
    }
}
class UserAction{
    private User user;
    private Task task;
    private Map<UUID, List<Task>> assignedTaskMap ;
    private List<Task> taskList;

    // constructor
    public UserAction(){
        taskList = new ArrayList<>();
        assignedTaskMap = new HashMap<>();
    }

    public synchronized void createTask(Task task){
        taskList.add(task);
        System.out.println("Task has been created !!" + task.getTitle());
    }

    public synchronized void updateTask(String id, String newTitle, String newDescription, String newDueDate, TaskPriority newTaskPriority){
        Optional<Task> optionalTask = taskList.stream().filter(task->task.getId().equals(id)).findFirst();
        if(optionalTask.isEmpty()) {
            System.out.println("Task with taskId : "+id+ " is not present");
            return;
        }
        Task t = optionalTask.get();
        if(!newTitle.equals("")) {
            t.setTitle(newTitle);
        }
        if(!newDescription.equals("")){
            t.setDescription(newDescription);
        }
        if(!newDueDate.equals("")){
            t.setDueDate(newDueDate);
        }
        if(newTaskPriority !=null){
            t.setPriority(newTaskPriority);
        }
        t.setModificationDate(LocalDateTime.now());
        System.out.println("Task has been updated Successfully with taskId :"+id);
    }

    public synchronized void deleteTask(String taskId){
        Optional<Task> optionalTask = taskList.stream().filter(task -> task.getId().equals(taskId)).findFirst();
        if(optionalTask.isEmpty()){
            System.out.println("Task with taskId : "+ taskId +" is not present");
            return;
        }
        taskList.remove(optionalTask.get());
        System.out.println("Task has been deleted : "+ taskId);

    }


    public synchronized void assignedTask(User user, Task task){
        UUID userId = user.getId();
        if(!assignedTaskMap.containsKey(userId)){
            assignedTaskMap.put(userId, new ArrayList<>());
        }
        task.setAssignedTo(user);
        List<Task> assignedTaskList = assignedTaskMap.get(userId);
        assignedTaskList.add(task);
        assignedTaskMap.put(userId, assignedTaskList);
        System.out.println(" Task with task Id : " + task.getId() +" has been assigned to : "+ user.getUserName());
    }

    public synchronized void markCompleteTask(String taskId, TaskStatus markStatus){
        Optional<Task> optionalTask = taskList.stream().filter(task -> task.getId().equals(taskId)).findFirst();
        if(optionalTask.isEmpty()){
            System.out.println("Task with taskId : "+ taskId +" is not present");
            return;
        }
        Task t = optionalTask.get();
        t.setStatus(markStatus);
        t.setModificationDate(LocalDateTime.now());
        System.out.println("Task with taskId :" + t.getId() +" has been completed ");
    }

    public List<Task> getAllTask(){
        if(taskList.isEmpty()){
            System.out.println("Current no task has been created: !!");
            return null;
        }
       return taskList;
    }

    public List<Task> getAssignedTaskByUser(User user){
        if(!assignedTaskMap.containsKey(user.getId())){
            System.out.println("User with user name: "+ user.getUserName()+" doesn't exist ");
            return null;
        }
            List<Task> assignedTaskToUser = assignedTaskMap.get(user.getId());
            if(assignedTaskToUser == null){
                System.out.println("No Task Assigned to the user: "+ user.getUserName());
                return null;
            }
        System.out.println("List of tasks assigned to the user :"+ user.getUserName());
        return assignedTaskToUser;
    }

}

class SystemManager {
     private User user;
     private TaskPriority priority;
     private  TaskStatus status;
     private UserAction userAction;

    public SystemManager(UserAction userAction) {
        this.userAction = userAction;
    }

    public List<Task> filterTaskByPriority(Filterization filterization){
        return filterization.filter(userAction.getAllTask());
    }

    public List<Task> filterTaskByAssignedTo(Filterization filterization){
        return filterization.filter(userAction.getAllTask());
    }
}

public class TaskMgmMain {
    public static void main(String[] args) {
             User krish = new User(UUID.randomUUID(),"krish", "abac@gmail.com", "222020");
             User manoj = new User(UUID.randomUUID(), "manoj", "xyz@gmail.com", "790393");
             User ram = new User(UUID.randomUUID(), "ram", "pqr@gmail.com", "9088008");
             User mohan = new User(UUID.randomUUID(), "mohan", "mno@gmail.com","332424");

             Task t1 = new Task("100", "Open account","a user can be able to open account","12/1/2025",TaskPriority.HIGH);
             Task t2 = new Task("200","Close account", "a user can be able to close account", "11/1/2025", TaskPriority.HIGH);
             Task t3 = new Task("300","Upload document"," a user can be able to upload document","13/1/2025", TaskPriority.MEDIUM);
             Task t4 = new Task("400","Update profile","a user can be able to update his profile","14/1/2025", TaskPriority.LOW);
             Task t5 = new Task("500","Fetch account details","Fetch account details for a particulate user","10/1/2025",TaskPriority.MEDIUM);
             Task t6 = new Task("600","Fetch user","fetch List of users","10/1/2025",TaskPriority.LOW);


             UserAction userAction  =new UserAction();

             System.out.println("Fetch All tasks before creation of tasks: ");
             userAction.getAllTask();

             System.out.println("Create the task !!");

            userAction.createTask(t1);
            userAction.createTask(t2);
            userAction.createTask(t3);
            userAction.createTask(t4);

        System.out.println("Fetch All tasks after creation of tasks : ");
        userAction.getAllTask().stream().forEach(task -> System.out.println(task));

            System.out.println("Assign the task to the users");
            userAction.assignedTask(krish,t1);
            userAction.assignedTask(manoj,t3);
            userAction.assignedTask(krish,t2);
            userAction.assignedTask(ram,t4);

        System.out.println("After assign the task: ");
        userAction.getAssignedTaskByUser(krish).stream().forEach(task -> System.out.println(task));

        System.out.println("Search and filter task based on task priority:: ");
        SystemManager systemManager = new SystemManager(userAction);
        systemManager.filterTaskByPriority(new PriorityFilterization(TaskPriority.HIGH))
                .stream().forEach(task -> System.out.println(task));

        System.out.println("Search and filter task base on assigned task:: ");
        SystemManager systemManager1 = new SystemManager(userAction);
        systemManager1.filterTaskByAssignedTo(new AssignedFilterization(ram))
                .stream().forEach(task -> System.out.println(task));

    }
}

/*
   In this code some solid principle might be volatile
   1. DRY  - This code should use DRY (dont repeat yourself) to fetch the task becaise before creating , deleting, updating task
      we need to feth the task so we can make one saperate private method.
  2.  for filtering the task , PriorityFilterization is tightly coupled with useraction and system manager has two method for diff
       filtering we can enhance it
       like : in filter method we can pass the List<Task> because we have to filter out the task form the list only
         interface TaskFilter {
              List<Task> filter(List<Task> tasks);
         }

        class PriorityFilter implements TaskFilter {
              private TaskPriority priority;
              public PriorityFilter(TaskPriority priority) {
              this.priority = priority;
       }
       @Override
        public List<Task> filter(List<Task> tasks) {
            return tasks.stream()
                    .filter(t -> t.getPriority() == priority)
                    .collect(Collectors.toList());
      }
    }

  class AssignedUserFilter implements TaskFilter {
    private User user;
    public AssignedUserFilter(User user) {
        this.user = user;
    }
    @Override
    public List<Task> filter(List<Task> tasks) {
        return tasks.stream()
                    .filter(t -> t.getAssignedTo().equals(user))
                    .collect(Collectors.toList());
    }
}
  3. We can split the overload of userAction class using repository pattern
 */
