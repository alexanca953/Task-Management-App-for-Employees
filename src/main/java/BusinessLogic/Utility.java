package BusinessLogic;
import DataAcces.Serialization;
import DataModel.Employee;
import DataModel.Task;
import javax.swing.*;
import java.util.*;

public class Utility {
///order the employees ascending if they have >40 hours of completed work
    public List<Employee> EmployeesWithHighWork() {
        Map<Employee, List<Task>> employeeTasksMap = new Serialization().deSerializeMap("Map.txt");
        if (employeeTasksMap == null) {
            JOptionPane.showMessageDialog(null, "No employees found.");
            return null;
        }
        List<Employee> employeesRes = new ArrayList<>();
        TaskManagement taskManagement = new TaskManagement();
        for (Employee e : employeeTasksMap.keySet()) {
            if (taskManagement.calculateEmployeeWorkDuration(e.getIdEmployee()) > 40)//// && employees_res.stream().noneMatch(emp -> emp.getIdEmployee() == e.getIdEmployee())) {
                employeesRes.add(e);
        }
        if (employeesRes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No employees with more than 40 hours of work.");
            return null;
        }
        Collections.sort(employeesRes);
        return employeesRes;
    }
    ///
    public Map<Employee, Map<String, Integer>> getTaskCompletionStats() {
        Map<Employee, Map<String, Integer>> res = new HashMap<>();
        Serialization s = new Serialization();
        Map<Employee, List<Task>> employeeTaskMap = s.deSerializeMap("Map.txt");
        if (employeeTaskMap == null)
            return res;

        for (Map.Entry<Employee, List<Task>> entry : employeeTaskMap.entrySet())
        { List<Task> tasks = entry.getValue();
            Employee employee = entry.getKey();
            int completed=0,uncomplet=0;
            for (Task task : tasks)
            {
                if ("Completed".equals(task.getStatusTask()))
                    completed++;
                 else if ("Uncompleted".equals(task.getStatusTask()))
                    uncomplet++;
            }
            Map<String, Integer> taskCounts = new HashMap<>();
            taskCounts.put("Completed",completed);
            taskCounts.put("Uncompleted",uncomplet);
            res.put(employee, taskCounts);
        }
        return res;
    }
}
