create table if not exists ax_join
(
    user_id bigint not null primary key,
    `type` varchar(250) not null,
    player_count int default 2,
    `state_id` bigint null,
    update_time long,
    key ax_join_type(`state_id`,`update_time`, `type`, `player_count`),

    key ax_join_clean(`update_time`, `state_id`, `type`)
);
