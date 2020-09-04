create table if not exists axq_message
(
    id bigint auto_increment primary key,
    tube varchar(150) not null,
    message longtext,
    create_time bigint not null,
    key axq_message_tube_index (`tube`, `id`)
); /* ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; */
