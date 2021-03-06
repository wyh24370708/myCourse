-- 大章
select * from chapter;
truncate table chapter;
#创建
drop table if exists `chapter`;
create table `chapter` (
        `id` varchar(10) not null comment '编号',
        `course_id` varchar(8)  comment '课程id',
        `name` varchar(52) not null comment '名称',
        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '大章';
#插入记录
insert into chapter (id, course_id, name) values ('001','00000001','测试1');
insert into chapter (id, course_id, name) values ('002','00000001','测试2');
insert into chapter (id, course_id, name) values ('003','00000001','测试3');
insert into chapter (id, course_id, name) values ('004','00000001','测试4');
insert into chapter (id, course_id, name) values ('007','b4zY0WoG','测试1');
insert into chapter (id, course_id, name) values ('008','b4zY0WoG','测试2');
insert into chapter (id, course_id, name) values ('009','b4zY0WoG','测试3');
insert into chapter (id, course_id, name) values ('010','b4zY0WoG','测试4');


-- 小节
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
     `id` CHAR(8) NOT NULL DEFAULT '' COMMENT 'ID',
     `title` VARCHAR(50) NOT NULL COMMENT '标题',
     `course_id` CHAR(8) COMMENT '课程|course.id',
     `chapter_id` CHAR(8) COMMENT '大章|chapter.id',
     `video` VARCHAR(200) COMMENT '视频',
     `time` INT COMMENT '时长|单位秒',
     `charge` CHAR(1) COMMENT '收费|C 收费；F 免费',
     `sort` INT COMMENT '顺序',
     `created_at` DATETIME COMMENT '创建时间',
     `updated_at` DATETIME COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小节';

INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
    VALUES ('10000001', '测试小节01', '00000001', '001', '', 500, 'F', 1, now(), now());


select * from section;


-- 课程表
drop table if exists course;
create table `course`(
    `id` char(8) not null default '' comment 'id',
    `name` varchar(50) not null comment '课程名称',
    `summary` varchar(2000) comment '概述',
    `time` int default 0 comment '时长|单位秒',
    `price` decimal(8,2) default 0.00 comment '价格(元)',
    `image` varchar(100) comment '封面',
    `level` char(1) comment '级别|ONE("1","初级"),TWO("2","中级"),THREE("3","高级")',
    `charge` char(1) comment '收费|CHARGE("C","收费"),FREE("F","免费")',
    `status` char(1) comment '状态|PUBLISH("P","发布"),DRAFT("D","草稿")',
    `enroll` integer default 0 comment '报名数',
    `sort` int comment '顺序',
    `created_at` datetime comment '创建时间',
    `updated_at` datetime comment '更新时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程表';

INSERT INTO course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at)
    VALUES ('00000001', '测试课程01', '这是一门测试课程', 7200, 19.9, '', '1', 'C', 'D', 100, 0, now(), now());

select * from course;



-- 分类
drop table if exists `category`;
create table `category` (
    `id` char(8) not null default '' comment 'id',
    `parent` char(8) not null default '' comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类';

insert into `category` (id, parent, name, sort) values ('00000100', '00000000', '前端技术', 100);
insert into `category` (id, parent, name, sort) values ('00000101', '00000100', 'html/css', 101);
insert into `category` (id, parent, name, sort) values ('00000102', '00000100', 'javascript', 102);
insert into `category` (id, parent, name, sort) values ('00000103', '00000100', 'vue.js', 103);
insert into `category` (id, parent, name, sort) values ('00000104', '00000100', 'react.js', 104);
insert into `category` (id, parent, name, sort) values ('00000105', '00000100', 'angular', 105);
insert into `category` (id, parent, name, sort) values ('00000106', '00000100', 'node.js', 106);
insert into `category` (id, parent, name, sort) values ('00000107', '00000100', 'jquery', 107);
insert into `category` (id, parent, name, sort) values ('00000108', '00000100', '小程序', 108);
insert into `category` (id, parent, name, sort) values ('00000200', '00000000', '后端技术', 200);
insert into `category` (id, parent, name, sort) values ('00000201', '00000200', 'java', 201);
insert into `category` (id, parent, name, sort) values ('00000202', '00000200', 'springboot', 202);
insert into `category` (id, parent, name, sort) values ('00000203', '00000200', 'spring cloud', 203);
insert into `category` (id, parent, name, sort) values ('00000204', '00000200', 'ssm', 204);
insert into `category` (id, parent, name, sort) values ('00000205', '00000200', 'python', 205);
insert into `category` (id, parent, name, sort) values ('00000206', '00000200', '爬虫', 206);
insert into `category` (id, parent, name, sort) values ('00000300', '00000000', '移动开发', 300);
insert into `category` (id, parent, name, sort) values ('00000301', '00000300', 'android', 301);
insert into `category` (id, parent, name, sort) values ('00000302', '00000300', 'ios', 302);
insert into `category` (id, parent, name, sort) values ('00000303', '00000300', 'react native', 303);
insert into `category` (id, parent, name, sort) values ('00000304', '00000300', 'ionic', 304);
insert into `category` (id, parent, name, sort) values ('00000400', '00000000', '前沿技术', 400);
insert into `category` (id, parent, name, sort) values ('00000401', '00000400', '微服务', 401);
insert into `category` (id, parent, name, sort) values ('00000402', '00000400', '区块链', 402);
insert into `category` (id, parent, name, sort) values ('00000403', '00000400', '机器学习', 403);
insert into `category` (id, parent, name, sort) values ('00000404', '00000400', '深度学习', 404);
insert into `category` (id, parent, name, sort) values ('00000405', '00000400', '数据分析&挖掘', 405);
insert into `category` (id, parent, name, sort) values ('00000500', '00000000', '云计算&大数据', 500);
insert into `category` (id, parent, name, sort) values ('00000501', '00000500', '大数据', 501);
insert into `category` (id, parent, name, sort) values ('00000502', '00000500', 'hadoop', 502);
insert into `category` (id, parent, name, sort) values ('00000503', '00000500', 'spark', 503);
insert into `category` (id, parent, name, sort) values ('00000504', '00000500', 'hbase', 504);
insert into `category` (id, parent, name, sort) values ('00000505', '00000500', '阿里云', 505);
insert into `category` (id, parent, name, sort) values ('00000506', '00000500', 'docker', 506);
insert into `category` (id, parent, name, sort) values ('00000507', '00000500', 'kubernetes', 507);
insert into `category` (id, parent, name, sort) values ('00000600', '00000000', '运维&测试', 600);
insert into `category` (id, parent, name, sort) values ('00000601', '00000600', '运维', 601);
insert into `category` (id, parent, name, sort) values ('00000602', '00000600', '自动化运维', 602);
insert into `category` (id, parent, name, sort) values ('00000603', '00000600', '中间件', 603);
insert into `category` (id, parent, name, sort) values ('00000604', '00000600', 'linux', 604);
insert into `category` (id, parent, name, sort) values ('00000605', '00000600', '测试', 605);
insert into `category` (id, parent, name, sort) values ('00000606', '00000600', '自动化测试', 606);
insert into `category` (id, parent, name, sort) values ('00000700', '00000000', '数据库', 700);
insert into `category` (id, parent, name, sort) values ('00000701', '00000700', 'mysql', 701);
insert into `category` (id, parent, name, sort) values ('00000702', '00000700', 'redis', 702);
insert into `category` (id, parent, name, sort) values ('00000703', '00000700', 'mongodb', 703);

select * from category;

-- 课程分类表
drop table if exists `course_category`;
create table `course_category` (
    `id` char(8) not null default '' comment 'id',
    `course_id` char(8) comment '课程|course_id',
    `category_id` char(8) comment '分类|category_id',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment '课程分类';

-- 课程内容表
drop table if exists `course_content`;
create table `course_content` (
    `id` char(8) not null default '' comment '课程id|id',
    `content` mediumtext not null comment '课程内容|content',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment '课程内容';

select * from course_content;


-- 课程内容文件
drop table if exists `course_content_file`;
create table `course_content_file` (
    `id` char(8) not null default '' comment 'id',
    `course_id` char(8) not null comment '课程id',
    `url` varchar(100) comment '地址',
    `name` varchar(100) comment '文件名',
    `size` int comment '大小|字节b',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程内容文件';



-- 讲师
drop table if exists `teacher`;
create table `teacher` (
     `id` char(8) not null default '' comment 'id',
     `name` varchar(50) not null comment '姓名',
     `nickname` varchar(50) comment '昵称',
     `image` varchar(100) comment '头像',
     `position` varchar(50) comment '职位',
     `motto` varchar(50) comment '座右铭',
     `intro` varchar(500) comment '简介',
     primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='讲师';

alter table `course` add column (`teacher_id` char(8) comment '讲师id|teacher_id');

select * from  `course`;


-- 业务文件上传
drop table if exists `profile`;
create table `profile`(
    `id` char(8) not null default '' comment 'id',
    `name` varchar(100) comment '名称',
    `path` varchar(100) not null comment '相对路径',
    `size` int comment '大小',
    `suffix` varchar(10) comment '后缀',
    `use` char(1) comment '用途|枚举[ProfileUseEnum]：COURSE("C", "讲师"), TEACHER("T", "课程")',
    `created_at` datetime comment '创建时间',
    `updated_at` datetime comment '修改时间',
    primary key (`id`),
    unique key `path_unique` (`path`)
)engine=innodb default charset = utf8mb4 comment '业务文件';

#增加列,原有的数据不会被清空
alter table `profile` add column (`shard_index` int comment '已上传的分片');
alter table `profile` add column (`shard_size` int comment '分片的大小|B');
alter table `profile` add column (`shard_total` int comment '分片总数');
alter table `profile` add column (`key` int comment '文件标识');
alter table `profile` add unique key key_unique (`key`);
#修改列类型
alter table `profile` change `key` `key` varchar(32) comment '文件标识';


-- 用户表
drop table if exists `user`;
create table `user`(
  `id` char(8) not null default '' comment 'id',
  `login_name` varchar(50) not null comment '登录名',
  `name` varchar(50) comment '昵称',
  `password` varchar(50) comment '密码',
  `created_at` datetime comment '创建时间',
  `updated_at` datetime comment '修改时间',
  primary key (`id`),
  unique key `login_name_unique` (`login_name`)
)engine=innodb default charset = utf8mb4 comment '用户';

insert into `user` values('10000001','test','测试','123456',now(),now());

select * from user;

-- 资源表  资源名称一般都是看的见你的元素: 菜单 , 按钮等
drop table if exists `resource`;
create table `resource`(
    `id` char(6) not null comment 'id',
    `name` varchar(100) not null comment '名称|菜单或按钮',
    `page` varchar(50) null comment '页面|路由',
    `request` varchar(200) null comment '请求|接口',
    `parent` char(6) comment '父id',
    primary key (`id`)
)engine =innodb default charset =utf8mb4 comment '资源';

insert into `resource` values ('01', '系统管理', null, null, null);
insert into `resource` values ('0101', '用户管理', '/system/user', null, '01');
insert into `resource` values ('010101', '保存', null, '["/system/admin/user/list", "/system/admin/user/save"]', '0101');
insert into `resource` values ('010102', '删除', null, '["/system/admin/user/delete"]', '0101');
insert into `resource` values ('010103', '重置密码', null, '["/system/admin/user/save-password"]', '0101');
insert into `resource` values ('0102', '资源管理', '/system/resource', null, '01');
insert into `resource` values ('010201', '保存/显示', null, '["/system/admin/resource"]', '0102');
insert into `resource` values ('0103', '角色管理', '/system/role', null, '01');
insert into `resource` values ('010301', '角色/权限管理', null, '["/system/admin/role"]', '0103');

select * from resource;

-- 角色
drop table if exists `role`;
create table `role` (
    `id` char(8) not null default '' comment 'id',
    `name` varchar(50) not null comment '角色',
    `desc` varchar(100) not null comment '描述',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='角色';

insert into `role` values ('00000000', '系统管理员', '管理用户、角色权限');
insert into `role` values ('00000001', '开发', '维护资源');
insert into `role` values ('00000002', '业务管理员', '负责业务管理');

select * from role;

-- 角色资源关联表
drop table if exists `role_resource`;
create table `role_resource`(
    `id` char(8) not null default '' comment 'id',
    `role_id` char(8) not null comment '角色id',
    `resource_id` char(6) not null comment '资源id',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment '角色资源关联';

insert into `role_resource` values ('00000001','00000000','01');
insert into `role_resource` values ('00000002','00000000','0101');
insert into `role_resource` values ('00000003','00000000','010101');
insert into `role_resource` values ('00000004','00000000','010102');
insert into `role_resource` values ('00000005','00000000','010103');
insert into `role_resource` values ('00000006','00000000','0102');
insert into `role_resource` values ('00000007','00000000','010201');
insert into `role_resource` values ('00000008','00000000','0103');
insert into `role_resource` values ('00000009','00000000','010301');

select * from role_resource;
truncate table role_resource;

-- 角色用户关联表
drop table if exists `role_user`;
create table `role_user`(
    `id` char(8) not null default '' comment 'id',
    `role_id` char(8) not null comment '角色id',
    `user_id` char(8) not null comment '用户id',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment '角色用户关联';

insert into `role_user` values ('00000000', '00000000', '10000001');



-- 测试
drop table if exists course;
CREATE TABLE `course` (
    `id` varchar(52) NOT NULL COMMENT '编号',
    `name` varchar(52) NOT NULL COMMENT '姓名',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into course values('1','李正龙');
insert into course values('2','王勇豪');
insert into course values('3','张杰');