import java.util.Scanner;

public class Exia {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        showGreeting();

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                if (input.equals("bye")) {
                    showGoodbye();
                    break;
                }

                if (input.equals("list")) {
                    showTaskList(tasks, taskCount);
                    continue;
                }

                if (input.startsWith("done ")) {
                    markTaskAsDone(tasks, input);
                    continue;
                }

                if (input.startsWith("todo ")) {
                    tasks[taskCount] = createToDo(input);
                    taskCount++;
                    showAddedTask(tasks[taskCount - 1]);
                    continue;
                }

                if (input.startsWith("deadline ")) {
                    tasks[taskCount] = createDeadline(input);
                    taskCount++;
                    showAddedTask(tasks[taskCount - 1]);
                    continue;
                }

                if (input.startsWith("event ")) {
                    tasks[taskCount] = createEvent(input);
                    taskCount++;
                    showAddedTask(tasks[taskCount - 1]);
                    continue;
                }

                tasks[taskCount] = new Task(input);
                taskCount++;
                showAddedTask(tasks[taskCount - 1]);
            }
        }
    }

    public static ToDo createToDo(String input) {
        String description = input.substring(5);
        return new ToDo(description);
    }

    public static Deadline createDeadline(String input) {
        String content = input.substring(9);
        String[] parts = content.split(" /by ", 2);
        String description = parts[0];
        String by = parts[1];
        return new Deadline(description, by);
    }

    public static Event createEvent(String input) {
        String content = input.substring(6);
        String[] fromSplit = content.split(" /from ", 2);
        String description = fromSplit[0];
        String[] toSplit = fromSplit[1].split(" /to ", 2);
        String from = toSplit[0];
        String to = toSplit[1];
        return new Event(description, from, to);
    }

    public static void showGreeting() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Exia");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public static void showGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public static void showTaskList(Task[] tasks, int taskCount) {
        System.out.println(LINE);
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        System.out.println(LINE);
    }

    public static void markTaskAsDone(Task[] tasks, String input) {
        int taskNumber = Integer.parseInt(input.substring(5));
        Task task = tasks[taskNumber - 1];
        task.markAsDone();

        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        System.out.println(LINE);
    }

    public static void showAddedTask(Task task) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println(LINE);
    }
}