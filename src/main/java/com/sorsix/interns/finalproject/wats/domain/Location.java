package com.sorsix.interns.finalproject.wats.domain;

import com.sorsix.interns.finalproject.wats.domain.review.Review;

import javax.persistence.*;
import java.util.Collection;

@Table(name = "locations")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

   // @OneToMany(mappedBy = "location")
   // public Collection<Review> reviews;

    public Location() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
