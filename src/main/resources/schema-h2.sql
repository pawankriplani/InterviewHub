CREATE TABLE IF NOT EXISTS Candidates (
    candidate_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    position_applied VARCHAR(100),
    job_details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Interview_Rounds (
    round_id INT PRIMARY KEY AUTO_INCREMENT,
    round_name VARCHAR(100),
    description TEXT
);

CREATE TABLE IF NOT EXISTS Interviewers (
    interviewer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    department VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Candidate_Interviews (
    candidate_interview_id INT PRIMARY KEY AUTO_INCREMENT,
    candidate_id INT,
    round_id INT,
    scheduled_at DATETIME,
    feedback TEXT,
    status ENUM('Pending', 'In progress', 'Completed', 'Selected', 'Rejected') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (candidate_id) REFERENCES Candidates(candidate_id),
    FOREIGN KEY (round_id) REFERENCES Interview_Rounds(round_id)
);

CREATE TABLE IF NOT EXISTS Candidate_Interviewers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    candidate_interview_id INT NOT NULL,
    interviewer_id INT NOT NULL,

    FOREIGN KEY (candidate_interview_id) REFERENCES Candidate_Interviews(candidate_interview_id),
    FOREIGN KEY (interviewer_id) REFERENCES Interviewers(interviewer_id)
);


-- Dummy data for Candidates
INSERT INTO Candidates (name, email, phone, position_applied, job_details) VALUES
('John Doe', 'john.doe@email.com', '1234567890', 'Software Engineer', 'Looking for a challenging role in backend development'),
('Jane Smith', 'jane.smith@email.com', '2345678901', 'Product Manager', 'Experienced in Agile methodologies and team leadership'),
('Mike Johnson', 'mike.johnson@email.com', '3456789012', 'Data Scientist', 'Expertise in machine learning and big data analytics'),
('Emily Brown', 'emily.brown@email.com', '4567890123', 'UX Designer', 'Passionate about creating intuitive user interfaces'),
('Chris Lee', 'chris.lee@email.com', '5678901234', 'DevOps Engineer', 'Skilled in CI/CD pipelines and cloud infrastructure'),
('Alice Cooper', 'alice.cooper@email.com', '6789012345', 'Frontend Developer', 'Experienced in React and modern JavaScript frameworks'),
('Bob Dylan', 'bob.dylan@email.com', '7890123456', 'Backend Developer', 'Proficient in Java and Spring Boot'),
('Carol Danvers', 'carol.danvers@email.com', '8901234567', 'Full Stack Developer', 'Skilled in both frontend and backend technologies'),
('Nina Patel', 'nina.patel@email.com', '9012345678', 'Full Stack Engineer', 'Experienced in MERN stack and system architecture'),
('Priya Shah', 'priya.shah@email.com', '9234567890', 'Frontend Developer', 'React, Redux, UI/UX focused developer'),
 ('Amit Rathi', 'amit.rathi@email.com', '9345678901', 'DevOps Engineer', 'Expert in Docker, Kubernetes, Jenkins pipelines');

-- Dummy data for InterviewRounds
INSERT INTO Interview_Rounds (round_name, description) VALUES
('HR Screen', 'Initial technical assessment to evaluate basic skills'),
('Technical Round 1', 'In-depth coding exercise to assess problem-solving abilities'),
('Technical Round 2', 'Discussion on designing scalable and efficient systems'),
('Managerial Round', 'Discussion on designing scalable and efficient systems');

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
(5, 1, '2025-07-25 15:00:00', 'Solid understanding of DevOps practices', 'Pending'),
-- Candidate who completed all technical rounds
(6, 1, '2025-07-26 10:00:00', 'Excellent frontend skills', 'Completed'),
(6, 2, '2025-07-28 14:00:00', 'Great problem-solving abilities', 'Completed'),
(6, 3, '2025-07-30 11:00:00', 'Impressive system design knowledge', 'Completed'),
-- Candidate who completed 2 technical rounds with the third one in progress
(7, 1, '2025-07-27 09:00:00', 'Strong Java fundamentals', 'Completed'),
(7, 2, '2025-07-29 13:00:00', 'Good understanding of Spring Boot', 'Completed'),
(7, 3, '2025-07-31 15:00:00', NULL, 'In progress'),
-- Candidate who is in the middle of the interview process
(8, 1, '2025-07-28 11:00:00', 'Solid full-stack knowledge', 'Completed'),
(8, 2, '2025-07-30 14:00:00', NULL, 'In progress'),
(8, 3, '2025-08-01 10:00:00', NULL, 'Pending'),
(9, 1, '2025-07-20 10:00:00', 'Excellent communication in HR screening', 'Completed'),
(9, 2, '2025-07-22 14:00:00', 'Strong coding and problem-solving skills', 'Completed'),

-- Ravi mehra
(10, 1, '2025-07-21 09:00:00', 'Great HR round', 'Completed'),
(10, 2, '2025-07-23 13:00:00', 'Solid Java and Spring Boot skills', 'Completed'),
(10, 3, '2025-07-25 11:00:00', NULL, 'In progress');
-- priya 
(11, 1, '2025-07-20 15:00:00', 'Strong HR round and motivation', 'Completed'),
(11, 2, '2025-07-22 16:00:00', NULL, 'In progress'),
(11, 3, '2025-07-24 10:00:00', NULL, 'Pending'),

(12, 1, '2025-07-20 11:00:00', 'Good DevOps fundamentals', 'Completed'),
(12, 2, '2025-07-22 13:00:00', 'Strong CI/CD knowledge', 'Completed'),
(12, 3, '2025-07-24 16:00:00', 'Excellent understanding of cloud automation', 'Completed'),
(12, 4, '2025-07-24 16:00:00', 'Excellent understanding of cloud automation', 'Selected');

-- Dummy data for CandidateInterviewers
INSERT INTO Candidate_Interviewers (candidate_interview_id, interviewer_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4),
(6, 1),
(7, 1),
(8, 2),
(9, 3),
(10, 1),
(11, 2),
(12, 3),
(13, 4),
(14, 1),
(15, 2),
(16, 1), 
(17, 1),
(18, 1),
(19, 2),
(20, 3),
(21, 1),
(22, 2),
(23, 3),
(24, 1),
(25, 2),
(26, 3),
(27, 4);
