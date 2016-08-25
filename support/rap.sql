/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016-8-25 14:28:14                           */
/*==============================================================*/


drop table if exists RAP_DICT_TYPE;

drop table if exists RAP_DICT_VALUE;

drop table if exists RAP_RESOURCE;

drop table if exists RAP_ROLE;

drop table if exists RAP_ROLE_RESOURCE;

drop table if exists RAP_USER;

drop table if exists RAP_USER_ROLE;

/*==============================================================*/
/* Table: RAP_DICT_TYPE                                         */
/*==============================================================*/
create table RAP_DICT_TYPE
(
   DICT_TYPE_ID         int not null auto_increment comment '主键',
   NAME                 varchar(40) not null comment '分类名称',
   CODE                 varchar(40) not null comment '分类代码',
   MEMO                 varchar(400) comment '备注',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (DICT_TYPE_ID),
   key AK_RAP_DICT_TYPE_CODE (CODE)
);

alter table RAP_DICT_TYPE comment '字典分类';

/*==============================================================*/
/* Table: RAP_DICT_VALUE                                        */
/*==============================================================*/
create table RAP_DICT_VALUE
(
   DICT_VALUE_ID        int not null auto_increment comment '主键',
   DICT_TYPE_ID         int comment '主键2',
   CODE                 varchar(400) not null comment '代码',
   VALUE                varchar(400) not null comment '值',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   MEMO                 varchar(400) comment '备注',
   primary key (DICT_VALUE_ID),
   key AK_RAP_DICT_VALUE_CODE (DICT_VALUE_ID, CODE)
);

alter table RAP_DICT_VALUE comment '字典值';

/*==============================================================*/
/* Table: RAP_RESOURCE                                          */
/*==============================================================*/
create table RAP_RESOURCE
(
   RESOURCE_ID          int not null auto_increment comment '主键',
   NAME                 varchar(40) not null comment '资源名称',
   CODE                 varchar(40) not null comment '代码',
   TYPE                 varchar(20) comment '类型',
   URL                  varchar(400) comment 'URL',
   MEMO                 varchar(400) comment '备注',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (RESOURCE_ID),
   key AK_RAP_RESOURCE_CODE (CODE)
);

alter table RAP_RESOURCE comment '资源';

/*==============================================================*/
/* Table: RAP_ROLE                                              */
/*==============================================================*/
create table RAP_ROLE
(
   ROLE_ID              int not null auto_increment comment '主键',
   MEMO                 varchar(400) comment '备注',
   CODE                 varchar(40) not null comment '代码',
   NAME                 varchar(40) not null comment '名称',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (ROLE_ID),
   key AK_RAP_ROLE_CODE (CODE)
);

alter table RAP_ROLE comment '角色';

/*==============================================================*/
/* Table: RAP_ROLE_RESOURCE                                     */
/*==============================================================*/
create table RAP_ROLE_RESOURCE
(
   RESOURCE_ID          int not null comment '主键2',
   ROLE_ID              int not null comment '主键',
   primary key (RESOURCE_ID, ROLE_ID)
);

alter table RAP_ROLE_RESOURCE comment '角色资源';

/*==============================================================*/
/* Table: RAP_USER                                              */
/*==============================================================*/
create table RAP_USER
(
   USER_ID              int not null auto_increment comment '主键',
   USERNAME             varchar(40) not null comment '用户名',
   PASSWORD             varchar(40) not null comment '密码',
   FULLNAME             varchar(40) not null comment '姓名',
   TEL                  varchar(40) comment '电话',
   BIRTHDAY             date comment '出生日期',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   MEMO                 varchar(400) comment '备注',
   primary key (USER_ID),
   key AK_RAP_USER_USERNAME (USERNAME)
);

alter table RAP_USER comment '用户';

/*==============================================================*/
/* Table: RAP_USER_ROLE                                         */
/*==============================================================*/
create table RAP_USER_ROLE
(
   USER_ID              int not null comment '主键',
   ROLE_ID              int not null comment '主键2',
   primary key (USER_ID, ROLE_ID)
);

alter table RAP_USER_ROLE comment '用户角色';

