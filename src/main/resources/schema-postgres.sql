CREATE TABLE candidates (
    candidate_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    position_applied VARCHAR(100),
    job_details TEXT,
    manager_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (manager_id) REFERENCES USERS(user_id)
);

CREATE TABLE Interview_Rounds (
    round_id SERIAL PRIMARY KEY,
    round_name VARCHAR(100),
    description TEXT
);

CREATE TABLE Interviewers (
    interviewer_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    department VARCHAR(100)
);

CREATE TABLE Candidate_Interviews (
    candidate_interview_id SERIAL PRIMARY KEY,
    candidate_id INT,
    round_id INT,
    scheduled_at TIMESTAMP,
    feedback TEXT,
    status VARCHAR(20) CHECK (status IN ('Pending', 'In progress', 'Completed', 'Selected', 'Rejected')) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (candidate_id) REFERENCES Candidates(candidate_id),
    FOREIGN KEY (round_id) REFERENCES Interview_Rounds(round_id)
);

CREATE TABLE Candidate_Interviewers (
    id SERIAL PRIMARY KEY,
    candidate_interview_id INT NOT NULL,
    interviewer_id INT NOT NULL,

    FOREIGN KEY (candidate_interview_id) REFERENCES Candidate_Interviews(candidate_interview_id),
    FOREIGN KEY (interviewer_id) REFERENCES Interviewers(interviewer_id)
);


-- Dummy data for Candidates
INSERT INTO candidates (name, email, phone, position_applied, job_details, manager_id) VALUES
('John Doe', 'john.doe@email.com', '1234567890', 'Software Engineer', 'Looking for a challenging role in backend development', 1),
('Jane Smith', 'jane.smith@email.com', '2345678901', 'Product Manager', 'Experienced in Agile methodologies and team leadership', 1),
('Mike Johnson', 'mike.johnson@email.com', '3456789012', 'Data Scientist', 'Expertise in machine learning and big data analytics', 1),
('Emily Brown', 'emily.brown@email.com', '4567890123', 'UX Designer', 'Passionate about creating intuitive user interfaces', 1),
('Chris Lee', 'chris.lee@email.com', '5678901234', 'DevOps Engineer', 'Skilled in CI/CD pipelines and cloud infrastructure', 1);

-- Dummy data for InterviewRounds
INSERT INTO Interview_Rounds (round_name, description) VALUES
('Technical Screen', 'Initial technical assessment to evaluate basic skills'),
('Coding Challenge', 'In-depth coding exercise to assess problem-solving abilities'),
('System Design', 'Discussion on designing scalable and efficient systems');

-- Dummy data for Interviewers
INSERT INTO Interviewers (name, email, department) VALUES
('Alice Johnson', 'alice.johnson@company.com', 'Engineering'),
('Bob Williams', 'bob.williams@company.com', 'Product'),
('Carol Davis', 'carol.davis@company.com', 'Data Science'),
('David Wilson', 'david.wilson@company.com', 'UX Design');

-- Dummy data for Candidate_Interviews
INSERT INTO Candidate_Interviews (candidate_id, round_id, scheduled_at, feedback, status) VALUES
(1, 1, '2025-07-20 10:00:00', 'Good understanding of basic concepts', 'Completed'),
(1, 2, '2025-07-22 14:00:00', 'Excellent problem-solving skills', 'Completed'),
(2, 1, '2025-07-21 11:00:00', 'Strong communication skills', 'Completed'),
(3, 1, '2025-07-23 09:00:00', 'Impressive knowledge of machine learning', 'Completed'),
(4, 1, '2025-07-24 13:00:00', 'Creative approach to design challenges', 'In progress'),
(5, 1, '2025-07-25 15:00:00', 'Solid understanding of DevOps practices', 'Pending');

-- Dummy data for CandidateInterviewers
INSERT INTO Candidate_Interviewers (candidate_interview_id, interviewer_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4),
(6, 1);
