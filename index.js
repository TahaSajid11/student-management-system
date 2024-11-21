// Required modules
const express = require('express');
const { Pool } = require('pg');
require('dotenv').config();

const app = express();
const port = process.env.PORT || 3000;

// Set up the PostgreSQL connection pool
const pool = new Pool({
  user: process.env.PG_USER,
  host: process.env.PG_HOST,
  database: process.env.PG_DATABASE,
  password: process.env.PG_PASSWORD,
  port: process.env.PG_PORT || 5432,
});

app.use(express.json());

// Sample route
app.get('/', (req, res) => {
  res.send('Student Management API');
});

// POST /students - Create a new student record
app.post('/students', async (req, res) => {
  const { student_id, first_name, last_name, date_of_birth, email, enrollment_date, courses } = req.body;

  // Validate required fields
  if (!student_id || !first_name || !last_name || !date_of_birth || !email || !enrollment_date) {
    return res.status(400).json({ error: 'Missing required fields' });
  }

  try {
    // Insert the new student into the database
    const result = await pool.query(
      'INSERT INTO students(student_id, first_name, last_name, date_of_birth, email, enrollment_date, courses) VALUES($1, $2, $3, $4, $5, $6, $7) RETURNING *',
      [student_id, first_name, last_name, date_of_birth, email, enrollment_date, JSON.stringify(courses)]
    );
    
    // Send the newly created student as a response
    res.status(201).json({
      message: 'Student created successfully',
      student: result.rows[0],
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to create student' });
  }
});

// GET /students - Retrieve all students
app.get('/students', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM students');
    res.status(200).json(result.rows);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to fetch students' });
  }
});

// GET /students/:student_id - Retrieve a specific student by their student_id
app.get('/students/:student_id', async (req, res) => {
  const { student_id } = req.params;

  try {
    const result = await pool.query('SELECT * FROM students WHERE student_id = $1', [student_id]);

    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Student not found' });
    }

    res.status(200).json(result.rows[0]);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to fetch student' });
  }
});

// PUT /students/:student_id - Update a student's details
app.put('/students/:student_id', async (req, res) => {
  const { student_id } = req.params;
  const { first_name, last_name, date_of_birth, email, enrollment_date, courses } = req.body;

  try {
    const result = await pool.query(
      'UPDATE students SET first_name = $1, last_name = $2, date_of_birth = $3, email = $4, enrollment_date = $5, courses = $6 WHERE student_id = $7 RETURNING *',
      [first_name, last_name, date_of_birth, email, enrollment_date, JSON.stringify(courses), student_id]
    );

    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Student not found' });
    }

    res.status(200).json({
      message: 'Student updated successfully',
      student: result.rows[0],
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to update student' });
  }
});

// DELETE /students/:student_id - Delete a student
app.delete('/students/:student_id', async (req, res) => {
  const { student_id } = req.params;

  try {
    const result = await pool.query('DELETE FROM students WHERE student_id = $1 RETURNING *', [student_id]);

    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Student not found' });
    }

    res.status(200).json({
      message: 'Student deleted successfully',
      student: result.rows[0],
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to delete student' });
  }
});

// Start the server
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
