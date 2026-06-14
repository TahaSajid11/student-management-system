package com.studentmanagement.service;

import com.studentmanagement.model.Student;
import com.studentmanagement.dao.StudentDAO;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;
    
    public StudentService() {
        this.studentDAO = new StudentDAO();
    }
    
    /**
     * Add a new student with validation
     * @param student Student object to add
     * @return true if successful
     */
    public boolean registerStudent(Student student) {
        // Validate student data
        if (!validateStudentData(student)) {
            return false;
        }
        return studentDAO.addStudent(student);
    }
    
    /**
     * Retrieve student by ID
     * @param studentId ID of the student
     * @return Student object or null
     */
    public Student findStudentById(int studentId) {
        if (studentId <= 0) {
            System.err.println("Invalid student ID");
            return null;
        }
        return studentDAO.getStudentById(studentId);
    }
    
    /**
     * Retrieve all students
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
    
    /**
     * Update student information
     * @param student Student object with updated information
     * @return true if successful
     */
    public boolean updateStudentInfo(Student student) {
        if (!validateStudentData(student)) {
            return false;
        }
        return studentDAO.updateStudent(student);
    }
    
    /**
     * Delete a student
     * @param studentId ID of the student to delete
     * @return true if successful
     */
    public boolean removeStudent(int studentId) {
        if (studentId <= 0) {
            System.err.println("Invalid student ID");
            return false;
        }
        return studentDAO.deleteStudent(studentId);
    }
    
    /**
     * Get students by major
     * @param major Major of students
     * @return List of students with specified major
     */
    public List<Student> findStudentsByMajor(String major) {
        if (major == null || major.trim().isEmpty()) {
            System.err.println("Major cannot be empty");
            return null;
        }
        return studentDAO.getStudentsByMajor(major);
    }
    
    /**
     * Get student by email
     * @param email Email of the student
     * @return Student object or null
     */
    public Student findStudentByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.err.println("Email cannot be empty");
            return null;
        }
        return studentDAO.getStudentByEmail(email);
    }
    
    /**
     * Get total student count
     * @return Number of students
     */
    public int getTotalStudents() {
        return studentDAO.getTotalStudentCount();
    }
    
    /**
     * Validate student data
     * @param student Student object to validate
     * @return true if valid
     */
    private boolean validateStudentData(Student student) {
        if (student == null) {
            System.err.println("Student object cannot be null");
            return false;
        }
        
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            System.err.println("First name cannot be empty");
            return false;
        }
        
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            System.err.println("Last name cannot be empty");
            return false;
        }
        
        if (student.getEmail() == null || !isValidEmail(student.getEmail())) {
            System.err.println("Email is invalid");
            return false;
        }
        
        if (student.getPhoneNumber() == null || !isValidPhoneNumber(student.getPhoneNumber())) {
            System.err.println("Phone number is invalid");
            return false;
        }
        
        if (student.getGpa() < 0 || student.getGpa() > 4.0) {
            System.err.println("GPA must be between 0 and 4.0");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate email format
     * @param email Email to validate
     * @return true if valid
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
    
    /**
     * Validate phone number format
     * @param phoneNumber Phone number to validate
     * @return true if valid
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Accept phone numbers with 10-13 digits, allowing dashes, spaces, and parentheses
        String phoneRegex = "^[\\d\\s\\-()]{10,13}$";
        return phoneNumber.matches(phoneRegex);
    }
}