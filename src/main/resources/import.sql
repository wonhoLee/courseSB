INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (0, 'test', '1234', '이름', 'test@naver.com');
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (-1, 'test1', '1234', '이름', 'test@naver.com');

INSERT INTO QUESTION(id, writer_id, title, contents, create_Date) VALUES(0, 0, '국내에서 Ruby on Rails 사용하나요?', 'Ruby on Rails 사용량이', CURRENT_TIMESTAMP());