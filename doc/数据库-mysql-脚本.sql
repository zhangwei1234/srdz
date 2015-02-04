======================================================
��������վ���ݿ�ű�
======================================================
##���ϵͳ
create table web_advertising(
	id int(11) not null AUTO_INCREMENT,
	name		varchar(100)	not null 			comment '�������',
	image_url	varchar(400)	not null			comment '���ͼƬ��ַ',
	link_url	varchar(400)	not null			comment '������ӵ�ַ',
	location	int(2)			not null default 1	comment	'���չʾλ��(1:��ҳ)',
	display		int(2)			not null default 1	comment '���չʾ��ʽ(1:banner ͼ)',
	start_time	datetime		not null			comment '�����Ч��ʼʱ��',
	end_time	datetime		not null			comment '�����Ч����ʱ��',
	status		int(1)			not null default 1	comment '���״̬(1:��Ч,-1:����)',
	create_user	int(11)			not null			comment '���¼���û�',
	create_time	datetime		not null			comment '����ʱ��',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_advertising_location ON web_advertising(location);

### �û���
create table web_user (
	id		int(11) not null AUTO_INCREMENT,
	user_name	varchar(40)	 null comment '�û�����',
	account		varchar(50)	not null comment '��½�˺�',
	passwd		varchar(32)	not null comment '��½����',
	sex			int(1)		null	comment	 '�Ա�(1:��,-1:Ů)',
	type		int(2)		not null	comment '�û�����(1:�������û�,2:��ͨ�û�)',
	email		varchar(40)	null	comment	 '����',
	mobile_phone	varchar(20)	null	comment '�ƶ��绰',
	create_time		datetime	not null	comment 'ע��ʱ��',
	update_time		datetime	null		comment	'�޸�ʱ��',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_user_account ON web_user(account);

### �����
create table web_group (
	id			int(11)		not null	AUTO_INCREMENT,
	name		varchar(10)	not null	comment '��������',
	remark		varchar(50)	not null	comment	'��������',
	order_no	int(2)		not null	comment '�����',
	icon_url	varchar(200) not null	comment	'icon��ַ',
	label		varchar(5)	 not null	default '' comment '�����ǩ,���Ϊ������ʾ',
	create_time	datetime	not null	comment '����ʱ��',
	update_time	datetime	null		comment '�޸�ʱ��',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

###	�����
create table web_type (
	id			int(11) 	not null AUTO_INCREMENT,
	name		varchar(10)	not null	comment '��������',
	remark		varchar(50) not null	comment '��������',
	group_id	int(11)		not null	comment	'������',
	order_no	int(2)		not null	comment '�����',
	display_type	int(1)	not null	comment '��ʾ��ʽ(1:ͼ��,2:�б�)',
	order_type	int(1)      not null	comment '��Ʒ����ʽ(1:���մ���ʱ�䵹��,2:���յ��������)',
	icon_url	varchar(200) not null	comment	'icon��ַ',
	create_time	datetime	not null	comment '����ʱ��',
	update_time	datetime	null		comment '�޸�ʱ��',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_type_groupid ON web_type(group_id);

### ��Ʒ��Ϣ��
create table web_product(
	id 		int(11)		not null	AUTO_INCREMENT,
	title	varchar(50)	not null	comment 'title',
	remark	varchar(100)	not null	comment '����',
	price	double			not null	comment	'�۸�',
	image_url	varchar(400)	not null	comment 'ͼƬ��ַ',
	image_width	double		not null	comment 'ͼƬ���',
	image_height	double		not null	comment	'ͼƬ�߶�',
	product_url	varchar(400)	not null	comment '��Ʒ����',
	click_count	int(11)			not null default 0		comment '�������',
	praise_count int(11)		not null default 0		comment '���޴���',
	create_time		datetime	not null	comment '����ʱ��',
	update_time		datetime	null		comment	'�޸�ʱ��',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_product_click ON web_product(click_count);
CREATE INDEX ik_product_praise ON web_product(praise_count);
CREATE INDEX ik_product_createtime ON web_product(create_time);

### ��Ʒ���Ա�(��Ʒ��������)
create table web_product_type(
	id		int(11)		not null	AUTO_INCREMENT,
	product_id	int(11)	not null	comment '��ƷID',
	type_id		int(11)	not null	comment	'��Ʒ��������',
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX ik_product_type_pctid ON web_product_type(product_id);
CREATE INDEX ik_product_type_typeid ON web_product_type(type_id);

####��ʼ�������û�����
insert into web_user(user_name,account,passwd,sex,type,email,mobile_phone,create_time) values('��������Ա','zhangwei_2943@163.com','686aeb560c684f3f07c45e4089153a24',1,1,'zhangwei_2943@163.com','15328007301',now());

