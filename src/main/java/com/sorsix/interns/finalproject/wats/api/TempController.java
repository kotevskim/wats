package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping(value = "/api/public")
public class TempController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDAO userDAO;

    @GetMapping(value = "user")
    @ResponseBody
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping(value = "test")
    @ResponseBody
    public User test() {
        return userDAO.save(new User("Kosta", "koki96", null, passwordEncoder.encode("kostadin"), null));
//        String tmp = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
