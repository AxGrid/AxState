create table if not exists ax_state
(
    id bigint auto_increment primary key not null,
    `type` varchar(250) not null,
    `status` int not null default 0,
    `data` longtext,
    update_time long,
    key ax_state_status_type(`status`,`type`),
    key ax_state_clean(`update_time`, `status`, `type`)
);
