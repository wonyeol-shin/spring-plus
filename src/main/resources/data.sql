insert into users(id, email, nickname, password, user_role)
values (1,'admin@test.com', 'adminNickname', '$2a$12$NdcsIXxPvy8xnpZPtkCnSO4VOqt6MaddO0TEce9EQoSK3vWz8s4TG', 'ADMIN'),
       (2, 'user1@test.com', 'userOneNickname', 'dummyPasswordHash', 'USER'),
       (3, 'u2@test.com', 'alphaNick', 'dummyPasswordHash', 'USER'),
       (4, 'u3@test.com', 'betaNick', 'dummyPasswordHash', 'USER'),
       (5, 'u4@test.com', 'gammaNick', 'dummyPasswordHash', 'USER'),
       (6, 'u5@test.com', 'alphaNickPlus', 'dummyPasswordHash', 'USER');

INSERT INTO todos (id, title, contents, weather, user_id, created_at, modified_at)
VALUES
    (1, 'title1', 'contents1', 'SUNNY', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'title2', 'contents2', 'RAINY', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'title alpha 1', 'contents3', 'SUNNY', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'title alpha 2', 'contents4', 'RAINY', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'title beta 1',  'contents5', 'CLOUDY', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 'misc title',    'contents6', 'SUNNY', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);;

INSERT INTO comments (id, contents, user_id, todo_id, created_at, modified_at)
VALUES
    (1, 'comment1', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'comment2', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'comment3', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'comment4', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'comment5', 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 'comment6', 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 'comment7', 5, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 'comment8', 6, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 'comment9', 3, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO managers (id, user_id, todo_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 3),
    (5, 4, 4),
    (6, 5, 4),
    (7, 6, 4),
    (8, 5, 5);