CREATE TABLE app_account (
    app_account_id INT AUTO_INCREMENT PRIMARY KEY,
    app_account_name VARCHAR(255) NOT NULL,
    agree_to_terms BOOLEAN NOT NULL,
    date_agreed TIMESTAMP
);