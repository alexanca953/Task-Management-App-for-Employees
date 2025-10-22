# Java Task Management Application

This is a desktop application built in Java, using **Swing** for the (GUI), designed to manage employees and their assigned tasks. It uses **Java Object Serialization** for data persistence and implements the **Composite** design pattern for modeling tasks.

## Key Features

### Project Architecture & Logic
The application employs a layered architecture:
* **Data Model:** Contains core entities like `Employee`, `Task` (abstract), `SimpleTask`, and `ComplexTask`.
* **Data Access:** Handles data persistence via `Serialization`, managing files like `Map.txt` and `EmployeeList.txt`.
* **Business Logic:** Includes `TaskManagement` (assignment, status modification) and `Utility` (reporting, statistics).
* **GUI:** The `ProjectManager` class provides the interactive Swing interface.

### Management & Monitoring
* **Employee & Task Creation:** Register employees and create both **Simple Tasks** (time-based) and hierarchical **Complex Tasks** (groups of sub-tasks).
* **Assignment & Status:** Assign tasks to employees and modify their status (e.g., "InProgress", "Completed").
* **Reporting:** Calculate an employee's total **Completed** work duration and generate reports (e.g., employees with $>40$ hours of completed work, sorted ascending).
* **Statistics:** Display task completion metrics (Completed vs. Uncompleted counts) per employee.

## Project Structure & Technology
* **Language/GUI:** Java / Swing (`javax.swing`).
* **Persistence:** Data is stored locally using Java Object Serialization.
* **Design:** Utilizes the **Composite Pattern** for task representation.
