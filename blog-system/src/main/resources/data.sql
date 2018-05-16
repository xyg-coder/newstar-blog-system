INSERT INTO user (id, username, name, password,  email) VALUES (1, 'admin', 'xinyuan','123456', 'xinyuan.gui95@gmail.com');
INSERT INTO user (id, username, name, password,  email)  VALUES (2, 'waylau', 'waylau','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'waylau@waylau.com');

INSERT INTO authority(id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority(id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);