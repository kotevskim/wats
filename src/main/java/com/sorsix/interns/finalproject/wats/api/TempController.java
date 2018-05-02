package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping(value = "/api")
public class TempController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;

    @GetMapping(value = "user")
    @ResponseBody
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping(value = "test")
    @ResponseBody
    public String test() {
//        userDao.save(new User("Kosta", "koki96", passwordEncoder.encode("kostadin")));
        String tmp = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return tmp;
    }
}
