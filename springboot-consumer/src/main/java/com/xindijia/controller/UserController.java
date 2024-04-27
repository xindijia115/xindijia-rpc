package com.xindijia.controller;

import com.xindijia.service.XindijiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author DJ
 * @since 2024/4/27/19:25
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private XindijiaService xindijiaService;

    @GetMapping("/user/info")
    public String getUser() {
        return xindijiaService.getUser();
    }
}
