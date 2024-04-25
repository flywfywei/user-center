create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    user_account varchar(256)                       null comment '用户账号',
    password     varchar(256)                       not null comment '用户密码',
    username     varchar(256)                       null comment '用户昵称',
    avatar_url   varchar(1024)                      null comment '用户头像',
    email        varchar(512)                       null comment '邮箱',
    sex          tinyint  default 1                 null comment '性别 0-女 1-男 ',
    role         int      default 0                 null comment '角色 1-管理 0-默认',
    state        int      default 0                 not null comment '用户状态 0-正常',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete    tinyint  default 0                 not null comment '逻辑删除 0-正常'
);


