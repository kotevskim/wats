package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.persistence.LocationDAO;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/public/locations")
public class TempController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LocationDAO locationDAO;

    @GetMapping("{locationId}")
    public Location user(@PathVariable Long locationId) {
        return this.locationDAO.findById(locationId)
        .orElseThrow(() -> new RuntimeException("no such location"));
    }

    @GetMapping(value = "test")
    public User test() {
        return userDAO.save(new User("Kosta", "koki96", null, passwordEncoder.encode("kostadin"), null));
//        String tmp = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
