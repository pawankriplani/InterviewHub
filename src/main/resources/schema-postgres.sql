
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
    FOREIGN KEY (manager_id) REFERENCES USERS(user_id)
);

CREATE TABLE Interview_Rounds (
    round_id SERIAL PRIMARY KEY,
    round_name VARCHAR(100),
    description TEXT
);


CREATE TABLE Candidate_Interviews (
    candidate_interview_id SERIAL PRIMARY KEY,
    candidate_id INT,
    round_id INT,
    interviewer_id INT,
    interviewer_email VARCHAR(100),
    scheduled_at TIMESTAMP,
    start_meeting_ts TIMESTAMP,
    end_meeting_ts TIMESTAMP,
    feedback TEXT,
    meeting_link TEXT,
    status VARCHAR(20) CHECK (status IN ('Pending', 'In progress', 'Completed', 'Selected', 'Rejected')) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (candidate_id) REFERENCES Candidates(candidate_id),
    FOREIGN KEY (round_id) REFERENCES Interview_Rounds(round_id),
);


