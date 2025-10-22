package DataModel;
import BusinessLogic.TaskManagement;
import DataAcces.Serialization;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Employee implements Serializable,Comparable<Employee> {
 private static final long serialVersionUID = 1L;
 private  int idEmployee;
 private String name;

   public Employee(int idEmployee, String name) {
       this.idEmployee = idEmployee;
       this.name = name;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }
    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
    public String toString() {
        return Integer.toString(idEmployee)+" "+name;
    }

   public static Employee findEmployeeById(int idEmployee) {
        Serialization serialization = new Serialization();
        ArrayList<Employee> employees = serialization.deserializeListOfEmployee("EmployeeList.txt");
        if (employees != null) {
            for (Employee emp : employees) {
                if (emp.getIdEmployee() == idEmployee) {
                    return emp;
                }
            }
        }
        return null;
    }

public static void addEmployee(int id,String name)
{
    Serialization serialization = new Serialization();
    Employee employee=new Employee(id,name);
    serialization.serializeEmployee(employee,"EmployeeList.txt");
}

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Employee employee = (Employee) obj;
        return idEmployee == employee.idEmployee;
    }
    @Override
    public int hashCode() {
        return Objects.hash(idEmployee);
    }

    public int compareTo(Employee other) {
        TaskManagement taskManagement = new TaskManagement();
        int thisWorkDuration = taskManagement.calculateEmployeeWorkDuration(this.idEmployee);
        int otherWorkDuration = taskManagement.calculateEmployeeWorkDuration(other.idEmployee);
        return Integer.compare(thisWorkDuration, otherWorkDuration);
    }
}
