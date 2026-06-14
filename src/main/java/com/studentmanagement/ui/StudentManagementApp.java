package com.studentmanagement.ui;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentService;
import java.util.List;
import java.util.Scanner;

public class StudentManagementApp {
    private StudentService studentService;
    private Scanner scanner;
    
    public StudentManagementApp() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Main menu for the application
     */
    public void start() {
        System.out.println("\n========================================");
        System.out.println("   Student Management System");
        System.out.println("========================================\n");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    System.out.println("\nThank you for using Student Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. View Statistics");
        System.out.println("7. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    /**
     * Add a new student
     */
    private void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        
        try {
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine().trim();
            
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine().trim();
            
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine().trim();
            
            System.out.print("Enter Address: ");
            String address = scanner.nextLine().trim();
            
            System.out.print("Enter Enrollment Date (YYYY-MM-DD): ");
            String enrollmentDate = scanner.nextLine().trim();
            
            System.out.print("Enter Major: ");
            String major = scanner.nextLine().trim();
            
            System.out.print("Enter GPA (0.0 - 4.0): ");
            double gpa = getDoubleInput();
            
            Student student = new Student(firstName, lastName, email, phoneNumber,
                                        address, enrollmentDate, major, gpa);
            
            if (studentService.registerStudent(student)) {
                System.out.println("\n✓ Student added successfully!");
            } else {
                System.out.println("\n✗ Failed to add student. Please check your input.");
            }
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    /**
     * View all students
     */
    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        printStudentTable(students);
    }
    
    /**
     * Search for a student
     */
    private void searchStudent() {
        System.out.println("\n--- Search Student ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Email");
        System.out.println("3. Search by Major");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                searchById();
                break;
            case 2:
                searchByEmail();
                break;
            case 3:
                searchByMajor();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void searchById() {
        System.out.print("Enter Student ID: ");
        int studentId = getIntInput();
        
        Student student = studentService.findStudentById(studentId);
        if (student != null) {
            System.out.println("\nStudent found:");
            printStudentDetails(student);
        } else {
            System.out.println("\nStudent not found.");
        }
    }
    
    private void searchByEmail() {
        System.out.print("Enter Student Email: ");
        String email = scanner.nextLine().trim();
        
        Student student = studentService.findStudentByEmail(email);
        if (student != null) {
            System.out.println("\nStudent found:");
            printStudentDetails(student);
        } else {
            System.out.println("\nStudent not found.");
        }
    }
    
    private void searchByMajor() {
        System.out.print("Enter Major: ");
        String major = scanner.nextLine().trim();
        
        List<Student> students = studentService.findStudentsByMajor(major);
        if (students != null && !students.isEmpty()) {
            System.out.println("\nStudents with major " + major + ":");
            printStudentTable(students);
        } else {
            System.out.println("\nNo students found with major: " + major);
        }
    }
    
    /**
     * Update student information
     */
    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter Student ID: ");
        int studentId = getIntInput();
        
        Student student = studentService.findStudentById(studentId);
        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }
        
        System.out.println("\nCurrent student information:");
        printStudentDetails(student);
        
        System.out.println("\nEnter new information (leave blank to keep current value):");
        
        String firstName = getOptionalInput("First Name ["+student.getFirstName()+ "]: ");
        if (!firstName.isEmpty()) student.setFirstName(firstName);
        
        String lastName = getOptionalInput("Last Name ["+student.getLastName()+ "]: ");
        if (!lastName.isEmpty()) student.setLastName(lastName);
        
        String email = getOptionalInput("Email ["+student.getEmail()+ "]: ");
        if (!email.isEmpty()) student.setEmail(email);
        
        String phoneNumber = getOptionalInput("Phone Number ["+student.getPhoneNumber()+ "]: ");
        if (!phoneNumber.isEmpty()) student.setPhoneNumber(phoneNumber);
        
        String address = getOptionalInput("Address ["+student.getAddress()+ "]: ");
        if (!address.isEmpty()) student.setAddress(address);
        
        String major = getOptionalInput("Major ["+student.getMajor()+ "]: ");
        if (!major.isEmpty()) student.setMajor(major);
        
        String gpaStr = getOptionalInput("GPA ["+student.getGpa()+ "]: ");
        if (!gpaStr.isEmpty()) {
            try {
                student.setGpa(Double.parseDouble(gpaStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. Keeping current value.");
            }
        }
        
        if (studentService.updateStudentInfo(student)) {
            System.out.println("\n✓ Student updated successfully!");
        } else {
            System.out.println("\n✗ Failed to update student.");
        }
    }
    
    /**
     * Delete a student
     */
    private void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID: ");
        int studentId = getIntInput();
        
        Student student = studentService.findStudentById(studentId);
        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }
        
        System.out.println("\nStudent to be deleted:");
        printStudentDetails(student);
        
        System.out.print("\nAre you sure you want to delete this student? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("yes")) {
            if (studentService.removeStudent(studentId)) {
                System.out.println("\n✓ Student deleted successfully!");
            } else {
                System.out.println("\n✗ Failed to delete student.");
            }
        } else {
            System.out.println("\nDeletion cancelled.");
        }
    }
    
    /**
     * Display statistics
     */
    private void displayStatistics() {
        System.out.println("\n--- Statistics ---");
        int totalStudents = studentService.getTotalStudents();
        System.out.println("Total Students: " + totalStudents);
    }
    
    /**
     * Print student table
     */
    private void printStudentTable(List<Student> students) {
        System.out.println(String.format("\n%-5s %-15s %-15s %-25s %-12s %-10s",
                "ID", "First Name", "Last Name", "Email", "Major", "GPA"));
        System.out.println("=".repeat(82));
        
        for (Student student : students) {
            System.out.println(String.format("%-5d %-15s %-15s %-25s %-12s %-10.2f",
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getMajor(),
                    student.getGpa()));
        }
    }
    
    /**
     * Print detailed student information
     */
    private void printStudentDetails(Student student) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + student.getPhoneNumber());
        System.out.println("Address: " + student.getAddress());
        System.out.println("Enrollment Date: " + student.getEnrollmentDate());
        System.out.println("Major: " + student.getMajor());
        System.out.println("GPA: " + student.getGpa());
        System.out.println("-".repeat(50));
    }
    
    /**
     * Get integer input from user
     */
    private int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1;
        }
    }
    
    /**
     * Get double input from user
     */
    private double getDoubleInput() {
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1.0;
        }
    }
    
    /**
     * Get optional input from user
     */
    private String getOptionalInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        StudentManagementApp app = new StudentManagementApp();
        app.start();
    }
}