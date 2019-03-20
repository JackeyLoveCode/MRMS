package com.hiveview.mrms.pojo;

import java.util.Date;

public class Movie {
    private Integer id;

    private String title;

    private String actors;

    private Integer type;

    private String area;

    private String language;

    private String director;

    private String moviePath;

    private String imgPath;

    private Date time;

    private String plot;

    private Integer playTimes;

    private Integer favoriteTimes;

    public Integer getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(Integer playTimes) {
        this.playTimes = playTimes;
    }

    public Integer getFavoriteTimes() {
        return favoriteTimes;
    }

    public void setFavoriteTimes(Integer favoriteTimes) {
        this.favoriteTimes = favoriteTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors == null ? null : actors.trim();
    }

    
    public String getArea() {
		return area;
	}

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getMoviePath() {
        return moviePath;
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath == null ? null : moviePath.trim();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot == null ? null : plot.trim();
    }
}