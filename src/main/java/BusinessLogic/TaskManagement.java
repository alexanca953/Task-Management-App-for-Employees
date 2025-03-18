package BusinessLogic;
import DataAcces.Serialization;
import DataModel.Employee;
import DataModel.Task;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    Map<Employee, List<Task>> tasksMap;

    public TaskManagement() {
        tasksMap = new HashMap<>();
    }
    public void assignTaskToEmployee(int idEmployee, Task task) {
        tasksMap = new Serialization().deSerializeMap("Map.txt");
        if (tasksMap == null)
            tasksMap = new HashMap<>();
        Employee e = Employee.findEmployeeById(idEmployee);
        if (e == null) {
            JOptionPane.showMessageDialog(null, "Employee not found");
            return;
        }
        if (tasksMap.containsKey(e)==false)
            tasksMap.put(e, new ArrayList<>());
        task.setStatusTask("InProgress");
        tasksMap.get(e).add(task);
        new Serialization().serializeMap(tasksMap, "Map.txt");
    }

    public void modifyTaskStatus(int idEmployee, int idTask, String status) {
        Employee e = Employee.findEmployeeById(idEmployee);
        Serialization serialization = new Serialization();
        if (e == null) {
            JOptionPane.showMessageDialog(null, "Employee not found");
            return;
        }
        Map<Employee, List<Task>> tasksMap = serialization.deSerializeMap("Map.txt");
        if (tasksMap == null) {
            JOptionPane.showMessageDialog(null, "No tasks assigned to any employee.");
            return;
        }
        boolean taskFound = false;
        for (Map.Entry<Employee, List<Task>> entry : tasksMap.entrySet()) {
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();
            if (employee.getIdEmployee() == idEmployee) {
                for (Task task : tasks) {
                    if (task.getIdTask() == idTask) {
                        task.setStatusTask(status);
                        taskFound = true;
                        break;
                    }
                }}
            if (taskFound) {
                serialization.serializeMap(tasksMap, "Map.txt");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Task not found.");
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        int sum = 0;
        Employee e = Employee.findEmployeeById(idEmployee);
        if (e == null) {
            JOptionPane.showMessageDialog(null, "Employee not found");
            return 0;
        }
        Map<Employee, List<Task>> tasks_map = new Serialization().deSerializeMap("Map.txt");
        for (Map.Entry<Employee, List<Task>> entry : tasks_map.entrySet()) {
            if (entry.getKey().getIdEmployee() == idEmployee) {
                if (entry.getValue() == null) {
                    return 0;
                }
                for (Task task : entry.getValue()) {
                    if ("Completed".equals(task.getStatusTask())) {
                        sum += task.estimateDuration();
                    }
                }
                return sum;
            }
        }
        return 0;
    }

}




