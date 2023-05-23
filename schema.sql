DROP TABLE IF EXISTS userdata;

create table userdata (
	id identity,
	login_id varchar(20) not null,
	login_pw varchar(20) not null,
	nickname varchar(20) not null,
	money int not null,
	FOREIGN KEY(maps) REFERENCES dungeonmap(id)
);

DROP TABLE IF EXISTS dungeonmap;

create table dungeonmap (
	id identity,
	name varchar(20) not null,
	created_time varchar(20) not null,
	FOREIGN KEY(stages) REFERENCES stage(id)
	FOREIGN KEY(owner_id) REFERENCES userdata(id)
);

DROP TABLE IF EXISTS stage;

create table stage (
	id identity,
	next_stage long,
	stage_type int not null,
	specific_type_info varchar(20) not null
	FOREIGN KEY(mowner_id) REFERENCES dungeonmap(id)
);