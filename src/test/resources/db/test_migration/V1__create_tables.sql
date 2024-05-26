-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    role_id INT,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Create notes table
CREATE TABLE IF NOT EXISTS notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id UUID NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES users(id)
);
