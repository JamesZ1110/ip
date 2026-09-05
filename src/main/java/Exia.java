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

                tasks[taskCount] = new Task(input);
                taskCount++;
                showAddedTask(input);
            }
        }
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

    public static void showAddedTask(String input) {
        System.out.println(LINE);
        System.out.println("added: " + input);
        System.out.println(LINE);
    }
}