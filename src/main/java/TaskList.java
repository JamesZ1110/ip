import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task markAsDone(int taskNumber) throws ExiaException {
        Task task = getByNumber(taskNumber);
        task.markAsDone();
        return task;
    }

    public Task delete(int taskNumber) throws ExiaException {
        getByNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    public TaskList find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }

        return new TaskList(matchingTasks);
    }

    private Task getByNumber(int taskNumber) throws ExiaException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ExiaException("That task number does not exist.");
        }

        return tasks.get(taskNumber - 1);
    }
}