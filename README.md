# Student Management System

A comprehensive Java-based Student Management System with MySQL database integration, providing full CRUD (Create, Read, Update, Delete) operations.

## Features

✅ **Add Students** - Register new students with complete information
✅ **View All Students** - Display all students in a formatted table
✅ **Search Functionality** - Search by ID, Email, or Major
✅ **Update Records** - Modify student information
✅ **Delete Records** - Remove students from the system
✅ **Statistics** - View total student count
✅ **Data Validation** - Email, phone number, and GPA validation
✅ **Indexed Database** - Fast queries with proper indexing

## Project Structure

```
Student-Management-System/
├── src/main/java/com/studentmanagement/
│   ├── ui/
│   │   └── StudentManagementApp.java          # Main UI/CLI
│   ├── service/
│   │   └── StudentService.java                # Business logic & validation
│   ├── dao/
│   │   └── StudentDAO.java                    # Data Access Object
│   ├── database/
│   │   └── DatabaseConnection.java            # Database connection management
│   └── model/
│       └── Student.java                       # Student model class
├── database/
│   └── create_database.sql                    # Database setup script
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

## Technology Stack

- **Language:** Java 11+
- **Database:** MySQL
- **Build Tool:** Maven
- **JDBC Driver:** mysql-connector-java 8.0.33

## Prerequisites

- Java 11 or higher
- MySQL Server 5.7 or higher
- Maven 3.6 or higher

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/TahaSajid11/Student-Management-System.git
cd Student-Management-System
```

### 2. Create MySQL Database

```bash
mysql -u root -p < database/create_database.sql
```

Or manually:

```sql
CREATE DATABASE student_management_system;
USE student_management_system;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    enrollment_date DATE NOT NULL,
    major VARCHAR(100) NOT NULL,
    gpa DECIMAL(3, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_major (major),
    INDEX idx_enrollment_date (enrollment_date)
);
```

### 3. Configure Database Connection

Edit `src/main/java/com/studentmanagement/database/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management_system";
private static final String DB_USER = "root";              // Change to your MySQL username
private static final String DB_PASSWORD = "";             // Change to your MySQL password
```

### 4. Build the Project

```bash
mvn clean package
```

### 5. Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.studentmanagement.ui.StudentManagementApp"
```

Or run the compiled JAR:

```bash
java -cp target/StudentManagementSystem-1.0.0.jar:target/lib/* com.studentmanagement.ui.StudentManagementApp
```

## Usage

When you run the application, you'll see a menu with the following options:

### Main Menu

```
========================================
   Student Management System
========================================

--- Main Menu ---
1. Add New Student
2. View All Students
3. Search Student
4. Update Student
5. Delete Student
6. View Statistics
7. Exit
```

### Example Operations

#### Add a New Student

```
Enter your choice: 1

--- Add New Student ---
Enter First Name: John
Enter Last Name: Doe
Enter Email: john.doe@example.com
Enter Phone Number: 123-456-7890
Enter Address: 123 Main Street
Enter Enrollment Date (YYYY-MM-DD): 2024-01-15
Enter Major: Computer Science
Enter GPA (0.0 - 4.0): 3.85

✓ Student added successfully!
```

#### View All Students

```
Enter your choice: 2

--- All Students ---
ID    First Name      Last Name       Email                     Major        GPA
==================================================================================
1     John            Doe             john.doe@example.com      Computer Sc  3.85
2     Jane            Smith           jane.smith@example.com    IT            3.92
```

#### Search Student

```
Enter your choice: 3

--- Search Student ---
1. Search by ID
2. Search by Email
3. Search by Major
Enter your choice: 1
Enter Student ID: 1

Student found:
--------------------------------------------------
Student ID: 1
Name: John Doe
Email: john.doe@example.com
Phone: 123-456-7890
Address: 123 Main Street
Enrollment Date: 2024-01-15
Major: Computer Science
GPA: 3.85
--------------------------------------------------
```

#### Update Student

```
Enter your choice: 4

--- Update Student ---
Enter Student ID: 1

Current student information:
...

Enter new information (leave blank to keep current value):
First Name [John]: Jane
Last Name [Doe]: 
...

✓ Student updated successfully!
```

#### Delete Student

```
Enter your choice: 5

--- Delete Student ---
Enter Student ID: 1

Student to be deleted:
...

Are you sure you want to delete this student? (yes/no): yes

✓ Student deleted successfully!
```

## Database Schema

### Students Table

| Column | Type | Description |
|--------|------|-------------|
| student_id | INT | Primary key, auto-increment |
| first_name | VARCHAR(50) | Student's first name |
| last_name | VARCHAR(50) | Student's last name |
| email | VARCHAR(100) | Unique email address |
| phone_number | VARCHAR(20) | Contact phone number |
| address | VARCHAR(255) | Student's address |
| enrollment_date | DATE | Date of enrollment |
| major | VARCHAR(100) | Field of study |
| gpa | DECIMAL(3,2) | Grade point average |
| created_at | TIMESTAMP | Record creation timestamp |
| updated_at | TIMESTAMP | Last update timestamp |

## Data Validation

The system includes comprehensive validation:

- ✓ **Name Fields:** Cannot be empty
- ✓ **Email:** Must follow valid email format (regex validation)
- ✓ **Phone Number:** 10-13 digits with dashes, spaces, or parentheses
- ✓ **GPA:** Must be between 0.0 and 4.0
- ✓ **Duplicate Email:** Prevented by database unique constraint

## Class Descriptions

### 1. Student.java
Represents a student entity with all personal and academic information.

### 2. DatabaseConnection.java
Manages MySQL database connections using JDBC.

### 3. StudentDAO.java
Data Access Object implementing all CRUD operations:
- `addStudent()` - Insert new student
- `getStudentById()` - Retrieve specific student
- `getAllStudents()` - Fetch all students
- `updateStudent()` - Modify student record
- `deleteStudent()` - Remove student
- `getStudentByEmail()` - Search by email
- `getStudentsByMajor()` - Filter by major
- `getTotalStudentCount()` - Get statistics

### 4. StudentService.java
Business logic layer with data validation:
- `registerStudent()` - Register with validation
- `findStudentById()` - Search with ID validation
- `updateStudentInfo()` - Update with validation
- Input validation methods

### 5. StudentManagementApp.java
Command-line user interface with menu-driven operations.

## Error Handling

The application includes robust error handling:

- Try-catch blocks for all database operations
- Input validation with user feedback
- Graceful error messages
- Connection error management

## Future Enhancements

- 📱 GUI using JavaFX or Swing
- 📊 Generate reports (PDF/Excel)
- 🔍 Advanced search filters
- 📧 Email notifications
- 🔐 User authentication
- 📋 Course management
- 📈 Grade tracking
- 🏫 Multiple classes/sections

## Troubleshooting

### Connection Issues

**Error:** `Communications link failure`

**Solution:**
- Ensure MySQL Server is running
- Check database URL, username, and password in DatabaseConnection.java
- Verify database exists: `SHOW DATABASES;`

### Driver Issues

**Error:** `MySQL JDBC Driver not found!`

**Solution:**
```bash
mvn dependency:resolve
mvn clean install
```

### Email Unique Constraint

**Error:** `Duplicate entry for key 'email'`

**Solution:**
- Enter a unique email address
- Or delete the existing student with that email

## Testing

Run unit tests with Maven:

```bash
mvn test
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/improvement`)
3. Commit your changes (`git commit -m 'Add feature'`)
4. Push to the branch (`git push origin feature/improvement`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Author

**Taha Sajid**
- GitHub: [@TahaSajid11](https://github.com/TahaSajid11)

## Contact & Support

For questions or support:
- Open an issue on GitHub
- Contact the developer
- Check the documentation

## Acknowledgments

- MySQL JDBC Driver documentation
- Java SE 11 documentation
- Maven build system

---

**Happy Coding! 🚀**