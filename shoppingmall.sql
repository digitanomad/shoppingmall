create table manager(
 managerId varchar(50) not null primary key,
 managerPasswd varchar(60) not null
);

insert into manager(managerId,managerPasswd)
values('bookmaster@shop.com','123456');

select * from manager;


drop table manager;

create table book(
  book_id int not null primary key auto_increment,
  book_kind varchar(3) not null,
  book_title varchar(100) not null,
  book_price int not null,
  book_count smallint not null,
  author varchar(40) not null,
  publishing_com varchar(30) not null,
  publishing_date varchar(15) not null,
  book_image varchar(16) default 'nothing.jpg',
  book_content text not null,
  discount_rate tinyint default 10,
  reg_date datetime not null
);

desc book;

create table qna(
  qna_id int not null primary key auto_increment,
  book_id int not null,
  book_title varchar(100) not null,
  qna_writer varchar(50) not null,
  qna_content text not null,
  group_id int not null,
  qora tinyint not null,
  reply tinyint default 0,
  reg_date datetime not null
);

desc qna;

select * from qna;

drop table qna;

create table bank(
  account varchar(30) not null,
  bank varchar(10) not null,
  name varchar(10) not null
);

insert into bank(account, bank, name)
values('11111-111-11111','��������','������');

select * from bank;

create table cart(
  cart_id int not null primary key auto_increment,
  buyer varchar(50) not null,
  book_id int not null,
  book_title varchar(100) not null,
  buy_price int not null,
  buy_count tinyint not null,
  book_image varchar(16) default 'nothing.jpg'
); 

desc cart;

select * from cart;

create table buy(
  buy_id bigint not null,
  buyer varchar(50) not null,
  book_id varchar(12) not null,
  book_title varchar(100) not null,
  buy_price int not null,
  buy_count tinyint not null,
  book_image varchar(16) default 'nothing.jpg',
  buy_date datetime not null,
  account varchar(50) not null,
  deliveryName varchar(10) not null,
  deliveryTel varchar(20) not null,
  deliveryAddress varchar(100) not null,
  sanction varchar(10) default '��ǰ�غ���'
);

desc buy;

drop table buy;

select * from buy;

select * from book;

drop table book;


select count(*) from book

