package com.studentmanagement.dao;

import com.studentmanagement.model.Student;
import com.studentmanagement.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;
    
    public StudentDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Create a new student record in the database
     * @param student Student object to be added
     * @return true if successful, false otherwise
     */
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (first_name, last_name, email, phone_number, " +
                       "address, enrollment_date, major, gpa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getEnrollmentDate());
            pstmt.setString(7, student.getMajor());
            pstmt.setDouble(8, student.getGpa());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieve a student by ID
     * @param studentId ID of the student
     * @return Student object if found, null otherwise
     */
    public Student getStudentById(int studentId) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Retrieve all students from the database
     * @return List of all Student objects
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    /**
     * Retrieve students by major
     * @param major Major of students
     * @return List of Student objects with the specified major
     */
    public List<Student> getStudentsByMajor(String major) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE major = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, major);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students by major: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    /**
     * Update a student record
     * @param student Student object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET first_name = ?, last_name = ?, email = ?, " +
                       "phone_number = ?, address = ?, enrollment_date = ?, major = ?, gpa = ? " +
                       "WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getEnrollmentDate());
            pstmt.setString(7, student.getMajor());
            pstmt.setDouble(8, student.getGpa());
            pstmt.setInt(9, student.getStudentId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete a student record
     * @param studentId ID of the student to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Search students by email
     * @param email Email of the student
     * @return Student object if found, null otherwise
     */
    public Student getStudentByEmail(String email) {
        String query = "SELECT * FROM students WHERE email = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get count of total students
     * @return Total number of students
     */
    public int getTotalStudentCount() {
        String query = "SELECT COUNT(*) as count FROM students";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting student count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Helper method to extract Student object from ResultSet
     * @param rs ResultSet containing student data
     * @return Student object
     * @throws SQLException
     */
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("student_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("address"),
            rs.getString("enrollment_date"),
            rs.getString("major"),
            rs.getDouble("gpa")
        );
    }
}