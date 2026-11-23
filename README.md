# AttendanceEase – Student Attendance Management System

A desktop-based Student Attendance Management System developed using **Java Swing, JDBC, and Supabase PostgreSQL**.

1. Project Overview

AttendanceEase is a Java-based desktop application designed to simplify student attendance management.
The application provides a graphical user interface for accessing student information and managing attendance records. It uses Java Swing for the frontend and JDBC to establish connectivity with a PostgreSQL database hosted on Supabase.
The project demonstrates the practical implementation of Java programming, object-oriented concepts, GUI development, and database connectivity.

 2. Key Features

- **Student Management:** Provides functionality to view and manage student information.
- **Attendance Management:** Supports recording and viewing student attendance.
- **Interactive GUI:** Provides a user-friendly desktop interface using Java Swing.
- **Database Integration:** Connects to a cloud-hosted PostgreSQL database through JDBC.
- **Modular Structure:** Separates user interface, database connectivity, and management operations into different Java classes.

 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java JDK 21 | Core programming language |
| Java Swing | Graphical User Interface |
| JDBC | Database connectivity |
| PostgreSQL | Relational database |
| Supabase | Cloud database hosting |
| PostgreSQL JDBC Driver 42.7.14 | Java-PostgreSQL communication |
| Kiro IDE | Development environment |

 📂 4. Project Structure

```text
AttendanceEase/
│
├── Attendance.java
├── AttendanceGUI.java
├── AttendanceManager.java
├── DBConnection.java
├── Main.java
├── Student.java
├── StudentManager.java
├── TakeAttendanceScreen.java
│
├── postgresql-42.7.14.jar
├── .gitignore
└── README.md
```

### Java File Descriptions

| File | Description |
|---|---|
| Attendance.java | Represents attendance-related data. |
| AttendanceGUI.java | Implements the main application interface. |
| AttendanceManager.java | Handles attendance-related operations. |
| DBConnection.java | Establishes a connection with the PostgreSQL database. |
| Main.java | Application entry point. |
| Student.java | Represents student information. |
| StudentManager.java | Handles student management operations. |
| TakeAttendanceScreen.java | Provides the interface for taking attendance. |

5. System Architecture

AttendanceEase follows a layered application structure:

**1. Presentation Layer**
- Developed using Java Swing.
- Handles user interaction through graphical components.

**2. Application Layer**
- Contains StudentManager and AttendanceManager.
- Handles student and attendance-related operations.

**3. Database Connectivity Layer**
- Uses JDBC to communicate with PostgreSQL.
- Connection logic is implemented in DBConnection.java.

**4. Database Layer**
- Uses Supabase-hosted PostgreSQL.
- Stores application data.

### Application Workflow

```text
       USER
         |
         v
    Java Swing GUI
         |
         v
  Management Classes
         |
         v
    JDBC Connection
         |
         v
  Supabase PostgreSQL
```

6. Prerequisites

Before running the application, ensure that you have:

- Java Development Kit (JDK) 21.
- Kiro IDE or another Java-compatible IDE.
- PostgreSQL JDBC Driver 42.7.14.
- A configured Supabase PostgreSQL database.
- Git (optional, for cloning the repository).

 7. Installation and Execution

Follow these steps to run the project locally.

### Step 1: Clone the Repository

```bash
git clone https://github.com/Jagruti-gits/AttendanceEase.git
```

### Step 2: Navigate to the Project Directory

```bash
cd AttendanceEase
```

### Step 3: Configure the Database Password

The application retrieves its database password using the `DB_PASSWORD` environment variable.

For Windows PowerShell:

```powershell
$env:DB_PASSWORD="YOUR_DATABASE_PASSWORD"
```

Replace `YOUR_DATABASE_PASSWORD` with your actual Supabase database password.

### Step 4: Compile the Java Files

```bash
javac -cp "postgresql-42.7.14.jar" *.java
```

### Step 5: Run the Application

```bash
java -cp ".;postgresql-42.7.14.jar" AttendanceGUI
```

The Java Swing application should launch once the database connection is configured correctly.

**Note:** The above commands are intended for Windows PowerShell. Ensure that your Supabase database is accessible and the connection details are correctly configured.

 8. Database Connectivity

The application uses JDBC (Java Database Connectivity) to communicate with PostgreSQL.

Supabase provides the cloud-hosted PostgreSQL database used by the application.

The database connection process involves:

1. Configuring the PostgreSQL connection details.
2. Retrieving the database password from an environment variable.
3. Establishing a connection using JDBC.
4. Executing database operations through the management classes.
5. Handling database-related exceptions.

9. Security Considerations

- Database passwords are retrieved through environment variables.
- Sensitive credentials should never be committed to GitHub.
- The `.gitignore` file excludes compiled `.class` files and environment-specific files.
- Database credentials should be configured separately on each system running the application.

 10. Learning Outcomes

This project provides practical experience in:

- Java programming and Object-Oriented Programming.
- Java Swing GUI development.
- JDBC database connectivity.
- PostgreSQL database integration.
- Exception handling.
- Git and GitHub version control.
- Integrating a desktop application with a cloud database.

 11. Conclusion

AttendanceEase demonstrates how Java Swing, JDBC, and Supabase PostgreSQL can be integrated to develop a database-connected desktop application.

The project provides a practical approach to understanding desktop application development, database management, and software integration using Java.

---

->Project Information

| Detail | Information |
|---|---|
| Project Name | AttendanceEase |
| Project Type | Project-Based Learning (PBL) |
| Programming Language | Java |
| Frontend | Java Swing |
| Database | PostgreSQL |
| Database Platform | Supabase |
| Connectivity | JDBC |
| IDE | Kiro |
| Java Version | JDK 21 |

**GitHub Repository:** https://github.com/Jagruti-gits/AttendanceEase

**Developed as an academic Project-Based Learning (PBL) project.**
