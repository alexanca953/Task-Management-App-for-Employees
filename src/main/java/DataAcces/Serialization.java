package DataAcces;
import DataModel.Employee;
import DataModel.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Serialization {

    public void serializeEmployee(Employee employee, String file)
    {
        ArrayList<Employee> employees = deserializeListOfEmployee(file);
        if(employees != null)
             employees.add(employee);
        else
        {
            employees = new ArrayList<>();
            employees.add(employee);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error serializing employee: " + e.getMessage());
        }
    }

    public void serializeTask(Task task, String file) {
        ArrayList<Task> tasks = deserializeListOfTasks(file);
        if(tasks != null)
         tasks.add(task);
        else
        {
            tasks = new ArrayList<>();
            tasks.add(task);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error serializing task: " + e.getMessage());
        }
    }

    public Employee deserializeEmployee(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Employee) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing employee: " + e.getMessage());
            return null;
        }
    }
    public Task deserializeTask(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Task) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing task: " + e.getMessage());
            return null;
        }
    }
    public ArrayList<Task> deserializeListOfTasks(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Task>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing task: " + e.getMessage());
            return null;
        }
    }
    public ArrayList<Employee> deserializeListOfEmployee(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Employee>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing employee list: " + e.getMessage());
            return null;
        }
    }
    public void serializeMap(Map<Employee, List<Task>> tasksMap, String file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(tasksMap);
        } catch (IOException e) {
            System.out.println("Error serializing employee: " + e.getMessage());
        }
    }

    public Map<Employee, List<Task>> deSerializeMap(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<Employee, List<Task>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing task: " + e.getMessage());
            return null;
        }
    }

    public void serializeListOfTask(List<Task> tasksList, String file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(tasksList);
        } catch (IOException e) {
            System.out.println("Error serializing TaskList " + e.getMessage());
        }
    }
}