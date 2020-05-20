-- 大章
select * from chapter;
truncate table chapter;
#创建
drop table if exists `chapter`;
create table `chapter` (
        `id` varchar(10) not null comment '编号',
        `course_id` varchar(8) unique comment '课程id',
        `name` varchar(52) not null comment '名称',
        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '大章';
#插入记录
insert into chapter (id, course_id, name) values ('001','SC001','测试1');
insert into chapter (id, course_id, name) values ('002','SC002','测试2');
insert into chapter (id, course_id, name) values ('003','SC003','测试3');
insert into chapter (id, course_id, name) values ('004','SC004','测试4');
insert into chapter (id, course_id, name) values ('005','SC005','测试5');
insert into chapter (id, course_id, name) values ('006','SC006','测试6');
insert into chapter (id, course_id, name) values ('007','SC007','测试7');
insert into chapter (id, course_id, name) values ('008','SC008','测试8');
insert into chapter (id, course_id, name) values ('009','SC009','测试9');
insert into chapter (id, course_id, name) values ('010','SC010','测试10');
insert into chapter (id, course_id, name) values ('011','SC011','测试11');
insert into chapter (id, course_id, name) values ('012','SC012','测试12');






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