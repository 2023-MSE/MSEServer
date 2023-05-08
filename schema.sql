create table userdata (
	id identity,
	login_id varchar(20) not null,
	login_pw varchar(20) not null,
	nickname varchar(20) not null,
	money int not null
);

create table dungeonmap (
	name varchar(20) not null,
	created_time varchar(20) not null
	
);

create table stage (
	next_stage long,
	stage_type int not null,
	specific_type_info varchar(20) not null
);