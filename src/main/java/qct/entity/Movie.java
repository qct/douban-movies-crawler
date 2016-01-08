package qct.entity;

import com.google.inject.Inject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;
import qct.MovieRepo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by quchentao on 16/1/4.
 */
@Entity(value = "movies", noClassnameStored = true)
public class Movie extends BaseEntity {
    private String name;
    private String directedBy;
    private String writer;
    private String actor;
    private String type;
    private String website;
    private String area;
    private String language;
    private String initialReleaseDate;
    private String runtime;
    private String otherName;
    private String imdbLink;
    private String ratingNum;

    @Inject
    @Transient
    MovieRepo movieRepo;

    public Movie() {
    }

    public Movie(String name) {
        this.name = name;
        movieRepo = new MovieRepo();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInitialReleaseDate() {
        return initialReleaseDate;
    }

    public void setInitialReleaseDate(String initialReleaseDate) {
        this.initialReleaseDate = initialReleaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public void setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
    }

    public String getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(String ratingNum) {
        this.ratingNum = ratingNum;
    }

    public void save() {
        movieRepo.insert(this);
    }

    public Movie trim() throws IllegalAccessException {
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(this) != null && Modifier.PRIVATE == field.getModifiers()) {
                field.set(this, field.get(this).toString().trim());
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", directedBy='" + directedBy + '\'' +
                ", writer='" + writer + '\'' +
                ", actor='" + actor + '\'' +
                ", type='" + type + '\'' +
                ", website='" + website + '\'' +
                ", area='" + area + '\'' +
                ", language='" + language + '\'' +
                ", initialReleaseDate='" + initialReleaseDate + '\'' +
                ", runtime='" + runtime + '\'' +
                ", otherName='" + otherName + '\'' +
                ", imdbLink='" + imdbLink + '\'' +
                ", ratingNum='" + ratingNum + '\'' +
                '}';
    }
}
