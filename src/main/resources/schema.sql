drop table if exists public.notes;
drop table if exists public.users;

create table if not exists public.notes (
	id 			serial primary key,
	name 		varchar(255),
	description varchar(255),
	is_deleted 	boolean,
	created_at 	timestamp,
	updated_at 	timestamp
);

create table if not exists public.users (
	id 			serial primary key,
	username 	varchar(255),
	password 	varchar(255),
	openpass 	varchar(255),
	email 		varchar(255)
);