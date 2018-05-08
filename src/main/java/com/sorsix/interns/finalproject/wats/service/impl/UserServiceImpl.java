package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDAO;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import com.sorsix.interns.finalproject.wats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ReviewDAO reviewDAO;
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(ReviewDAO reviewDAO, UserDAO userDAO) {
        this.reviewDAO = reviewDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Collection<Review> getUserReviews(Long userId) {
        return reviewDAO.findByUserId(userId);
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return userDAO.findById(userId);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User createUser(String name, String username, String email, String password, String pictureUrl) {
        return this.userDAO.save(new User(name, username, email, password, pictureUrl));
    }
}
