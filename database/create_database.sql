-- Create the database
CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;

-- Create students table
CREATE TABLE IF NOT EXISTS students (
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

-- Insert sample data
INSERT INTO students (first_name, last_name, email, phone_number, address, enrollment_date, major, gpa) VALUES
('John', 'Doe', 'john.doe@example.com', '123-456-7890', '123 Main St', '2023-09-01', 'Computer Science', 3.85),
('Jane', 'Smith', 'jane.smith@example.com', '987-654-3210', '456 Oak Ave', '2023-09-01', 'Information Technology', 3.92),
('Michael', 'Johnson', 'michael.j@example.com', '555-123-4567', '789 Pine Rd', '2023-09-02', 'Computer Science', 3.75),
('Emily', 'Williams', 'emily.w@example.com', '555-987-6543', '321 Elm St', '2023-09-02', 'Data Science', 3.88),
('David', 'Brown', 'david.b@example.com', '555-456-7890', '654 Maple Dr', '2023-09-03', 'Information Technology', 3.70);

-- Create index for faster queries
CREATE INDEX idx_gpa ON students(gpa DESC);