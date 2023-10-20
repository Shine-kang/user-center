create table if not exists yupi.user
(
    id           bigint auto_increment comment 'id'
    primary key,
    username     varchar(256)                       null comment '用户昵称',
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    email        varchar(512)                       null comment '邮箱',
    phone        varchar(128)                       null comment '电话号码',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '登录密码',
    userStatus   int      default 0                 not null comment '状态-0 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 null comment '用户权限 普通人-0  管理员-1  ',
    planetCode   varchar(512)                       null comment '用户编号'
    )
    comment '用户';
