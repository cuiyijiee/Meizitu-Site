package me.cuiyijie.nongmo.controller.admin;

import me.cuiyijie.nongmo.entity.FileUpload;
import me.cuiyijie.nongmo.entity.vo.AlbumDetailVO;
import me.cuiyijie.nongmo.entity.vo.AlbumVO;
import me.cuiyijie.nongmo.service.AlbumAutoCheckService;
import me.cuiyijie.nongmo.service.AlbumService;
import me.cuiyijie.nongmo.service.FileUploadService;
import me.cuiyijie.nongmo.trans.request.TransAlbumRequest;
import me.cuiyijie.nongmo.trans.response.TransBaseResponse;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/album")
public class AdminAlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private AlbumAutoCheckService albumAutoCheckService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public TransBaseResponse listAlbum(@RequestBody TransAlbumRequest transAlbumRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        PageUtil.PageResp<AlbumVO> albumPageResp = albumService.listAlbum(transAlbumRequest);
        transBaseResponse.setObj(albumPageResp);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }

    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public TransBaseResponse getAlbumDetail(@RequestBody TransAlbumRequest transAlbumRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        AlbumDetailVO albumDetailVO = albumService.getAlbumDetail(transAlbumRequest.getId());
        transBaseResponse.setObj(albumDetailVO);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public TransBaseResponse getJsoupTest() {
        albumAutoCheckService.check();
        return new TransBaseResponse();
    }

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public TransBaseResponse uploadFile(@RequestParam("md5") String md5, MultipartFile file) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        FileUpload fileUpload = fileUploadService.uploadFile(md5, file);
        transBaseResponse.setObj(fileUpload);
        transBaseResponse.setCode("0");
        return transBaseResponse;
    }

}
