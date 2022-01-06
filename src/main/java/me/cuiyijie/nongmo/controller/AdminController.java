package me.cuiyijie.nongmo.controller;

import me.cuiyijie.nongmo.service.AlbumService;
import me.cuiyijie.nongmo.transfer.response.TransBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yjcui3
 * @Date: 2021/12/27 11:16
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AlbumService albumService;


    @RequestMapping(value = "listAlbum", method = RequestMethod.POST)
    private TransBaseResponse listAlbum() {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        return transBaseResponse;
    }

}
