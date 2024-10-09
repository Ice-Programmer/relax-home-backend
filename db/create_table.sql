-- create database
create database if not exists rent_home;

-- use database
use rent_home;

-- user table
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment 'account',
    userPassword varchar(512)                           not null comment 'password',
    email        varchar(512)                           null comment 'email',
    userName     varchar(256)                           null comment 'user nickname',
    userAvatar   varchar(1024)                          null comment 'user avatar',
    userRole     varchar(256) default 'tenant'          not null comment 'user role：tenant/landlord/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete     tinyint      default 0                 not null comment 'is delete',
    index idx_userAccount (userAccount)
) comment 'user' collate = utf8mb4_unicode_ci;

-- apartment advertisement
create table if not exists advertisement
(
    id          bigint auto_increment comment 'id' primary key,
    title       varchar(256)                       null comment 'advertisement title',
    description text                               null comment 'apartment description',
    address     varchar(512)                       null comment 'apartment address',
    price       int                                not null comment 'apartment yearly rental price',
    area        int                                not null comment 'apartment floor area',
    userId      bigint                             not null comment 'create user id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete    tinyint  default 0                 not null comment 'is delete',
    index idx_title (title)
) comment 'apartment rent advertisement' collate = utf8mb4_unicode_ci;

-- apartment advertisement's picture
create table if not exists advertisement_picture
(
    id              bigint auto_increment comment 'id' primary key,
    advertisementId bigint                             not null comment 'advertisement id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete        tinyint  default 0                 not null comment 'is delete'
) comment 'apartment pictures' collate = utf8mb4_unicode_ci;