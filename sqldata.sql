CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','CUSTOMER','WORKER') NOT NULL,
    status ENUM('ACTIVE','BLOCKED') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);



CREATE TABLE worker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    employment_model ENUM('ON_DEMAND','HOURLY','MONTHLY') NOT NULL,
    rating DECIMAL(2,1) DEFAULT 0.0,
    available BOOLEAN DEFAULT TRUE,
    verified BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);



CREATE TABLE service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);



CREATE TABLE worker_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    fixed_price DECIMAL(10,2),
    hourly_rate DECIMAL(10,2),
    monthly_salary DECIMAL(10,2),
    FOREIGN KEY (worker_id) REFERENCES worker(id),
    FOREIGN KEY (service_id) REFERENCES service(id),
    UNIQUE(worker_id, service_id)
);



CREATE TABLE work_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    employment_model ENUM('ON_DEMAND','HOURLY','MONTHLY') NOT NULL,
    status ENUM(
        'REQUESTED',
        'PENDING_ACCEPTANCE',
        'ACCEPTED',
        'REJECTED',
        'CANCELLED'
    ) DEFAULT 'REQUESTED',
    requested_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id),
    FOREIGN KEY (service_id) REFERENCES service(id)
);


CREATE TABLE job (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_request_id BIGINT NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    status ENUM('IN_PROGRESS','COMPLETED'),
    FOREIGN KEY (work_request_id) REFERENCES work_request(id)
);


CREATE TABLE hourly_work_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_request_id BIGINT NOT NULL,
    check_in DATETIME,
    check_out DATETIME,
    total_hours DECIMAL(5,2),
    FOREIGN KEY (work_request_id) REFERENCES work_request(id)
);



CREATE TABLE contract (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_request_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    monthly_salary DECIMAL(10,2) NOT NULL,
    status ENUM('ACTIVE','PAUSED','TERMINATED') DEFAULT 'ACTIVE',
    FOREIGN KEY (work_request_id) REFERENCES work_request(id)
);



CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    work_day ENUM('MON','TUE','WED','THU','FRI','SAT','SUN'),
    start_time TIME,
    end_time TIME,
    FOREIGN KEY (contract_id) REFERENCES contract(id)
);


CREATE TABLE attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    work_date DATE NOT NULL,
    check_in TIME,
    check_out TIME,
    status ENUM('PRESENT','ABSENT','HALF_DAY'),
    FOREIGN KEY (contract_id) REFERENCES contract(id),
    UNIQUE(contract_id, work_date)
);


CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_request_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_type ENUM('FIXED','HOURLY','MONTHLY') NOT NULL,
    status ENUM('PENDING','PAID','FAILED') DEFAULT 'PENDING',
    paid_at DATETIME,
    FOREIGN KEY (work_request_id) REFERENCES work_request(id)
);


CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_request_id BIGINT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (work_request_id) REFERENCES work_request(id)
);
