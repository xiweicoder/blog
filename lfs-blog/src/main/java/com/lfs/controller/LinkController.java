package com.lfs.controller;

import com.lfs.domain.ResponseResult;
import com.lfs.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    //    友链
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
