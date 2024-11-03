CREATE TABLE sector (
    sector_id INT AUTO_INCREMENT PRIMARY KEY,
    sector_name VARCHAR(255) NOT NULL,
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES sector(sector_id) ON DELETE SET NULL
);
