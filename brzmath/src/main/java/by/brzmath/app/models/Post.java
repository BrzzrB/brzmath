package by.brzmath.app.models;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, condition, theme, userId, answerone, answertwo, answerthree;
    private int views;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswerone() {
        return answerone;
    }

    public void setAnswerone(String answerOne) {
        this.answerone = answerOne;
    }

    public String getAnswertwo() {
        return answertwo;
    }

    public void setAnswertwo(String answerTwo) {
        this.answertwo = answerTwo;
    }

    public String getAnswerthree() {
        return answerthree;
    }

    public void setAnswerthree(String answerThree) {
        this.answerthree = answerThree;
    }

    public Post() {
    }

    public Post(String title, String condition, String theme, String userId, String answerone, String answertwo, String answerthree) {
        this.title = title;
        this.condition = condition;
        this.theme = theme;
        this.userId = userId;
        this.answerone = answerone;
        this.answertwo = answertwo;
        this.answerthree = answerthree;
    }
}

