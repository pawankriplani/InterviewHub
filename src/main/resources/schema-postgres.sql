
CREATE TABLE job_descriptions (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    location VARCHAR(255),
    company VARCHAR(255),
    overview TEXT,
    summary TEXT,
    responsibilities JSONB,
    required_qualifications JSONB,
    preferred_qualifications JSONB,
    benefits JSONB,
    technical_skills JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE Interview_Rounds (
    round_id SERIAL PRIMARY KEY,
    round_name VARCHAR(100),
    description TEXT
);

CREATE TABLE candidates (
    candidate_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    position_applied VARCHAR(100),
    job_details TEXT,
    manager_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    score DOUBLE PRECISION,
    resume_id VARCHAR(50),
    evaluation_id VARCHAR(50),
    job_description_id INT,
    FOREIGN KEY (manager_id) REFERENCES USERS(user_id),
    FOREIGN KEY (job_description_id) REFERENCES job_descriptions(id)
);

CREATE TABLE Candidate_Interviews (
    candidate_interview_id SERIAL PRIMARY KEY,
    candidate_id INT,
    round_id INT,
    start_meeting_ts TIMESTAMP,
    end_meeting_ts TIMESTAMP,
    feedback TEXT,
    meeting_link TEXT,
    status VARCHAR(20) CHECK (status IN ('Pending', 'In progress', 'Completed', 'Selected', 'Rejected')) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (candidate_id) REFERENCES Candidates(candidate_id),
    FOREIGN KEY (round_id) REFERENCES Interview_Rounds(round_id)
);

CREATE TABLE Candidate_Interviewer (
    id SERIAL PRIMARY KEY,
    candidate_interview_id INT,
    interviewer_id VARCHAR(50),
    interviewer_email VARCHAR(100),

    FOREIGN KEY (candidate_interview_id) REFERENCES Candidate_Interviews(candidate_interview_id)
);
