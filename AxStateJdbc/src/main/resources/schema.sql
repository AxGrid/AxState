create table if not exists ax_state
(
    id bigint auto_increment primary key not null,
    `type` varchar(250) not null,
    `data` longtext,
    update_time long,
    key ax_state_type(`type`),
    key ax_state_type(`update_time`, `type`)
);
