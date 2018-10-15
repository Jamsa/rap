/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018-10-3 15:09:02                           */
/*==============================================================*/


drop table if exists RAP_DICT_TYPE;

drop table if exists RAP_DICT_VALUE;

drop table if exists RAP_LOV;

drop table if exists RAP_META_APP;

drop table if exists RAP_META_MODEL;

drop table if exists RAP_META_MODEL_OBJECT;

drop table if exists RAP_META_MODEL_STATUS;

drop table if exists RAP_META_MODEL_STATUS_FIELD;

drop table if exists RAP_META_PAGE;

drop table if exists RAP_META_PAGE_ACTION;

drop table if exists RAP_META_TABLE;

drop table if exists RAP_META_TABLE_FIELD;

drop table if exists RAP_META_VIEW_FIELD;

drop table if exists RAP_META_VIEW_FIELD_PROP;

drop table if exists RAP_META_VIEW_LINK;

drop table if exists RAP_META_VIEW_OBJECT;

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
/* Table: RAP_LOV                                               */
/*==============================================================*/
create table RAP_LOV
(
   LOV_CODE             varchar(200) not null comment '代码',
   LOV_NAME             varchar(200) not null comment '名称',
   LOV_SQL              text comment 'SQL',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (LOV_CODE)
);

alter table RAP_LOV comment '值列表';

/*==============================================================*/
/* Table: RAP_META_APP                                          */
/*==============================================================*/
create table RAP_META_APP
(
   APP_ID               int not null comment '应用程序主键',
   APP_NAME             varchar(40) not null comment '应用程序名称',
   APP_CODE             varchar(40) not null comment '应用程序代码',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (APP_ID)
);

alter table RAP_META_APP comment '应用程序';

/*==============================================================*/
/* Table: RAP_META_MODEL                                        */
/*==============================================================*/
create table RAP_META_MODEL
(
   MODEL_ID             int not null auto_increment comment '模型主键',
   FIELD_ID             int comment '视图对象字段主键',
   APP_ID               int comment '应用程序主键',
   MODEL_NAME           varchar(200) not null comment '模型名称',
   MODEL_CODE           varchar(200) not null comment '模型代码',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (MODEL_ID)
);

alter table RAP_META_MODEL comment '模型对象';

/*==============================================================*/
/* Table: RAP_META_MODEL_OBJECTS                                */
/*==============================================================*/
create table RAP_META_MODEL_OBJECTS
(
   VIEW_OBJECT_ID       int not null comment '视图对象主键',
   MODEL_ID             int not null comment '模型主键',
   VIEW_TYPE            varchar(20) comment '主或从',
   UPDATABLE            varchar(10) comment '可更新',
   DELETABLE            varchar(10) comment '可删除',
   CREATABLE            varchar(10) comment '可创建',
   REL_FIELD            numeric(8,0) comment '子表与主表的关联字段',
   primary key (VIEW_OBJECT_ID, MODEL_ID)
);

alter table RAP_META_MODEL_OBJECT comment '模型视图对象';

/*==============================================================*/
/* Table: RAP_META_MODEL_STATUS                                 */
/*==============================================================*/
create table RAP_META_MODEL_STATUS
(
   MODEL_STATUS_ID      int not null auto_increment comment '视图状态主键',
   MODEL_ID             int comment '模型主键',
   STATUS_CODE          varchar(200) comment '状态代码',
   DEFAULT_STATUS       varchar(40) comment '默认状态',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (MODEL_STATUS_ID)
);

alter table RAP_META_MODEL_STATUS comment '模型状态';

/*==============================================================*/
/* Table: RAP_META_MODEL_STATUS_FIELD                           */
/*==============================================================*/
create table RAP_META_MODEL_STATUS_FIELD
(
   MODEL_STATUS_ID      int not null auto_increment comment '视图状态主键',
   FIELD_ID             int not null comment '视图对象字段主键',
   HIDDEN               varchar(20) comment '隐藏',
   READONLY             varchar(20) comment '只读',
   REQUIRED             varchar(20) comment '必填',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (MODEL_STATUS_ID, FIELD_ID)
);

alter table RAP_META_MODEL_STATUS_FIELD comment '模型状态字段';

/*==============================================================*/
/* Table: RAP_META_PAGE                                         */
/*==============================================================*/
create table RAP_META_PAGE
(
   PAGE_ID              int not null auto_increment comment '页面主键',
   MODEL_ID             int comment '模型主键',
   PAGE_NAME            varchar(200) not null comment '页面名称',
   PAGE_CODE            varchar(200) not null comment '页面代码',
   PAGE_TYPE            varchar(20) comment '页面类型',
   FILE_PATH            varchar(400) comment '页面文件路径',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (PAGE_ID)
);

alter table RAP_META_PAGE comment '页面';

/*==============================================================*/
/* Table: RAP_META_PAGE_ACTION                                  */
/*==============================================================*/
create table RAP_META_PAGE_ACTION
(
   PAGE_ID              int comment '页面主键',
   ACTION_ID            int comment '视图操作主键',
   ACTION_TYPE          varchar(20) comment '操作类型',
   NAV_TARGET           varchar(40) comment '导航目标',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY'
);

alter table RAP_META_PAGE_ACTION comment '页面操作';

/*==============================================================*/
/* Table: RAP_META_TABLE                                        */
/*==============================================================*/
create table RAP_META_TABLE
(
   TABLE_ID             int not null auto_increment comment '表主键',
   APP_ID               int comment '应用程序主键',
   TABLE_NAME           varchar(200) comment '表名称',
   TABLE_CODE           varchar(200) comment '表代码',
   TABLE_SCHEMA         varchar(20) comment 'SCHEMA',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (TABLE_ID)
);

alter table RAP_META_TABLE comment '表';

/*==============================================================*/
/* Table: RAP_META_TABLE_FIELD                                  */
/*==============================================================*/
create table RAP_META_TABLE_FIELD
(
   FIELD_ID             int not null auto_increment comment '字段主键',
   TABLE_ID             int comment '表主键',
   FIELD_NAME           varchar(200) not null comment '字段名称',
   FIELD_CODE           varchar(200) not null comment '字段代码',
   REQUIRED             varchar(10) comment '必填',
   UNIQ                 varchar(10) comment '唯一',
   KEY_FIELD            varchar(10) comment '主键字段',
   FIELD_LENGTH         int comment '长度',
   FIELD_PRECISION      int comment '精度',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (FIELD_ID)
);

alter table RAP_META_TABLE_FIELD comment '字段';

/*==============================================================*/
/* Table: RAP_META_VIEW_FIELD                                   */
/*==============================================================*/
create table RAP_META_VIEW_FIELD
(
   FIELD_ID             int not null auto_increment comment '视图对象字段主键',
   VIEW_OBJECT_ID       int comment '视图对象主键',
   FIELD_NAME           varchar(200) not null comment '视图对象字段名称',
   FIELD_CODE           varchar(200) not null comment '视图对象字段代码',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (FIELD_ID)
);

alter table RAP_META_VIEW_FIELD comment '视图对象字段';

/*==============================================================*/
/* Table: RAP_META_VIEW_FIELD_PROP                              */
/*==============================================================*/
create table RAP_META_VIEW_FIELD_PROP
(
   FIELD_ID             int comment '视图对象字段主键',
   CODE                 varchar(40) not null comment '属性代码',
   VALUE                varchar(400) comment '属性值',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY'
);

alter table RAP_META_VIEW_FIELD_PROP comment '字段属性';

/*==============================================================*/
/* Table: RAP_META_VIEW_LINK                                    */
/*==============================================================*/
create table RAP_META_VIEW_LINK
(
   VIEW_LINK_ID         int not null auto_increment comment '视图对象链接主键',
   VIEW_OBJECT_ID       int comment '视图对象主键',
   RAP_VIEW_OBJECT_ID   int comment '视图对_视图对象主键',
   VIEW_LINK_NAME       varchar(200) comment '视图对象连接名称',
   VIEW_LINK_CODE       varchar(200) comment '视图对象连接代码',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (VIEW_LINK_ID)
);

alter table RAP_META_VIEW_LINK comment '视图对象链接？';

/*==============================================================*/
/* Table: RAP_META_VIEW_OBJECT                                  */
/*==============================================================*/
create table RAP_META_VIEW_OBJECT
(
   VIEW_OBJECT_ID       int not null auto_increment comment '视图对象主键',
   APP_ID               int comment '应用程序主键',
   OBJECT_NAME          varchar(200) not null comment '视图对象名称',
   OBJECT_CODE          varchar(200) not null comment '视图对象代码',
   OBJECT_SQL           text comment 'SQL语句',
   CREATED_BY           int comment 'CREATED_BY',
   CREATION_DATE        datetime comment 'CREATION_DATE',
   LAST_UPDATE_DATE     datetime comment 'LAST_UPDATE_DATE',
   LAST_UPDATED_BY      int comment 'LAST_UPDATED_BY',
   primary key (VIEW_OBJECT_ID)
);

alter table RAP_META_VIEW_OBJECT comment '视图对象';

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

