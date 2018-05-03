package com.sorsix.interns.finalproject.wats.domain.Forum;

import com.sorsix.interns.finalproject.wats.domain.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "forum_answers")
public class ForumAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumAnswerId;
    private String description;
    private Timestamp datePublished;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "forum_question_id")
    private ForumQuestion forumQuestion;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "forum_answer_likes",
            joinColumns = { @JoinColumn(name = "forum_answer_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    Set<User> likes = new HashSet<>();

    public ForumAnswer() {}

    public Long getForumAnswerId() {
        return forumAnswerId;
    }

    public void setForumAnswerId(Long forumAnswerId) {
        this.forumAnswerId = forumAnswerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ForumQuestion getForumQuestion() {
        return forumQuestion;
    }

    public void setForumQuestion(ForumQuestion forumQuestion) {
        this.forumQuestion = forumQuestion;
    }
}
