package com.fit.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="musics")
public class Music {

	@Id
	private String id;
	
	@Field(value="song_title")
	private String songTitle;
	
	@Field(value="singer")
	private String singer;
	
	@Field(value="release_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date releaseDate;
	
	@Field(value="album_title")
	private String albumTitle;
	
	@Field(value="genre")
	private String genre;
	
	@Field(value="singer_type")
	private String singerType;
	
	@Field(value="period")
	private String period;
	
	@Field(value="preference_tf")
	private Boolean preferenceTf;
	
	@Field(value="img_path")
	private String imgPath;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSongTitle() {
		return songTitle;
	}
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getSingerType() {
		return singerType;
	}
	public void setSingerType(String singerType) {
		this.singerType = singerType;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Boolean getPreferenceTf() {
		return preferenceTf;
	}
	public void setPreferenceTf(Boolean preferenceTf) {
		this.preferenceTf = preferenceTf;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}