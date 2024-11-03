-- App account insert with agree_to_terms being false
INSERT INTO app_account (app_account_id, app_account_name, agree_to_terms, date_agreed)
VALUES (1, 'testUser', 1, NOW());

-- App account insert with agree_to_terms being true
INSERT INTO app_account (app_account_id, app_account_name, agree_to_terms, date_agreed)
VALUES (2, 'Helmes', 0, NOW());
