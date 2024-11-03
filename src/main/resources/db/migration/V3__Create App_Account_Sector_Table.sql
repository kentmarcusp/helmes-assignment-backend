CREATE TABLE app_account_sector (
    app_account_id INT,
    sector_id INT,
    FOREIGN KEY (app_account_id) REFERENCES app_account(app_account_id) ON DELETE CASCADE,
    FOREIGN KEY (sector_id) REFERENCES sector(sector_id) ON DELETE CASCADE,
    PRIMARY KEY (app_account_id, sector_id)
);