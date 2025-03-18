package DataModel;
import java.util.ArrayList;

public class ComplexTask extends Task {
   private ArrayList<Task> tasks;
    public ComplexTask(String name, int id) {
        super(id,name);
        tasks = new ArrayList<>();
    }
    public int estimateDuration() {
        cleanTasks();
        int sum = 0;
        for (Task task : tasks) {
            if (task != null) {
                sum += task.estimateDuration();
            }
        }
        return sum;
    }
    public void addTask(Task task) {
        if (task == this) {
            return;
        }
        if (tasks.contains(task)) {
            return;
        }
        tasks.add(task);
    }
    public void deleteTask(Task task)
    {
        tasks.remove(task);
    }
    public ArrayList<Task> getTasks()
    {
        return tasks;
    }

//    public String toString() {
//        StringBuilder res = new StringBuilder();
//        res.append(this.getIdTask()).append(":");
//        for (Task task : tasks) {
//            res.append(task.getName()).append(",");
//        }
//        return res.toString();
//    }
    public void cleanTasks() {
        tasks.removeIf(task->task == null);
    }
    @Override
    public String toString() {
        String res=new String();
        res+=this.getIdTask()+":";
        for(Task task : tasks)
        {
            res=res+task.getName()+",";
        }
        return res;
    }
}
