package GraphicalUserInterface;
import BusinessLogic.TaskManagement;
import BusinessLogic.Utility;
import DataAcces.Serialization;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.SimpleTask;
import DataModel.Task;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ProjectManager extends JFrame {
    TaskManagement tm = new TaskManagement();
    private JPanel topPanel;
    private JLabel label;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;

    public ProjectManager() {
        setTitle("Project Manager");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        topPanel = new JPanel();
        label = new JLabel("Project Manager");
        label.setBackground(Color.LIGHT_GRAY);
        topPanel.add(label);

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(9, 1, 20, 20));

        button1 = new JButton("Add Employee");
        button2 = new JButton("Add Task");
        button3 = new JButton("View Employee List");
        button4 = new JButton("View Task List");
        button5 = new JButton("Assign task");
        button7= new JButton("Calculate work duration");
        button6 = new JButton("Modify task status");
        button8= new JButton("Order Employees");
        button9=new JButton("Stats");
        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);
        leftPanel.add(button4);
        leftPanel.add(button5);
        leftPanel.add(button6);
        leftPanel.add(button7);
        leftPanel.add(button8);
        leftPanel.add(button9);

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setLayout(new GridLayout(2, 1, 100, 50));
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
        ///Add employe
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = 0;
                String nume = JOptionPane.showInputDialog(topPanel, "Enter Employee Name", "Employee Name", JOptionPane.PLAIN_MESSAGE);
                if (nume != null) {
                    File file = new File("LastIdEmployee.txt");
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line = reader.readLine();
                        if (line != null && !line.trim().isEmpty()) {
                            id = Integer.parseInt(line.trim());
                            id++;
                        }
                    } catch (NumberFormatException | IOException ex) {
                        ex.printStackTrace();
                    }
                    Employee.addEmployee(id, nume);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(String.valueOf(id));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Add a simple task?",
                        "Task Type Selection",
                        JOptionPane.YES_NO_OPTION);
                int id=0;
                final int[] ok = {0};
                File file = new File("LastIdTask.txt");
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null && !line.trim().isEmpty()) {
                        id = Integer.parseInt(line.trim());
                        id++;
                    }
                } catch (NumberFormatException | IOException ex) {
                    ex.printStackTrace();
                }
                if (option == JOptionPane.YES_OPTION) {
                    String name = JOptionPane.showInputDialog("Give the name");
                    int start = Integer.parseInt(JOptionPane.showInputDialog("Give start hour"));
                    int end = Integer.parseInt(JOptionPane.showInputDialog("Give end hour"));
                    SimpleTask stask = new SimpleTask(start, end, id, name);
                    Serialization s = new Serialization();
                    s.serializeTask(stask, "Task.txt");
                    ok[0] =1;

                } else if (option == JOptionPane.NO_OPTION) {
                    JFrame frame2 = new JFrame("Select Tasks for Complex Task");
                    frame2.setSize(500, 400);
                    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame2.setLocationRelativeTo(null);
                    frame2.setLayout(new BorderLayout());
                    JPanel taskPanel = new JPanel();
                    taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
                    ArrayList<Task> tasks = new Serialization().deserializeListOfTasks("Task.txt");
                    ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
                    if (tasks != null) {
                        for (Task task : tasks) {
                            JCheckBox checkBox = new JCheckBox(task.getName());
                            checkBoxes.add(checkBox);
                            taskPanel.add(checkBox);
                        }
                    }
                    JScrollPane scrollPane = new JScrollPane(taskPanel);
                    frame2.add(scrollPane, BorderLayout.CENTER);
                    JButton confirmButton = new JButton("Confirm Selection");
                    frame2.add(confirmButton, BorderLayout.SOUTH);
                    int finalId = id;
                    confirmButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String name = JOptionPane.showInputDialog("Give the name for Complex Task");
                            ComplexTask ctask = new ComplexTask(name, finalId);
                            for (int i = 0; i < checkBoxes.size(); i++) {
                                if (checkBoxes.get(i).isSelected()) {
                                    ctask.addTask(tasks.get(i));
                                }
                            }
                            Serialization s = new Serialization();
                            s.serializeTask(ctask, "Task.txt");
                            JOptionPane.showMessageDialog(frame2, "Complex Task created successfully!");
                            frame2.dispose();
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                writer.write(String.valueOf(finalId));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    frame2.setVisible(true);
                }
                if(ok[0]==1)
                {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(String.valueOf(id));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Id");
                model.addColumn("Name");
                JTable table = new JTable(model);
                ArrayList<Employee> res = new Serialization().deserializeListOfEmployee("EmployeeList.txt");
                if (res != null) {
                    for (Employee employee : res) {
                            model.addRow(new Object[]{employee.getIdEmployee(), employee.getName()});
                    }
                }
                JScrollPane scrollPane = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(new Dimension(500, 100));
                rightPanel.removeAll();
                rightPanel.add(scrollPane, BorderLayout.CENTER);
                rightPanel.revalidate();
                rightPanel.repaint();
                rightPanel.setVisible(true);
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("IdTask");
                model.addColumn("NameTask");
                model.addColumn("StartHour");
                model.addColumn("EndHour");
                model.addColumn("Duration");
                JTable table = new JTable(model);
                DefaultTableModel model2 = new DefaultTableModel();
                model2.addColumn("IdTask");
                model2.addColumn("NameTask");
                model2.addColumn("List of tasks");
                model2.addColumn("Duration");
                JTable table2 = new JTable(model2);
                ArrayList<Task> res = new Serialization().deserializeListOfTasks("Task.txt");
                if (res != null) {
                    for (Task task : res) {
                        if (task instanceof SimpleTask) {
                            SimpleTask simpleTask = (SimpleTask) task;
                            model.addRow(new Object[]{
                                    simpleTask.getIdTask(),
                                    simpleTask.getName(),
                                    simpleTask.getStartHour(),
                                    simpleTask.getEndHour(),
                                    simpleTask.estimateDuration()
                            });
                        } else if (task instanceof ComplexTask) {
                            ComplexTask complexTask = (ComplexTask) task;
                            model2.addRow(new Object[]{
                                    complexTask.getIdTask(),
                                    complexTask.getName(),
                                    complexTask.toString(),
                                    complexTask.estimateDuration()
                            });
                        }
                    }
                }
                JPanel mainPanel = new JPanel(new BorderLayout());
                JPanel tablePanel = new JPanel();
                tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(new Dimension(500, 150));
                tablePanel.add(scrollPane);
                tablePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                JScrollPane scrollPane2 = new JScrollPane(table2);
                table2.setPreferredScrollableViewportSize(new Dimension(500, 150));
                tablePanel.add(scrollPane2);
                mainPanel.add(tablePanel, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton delTask = new JButton("Delete Task");
                JButton editTask = new JButton("Edit Complex Task");
                buttonPanel.add(delTask);
                buttonPanel.add(editTask);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);
                rightPanel.removeAll();
                rightPanel.add(mainPanel);
                rightPanel.revalidate();
                rightPanel.repaint();
                rightPanel.setVisible(true);
                delTask.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int delId = Integer.parseInt(JOptionPane.showInputDialog("Give id of task to delete"));
                        ArrayList<Task> tasks = new Serialization().deserializeListOfTasks("Task.txt");
                        if (tasks != null) {
                            Task taskToRemove = null;
                            for (Task task : tasks) {
                                if (task.getIdTask() == delId) {
                                    taskToRemove = task;
                                    break;
                                }
                            }
                            if (taskToRemove != null) {
                                tasks.remove(taskToRemove);
                                for (Task task : tasks) {
                                    if (task instanceof ComplexTask) {
                                        ComplexTask complexTask = (ComplexTask) task;
                                        complexTask.getTasks().remove(taskToRemove);
                                    }
                                }
                                new Serialization().serializeListOfTask(tasks, "Task.txt");
                                JOptionPane.showMessageDialog(rightPanel, "Task deleted successfully!");
                                button4.doClick();
                            } else {
                                JOptionPane.showMessageDialog(rightPanel, "Task with ID " + delId + " not found.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(rightPanel, "No tasks available.");
                        }
                    }
                });
                editTask.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int editId = Integer.parseInt(JOptionPane.showInputDialog("Give id of complex task to edit"));
                        ArrayList<Task> tasks = new Serialization().deserializeListOfTasks("Task.txt");
                        if (tasks == null || tasks.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No tasks available.");
                            return;
                        }
                        ComplexTask complexTaskToEdit = null;
                        for (Task task : tasks) {
                            if (task instanceof ComplexTask && task.getIdTask() == editId) {
                                complexTaskToEdit = (ComplexTask) task;
                                break;
                            }
                        }
                        if (complexTaskToEdit == null) {
                            JOptionPane.showMessageDialog(null, "Complex Task not found.");
                            return;
                        }
                        JFrame editFrame = new JFrame("Edit Complex Task");
                        editFrame.setSize(500, 400);
                        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        editFrame.setLocationRelativeTo(null);
                        editFrame.setLayout(new BorderLayout());

                        JPanel taskPanel = new JPanel();
                        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
                        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

                        for (Task task : tasks) {
                            if (task != complexTaskToEdit) {
                                JCheckBox checkBox = new JCheckBox(task.getName());
                                checkBox.setSelected(complexTaskToEdit.getTasks().contains(task));
                                checkBoxes.add(checkBox);
                                taskPanel.add(checkBox);
                            }
                        }
                        JScrollPane scrollPane = new JScrollPane(taskPanel);
                        editFrame.add(scrollPane, BorderLayout.CENTER);
                        JButton saveButton = new JButton("Save Changes");
                        editFrame.add(saveButton, BorderLayout.SOUTH);
                        ComplexTask finalComplexTaskToEdit = complexTaskToEdit;

                        saveButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                finalComplexTaskToEdit.getTasks().clear();

                                for (int i = 0; i < checkBoxes.size(); i++) {
                                    if (checkBoxes.get(i).isSelected()) {
                                        finalComplexTaskToEdit.addTask(tasks.get(i));
                                    }
                                }
                                new Serialization().serializeListOfTask(tasks, "Task.txt");
                                JOptionPane.showMessageDialog(editFrame, "Complex Task updated successfully!");
                                editFrame.dispose();
                                button4.doClick();
                            }
                        });
                        editFrame.setVisible(true);
                    }
                });
            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Employee> employees =new Serialization().deserializeListOfEmployee("EmployeeList.txt");
                ArrayList<Task> tasks =new Serialization().deserializeListOfTasks("Task.txt");

                if (employees != null && tasks != null && !tasks.isEmpty()) {
                    JComboBox<Employee> employeeComboBox = new JComboBox<>();
                    for (Employee employee : employees) {
                        employeeComboBox.addItem(employee);
                    }
                    JComboBox<Task> taskComboBox = new JComboBox<>();
                    for (Task task : tasks) {
                            taskComboBox.addItem(task);
                        }
//                    if (taskComboBox.getItemCount() == 0) {
//                        JOptionPane.showMessageDialog(null, "No unassigned tasks available.");
//                        return; // Exit the method if no tasks are available
//                    }
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new JLabel("Select Employee:"));
                    panel.add(employeeComboBox);
                    panel.add(new JLabel("Select Task:"));
                    panel.add(taskComboBox);

                    int option = JOptionPane.showConfirmDialog(null, panel, "Assign Task to Employee", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();
                        Task selectedTask = (Task) taskComboBox.getSelectedItem();
                        if (selectedEmployee != null && selectedTask != null) {
                            TaskManagement taskManagement = new TaskManagement();
                            taskManagement.assignTaskToEmployee(selectedEmployee.getIdEmployee(), selectedTask);
//                       for(Task task : tasks)
//                       {
//                           if(selectedTask.getIdTask() == task.getIdTask())
//                           ///    task.setStatusTask("InProgress");
//                       }
                      //  new Serialization().serializeListOfTask(tasks,"Task.txt");
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select both an employee and a task.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No employees or tasks available.");
                }
            }
        });
        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<Employee, java.util.List<Task>>  tasks_map = new Serialization().deSerializeMap("Map.txt");

                if (tasks_map != null && !tasks_map.isEmpty()) {
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("Employee ID");
                    model.addColumn("Employee Name");
                    model.addColumn("Task ID");
                    model.addColumn("Task Name");
                    model.addColumn("Status");

                    JTable table = new JTable(model);

                    for (Map.Entry<Employee, List<Task>> entry : tasks_map.entrySet()) {
                        Employee employee = entry.getKey();
                        List<Task> tasks = entry.getValue();

                        for (Task task : tasks) {
                            model.addRow(new Object[]{
                                    employee.getIdEmployee(),
                                    employee.getName(),
                                    task.getIdTask(),
                                    task.getName(),
                                    task.getStatusTask()
                            });
                        }
                    }

                    JScrollPane scrollPane = new JScrollPane(table);
                    table.setPreferredScrollableViewportSize(new Dimension(500, 150));

                    JPanel statusPanel = new JPanel();
                    statusPanel.setLayout(new FlowLayout());
                    statusPanel.add(new JLabel("Select Status for Task"));

                    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Uncompleted", "In progress", "Completed"});
                    statusPanel.add(statusComboBox);

                    JButton modifyStatusButton = new JButton("Modify Status");
                    statusPanel.add(modifyStatusButton);

                    modifyStatusButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow != -1) {
                                int employeeId = (Integer) table.getValueAt(selectedRow, 0);
                                int taskId = (Integer) table.getValueAt(selectedRow, 2);
                                String selectedStatus = (String) statusComboBox.getSelectedItem();

                                Employee selectedEmployee = Employee.findEmployeeById(employeeId);
                                if (selectedEmployee != null) {
                                    TaskManagement taskManagement = new TaskManagement();
                                    taskManagement.modifyTaskStatus(employeeId, taskId, selectedStatus);
                                    table.setValueAt(selectedStatus, selectedRow, 4);
                                    JOptionPane.showMessageDialog(statusPanel, "Status updated successfully!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(statusPanel, "Please select a task to modify.");
                            }
                        }
                    });
                    rightPanel.removeAll();
                    rightPanel.add(scrollPane, BorderLayout.CENTER);
                    rightPanel.add(statusPanel, BorderLayout.SOUTH);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                    rightPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No tasks or employees found.");
                }
            }
        });

        button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = new JComboBox();
               ArrayList<Employee> employees = new Serialization().deserializeListOfEmployee("EmployeeList.txt");
                if(employees!=null)
               for(Employee employee : employees) {
                   comboBox.addItem(employee);
               }
               comboBox.setVisible(true);
                 JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                 panel.add(new JLabel("Select Employee:"));
                 panel.add(comboBox);
                rightPanel.removeAll();
                rightPanel.add(panel);
                rightPanel.revalidate();
                rightPanel.repaint();
                rightPanel.setVisible(true);
                comboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Employee e2= (Employee) comboBox.getSelectedItem();
                        System.out.println(e2.toString());
                     int val=tm.calculateEmployeeWorkDuration( e2.getIdEmployee());

                     JOptionPane.showMessageDialog(rightPanel, "Employee Work Duration: "+val);
                    }
                });
            }
        });

button8.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        ArrayList <Employee> employees = (ArrayList<Employee>) new Utility().EmployeesWithHighWork();
        if (employees == null || employees.isEmpty()) {
            return;
        }
        JFrame frame3 = new JFrame();
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3.setTitle("Employees work:");
        frame3.setVisible(true);
        frame3.setSize(new Dimension(500, 150));
        JPanel panelUt= new JPanel();
        panelUt.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelUt.setVisible(true);
        frame3.add(panelUt);
        for(Employee employee : employees)
        {
            panelUt.add(new JLabel(employee.getName()+tm.calculateEmployeeWorkDuration(employee.getIdEmployee())));
        }
    }
});
        button9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<Employee, Map<String, Integer>> tasksMap = new Utility().getTaskCompletionStats();

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Employee Name");
                model.addColumn("Completed Tasks");
                model.addColumn("Uncompleted Tasks");

                for (Employee employee : tasksMap.keySet()) {
                    Map<String, Integer> taskCounts = tasksMap.get(employee);
                    model.addRow(new Object[]{
                            employee.getName(),
                            taskCounts.get("Completed"),
                            taskCounts.get("Uncompleted")
                    });
                }
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(new Dimension(500, 150));
                rightPanel.removeAll(); // Curăță panoul din dreapta
                rightPanel.add(scrollPane, BorderLayout.CENTER); // Adaugă tabelul în panoul din dreapta
                rightPanel.revalidate(); // Reîmprospătează panoul
                rightPanel.repaint(); // Redesenează panoul
            }
        });
    }
    public static void main(String[] args) {
        new ProjectManager();
    }
}