create table musics (
	id int auto_increment primary key,
	song_title varchar(255) not null,
	singer varchar(255) not null,
	realease_data datetime,
	album_title varchar(255),
	genre varchar(255),
	singer_type varchar(255),
	period varchar(255),
	preference boolean,
	img_path varchar(255),
	img_file_name varchar(255)
);