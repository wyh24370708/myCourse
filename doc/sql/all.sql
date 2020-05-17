-- 大章
drop table if exists `chapter`;
create table `chapter` (
        `id` int not null auto_increment comment '编号',
        `course_id` varchar(8) unique comment '课程id',
        `name` varchar(52) not null comment '名称',
        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '大章';



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