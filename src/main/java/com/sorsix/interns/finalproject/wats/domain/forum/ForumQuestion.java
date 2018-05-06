package com.sorsix.interns.finalproject.wats.domain.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "forum_questions")
public class ForumQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Timestamp date_published;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public ForumQuestion() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate_published() {
        return date_published;
    }

    public void setDate_published(Timestamp date_published) {
        this.date_published = date_published;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
