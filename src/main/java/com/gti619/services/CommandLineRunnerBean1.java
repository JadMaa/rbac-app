package com.gti619.services;//package com.example.rbacapp.commons;
//
//
//import User;
//import UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//@Component
//public class CommandLineRunnerBean1 implements CommandLineRunner {
//
//    private UserRepository userRepository;
//
//    @Autowired
//    public CommandLineRunnerBean1(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public void run(String... args) {
//       this.userRepository.deleteAll();
//
//        List<String> adminRoles = new ArrayList<>();
//        adminRoles.add("ADMIN");
//        adminRoles.add("RESIDENTIAL");
//        adminRoles.add("BUSINESS");
//        this.userRepository.save(new User("admin","secret1",adminRoles));
//
//        List<String> residRoles = new ArrayList<>();
//        adminRoles.add("RESIDENTIAL");
//        this.userRepository.save(new User("resid","secret2", residRoles));
//
//        List<String> busiRoles = new ArrayList<>();
//        adminRoles.add("BUSINESS");
//        this.userRepository.save(new User("busi","secret3", busiRoles));
//    }
//
//}
