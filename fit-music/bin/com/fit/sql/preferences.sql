create table preferences (
 id int auto_increment primary key,
 music_id int references musics(id),
 user_id varchar(255) references users(id),
 score varchar(255) not null
);