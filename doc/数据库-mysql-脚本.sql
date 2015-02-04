======================================================
秀美丽网站数据库脚本
======================================================
##广告系统
create table web_advertising(
	id int(11) not null AUTO_INCREMENT,
	name		varchar(100)	not null 			comment '广告名称',
	image_url	varchar(400)	not null			comment '广告图片地址',
	link_url	varchar(400)	not null			comment '广告链接地址',
	location	int(2)			not null default 1	comment	'广告展示位置(1:主页)',
	display		int(2)			not null default 1	comment '广告展示方式(1:banner 图)',
	start_time	datetime		not null			comment '广告生效开始时间',
	end_time	datetime		not null			comment '广告生效结束时间',
	status		int(1)			not null default 1	comment '广告状态(1:生效,-1:禁用)',
	create_user	int(11)			not null			comment '广告录入用户',
	create_time	datetime		not null			comment '创建时间',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_advertising_location ON web_advertising(location);

### 用户表
create table web_user (
	id		int(11) not null AUTO_INCREMENT,
	user_name	varchar(40)	 null comment '用户名称',
	account		varchar(50)	not null comment '登陆账号',
	passwd		varchar(32)	not null comment '登陆密码',
	sex			int(1)		null	comment	 '性别(1:男,-1:女)',
	type		int(2)		not null	comment '用户类型(1:管理类用户,2:普通用户)',
	email		varchar(40)	null	comment	 '邮箱',
	mobile_phone	varchar(20)	null	comment '移动电话',
	create_time		datetime	not null	comment '注册时间',
	update_time		datetime	null		comment	'修改时间',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_user_account ON web_user(account);

### 分组表
create table web_group (
	id			int(11)		not null	AUTO_INCREMENT,
	name		varchar(10)	not null	comment '分组名称',
	remark		varchar(50)	not null	comment	'分组描述',
	order_no	int(2)		not null	comment '排序号',
	icon_url	varchar(200) not null	comment	'icon地址',
	label		varchar(5)	 not null	default '' comment '分组标签,如果为空则不显示',
	create_time	datetime	not null	comment '创建时间',
	update_time	datetime	null		comment '修改时间',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

###	分类表
create table web_type (
	id			int(11) 	not null AUTO_INCREMENT,
	name		varchar(10)	not null	comment '分类名称',
	remark		varchar(50) not null	comment '分类描述',
	group_id	int(11)		not null	comment	'所属主',
	order_no	int(2)		not null	comment '排序号',
	display_type	int(1)	not null	comment '显示方式(1:图标,2:列表)',
	order_type	int(1)      not null	comment '商品排序方式(1:按照创建时间倒序,2:按照点击量倒序)',
	icon_url	varchar(200) not null	comment	'icon地址',
	create_time	datetime	not null	comment '创建时间',
	update_time	datetime	null		comment '修改时间',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_type_groupid ON web_type(group_id);

### 商品信息表
create table web_product(
	id 		int(11)		not null	AUTO_INCREMENT,
	title	varchar(50)	not null	comment 'title',
	remark	varchar(100)	not null	comment '描述',
	price	double			not null	comment	'价格',
	image_url	varchar(400)	not null	comment '图片地址',
	image_width	double		not null	comment '图片宽度',
	image_height	double		not null	comment	'图片高度',
	product_url	varchar(400)	not null	comment '商品链接',
	click_count	int(11)			not null default 0		comment '点击次数',
	praise_count int(11)		not null default 0		comment '点赞次数',
	create_time		datetime	not null	comment '创建时间',
	update_time		datetime	null		comment	'修改时间',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_product_click ON web_product(click_count);
CREATE INDEX ik_product_praise ON web_product(praise_count);
CREATE INDEX ik_product_createtime ON web_product(create_time);

### 商品属性表(商品分类属性)
create table web_product_type(
	id		int(11)		not null	AUTO_INCREMENT,
	product_id	int(11)	not null	comment '商品ID',
	type_id		int(11)	not null	comment	'商品所属分类',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_product_type_pctid ON web_product_type(product_id);
CREATE INDEX ik_product_type_typeid ON web_product_type(type_id);

####初始化管理用户数据
insert into web_user(user_name,account,passwd,sex,type,email,mobile_phone,create_time) values('超级管理员','zhangwei_2943@163.com','686aeb560c684f3f07c45e4089153a24',1,1,'zhangwei_2943@163.com','15328007301',now());

