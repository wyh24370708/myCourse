-- 大章
drop table if exists `chapter`;
create table `chapter` (
        `id` int not null auto_increment comment '编号',
        `course_id` varchar(8) unique comment '课程id',
        `name` varchar(52) not null comment '名称',
        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '大章';

select * from chapter;
truncate table chapter;
insert into chapter (course_id, name) values ('SC001','测试1');
insert into chapter (course_id, name) values ('SC002','测试2');
insert into chapter (course_id, name) values ('SC003','测试3');
insert into chapter (course_id, name) values ('SC004','测试4');
insert into chapter (course_id, name) values ('SC005','测试5');
insert into chapter (course_id, name) values ('SC006','测试6');
insert into chapter (course_id, name) values ('SC007','测试7');
insert into chapter (course_id, name) values ('SC008','测试8');
insert into chapter (course_id, name) values ('SC009','测试9');
insert into chapter (course_id, name) values ('SC010','测试10');
insert into chapter (course_id, name) values ('SC011','测试11');
insert into chapter (course_id, name) values ('SC012','测试12');
insert into chapter (course_id, name) values ('SC013','测试13');
insert into chapter (course_id, name) values ('SC014','测试14');
insert into chapter (course_id, name) values ('SC015','测试15');
insert into chapter (course_id, name) values ('SC016','测试16');
insert into chapter (course_id, name) values ('SC017','测试17');
insert into chapter (course_id, name) values ('SC018','测试18');
insert into chapter (course_id, name) values ('SC019','测试19');
insert into chapter (course_id, name) values ('SC020','测试20');

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