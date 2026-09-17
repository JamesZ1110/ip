import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Exia {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        showGreeting();

        Storage storage = new Storage("data/exia.txt");
        ArrayList<Task> tasks;

        try {
            tasks = storage.loadTasks();
        } catch (IOException e) {
            showError(e.getMessage());
            tasks = new ArrayList<>();
        }

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                try {
                    if (input.equals("bye")) {
                        showGoodbye();
                        break;
                    }

                    if (input.equals("list")) {
                        showTaskList(tasks);
                        continue;
                    }

                    if (input.startsWith("done ")) {
                        markTaskAsDone(tasks, input);
                        storage.saveTasks(tasks);
                        continue;
                    }

                    if (input.equals("delete") || input.startsWith("delete ")) {
                        deleteTask(tasks, input);
                        storage.saveTasks(tasks);
                        continue;
                    }

                    Task task = createTask(input);
                    tasks.add(task);
                    storage.saveTasks(tasks);
                    showAddedTask(task);
                } catch (ExiaException e) {
                    showError(e.getMessage());
                } catch (IOException e) {
                    showError("Unable to save tasks.");
                }
            }
        }
    }

    public static Task createTask(String input) throws ExiaException {
        if (input.equals("todo")) {
            throw new ExiaException(
                    "The description of a todo cannot be empty.");
        }

        if (input.equals("deadline")) {
            throw new ExiaException(
                    "The description of a deadline cannot be empty.");
        }

        if (input.equals("event")) {
            throw new ExiaException(
                    "The description of an event cannot be empty.");
        }

        if (input.startsWith("todo ")) {
            return createToDo(input);
        }

        if (input.startsWith("deadline ")) {
            return createDeadline(input);
        }

        if (input.startsWith("event ")) {
            return createEvent(input);
        }

        throw new ExiaException(
                "I'm sorry, but I don't know what that means :-(");
    }

    public static ToDo createToDo(String input) throws ExiaException {
        String description = input.substring(5).trim();

        if (description.isEmpty()) {
            throw new ExiaException(
                    "The description of a todo cannot be empty.");
        }

        return new ToDo(description);
    }

    public static Deadline createDeadline(String input)
            throws ExiaException {
        String content = input.substring(9).trim();

        if (content.isEmpty()) {
            throw new ExiaException(
                    "The description of a deadline cannot be empty.");
        }

        String[] parts = content.split(" /by ", 2);

        if (parts.length < 2 || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty()) {
            throw new ExiaException(
                    "Please use: deadline DESCRIPTION /by TIME");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        return new Deadline(description, by);
    }

    public static Event createEvent(String input) throws ExiaException {
        String content = input.substring(6).trim();

        if (content.isEmpty()) {
            throw new ExiaException(
                    "The description of an event cannot be empty.");
        }

        String[] fromSplit = content.split(" /from ", 2);

        if (fromSplit.length < 2 || fromSplit[0].trim().isEmpty()) {
            throw new ExiaException(
                    "Please use: event DESCRIPTION /from START /to END");
        }

        String[] toSplit = fromSplit[1].split(" /to ", 2);

        if (toSplit.length < 2 || toSplit[0].trim().isEmpty()
                || toSplit[1].trim().isEmpty()) {
            throw new ExiaException(
                    "Please use: event DESCRIPTION /from START /to END");
        }

        String description = fromSplit[0].trim();
        String from = toSplit[0].trim();
        String to = toSplit[1].trim();
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

    public static void showTaskList(ArrayList<Task> tasks) {
        System.out.println(LINE);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    public static void markTaskAsDone(
            ArrayList<Task> tasks, String input) throws ExiaException {
        String taskNumberText = input.substring(5).trim();

        if (taskNumberText.isEmpty()) {
            throw new ExiaException(
                    "Please tell me which task number to mark as done.");
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(taskNumberText);
        } catch (NumberFormatException e) {
            throw new ExiaException("Task number must be a number.");
        }

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ExiaException(
                    "That task number does not exist.");
        }

        Task task = tasks.get(taskNumber - 1);
        task.markAsDone();

        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        System.out.println(LINE);
    }

    public static void deleteTask(
            ArrayList<Task> tasks, String input) throws ExiaException {
        String taskNumberText = input.substring(6).trim();

        if (taskNumberText.isEmpty()) {
            throw new ExiaException(
                    "Please tell me which task number to delete.");
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(taskNumberText);
        } catch (NumberFormatException e) {
            throw new ExiaException("Task number must be a number.");
        }

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ExiaException(
                    "That task number does not exist.");
        }

        Task removedTask = tasks.remove(taskNumber - 1);

        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask);
        System.out.println(
                "Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void showAddedTask(Task task) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println(LINE);
    }

    public static void showError(String message) {
        System.out.println(LINE);
        System.out.println("OOPS!!! " + message);
        System.out.println(LINE);
    }
}