insert into users(id, email, nickname, password, user_role)
values (1,'admin@test.com', 'adminNickname', '$2a$12$NdcsIXxPvy8xnpZPtkCnSO4VOqt6MaddO0TEce9EQoSK3vWz8s4TG', 'ADMIN'),
       (2, 'user1@test.com', 'userOneNickname', 'dummyPasswordHash', 'USER');

INSERT INTO todos (id, title, contents, weather, user_id, created_at, modified_at)
VALUES
    (1, 'title1', 'contents1', 'SUNNY', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'title2', 'contents2', 'RAINY', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO comments (id, contents, user_id, todo_id, created_at, modified_at)
VALUES
    (1, 'comment1', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'comment2', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'comment3', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'comment4', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO managers (id, user_id, todo_id)
VALUES
    (1, 1, 1),
    (2, 2, 2);