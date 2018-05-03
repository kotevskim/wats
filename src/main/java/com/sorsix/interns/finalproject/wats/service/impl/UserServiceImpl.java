package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDao;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import com.sorsix.interns.finalproject.wats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ReviewDao reviewDao;
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(ReviewDao reviewDao, UserDao userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @Override
    public Collection<Review> getUserReviews(Long userId) {
        return reviewDao.findByUserId(userId);
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return userDao.findById(userId);
    }
}
