create table users ( 
	 id varchar(255) primary key,
	 name varchar(255) not null,
	 password varchar(255) not null,
	 preference boolean not null
);

insert into users (id, name, password, preference) 
  values('user1', 'user1', 'user1', false);

  
 select * from users;