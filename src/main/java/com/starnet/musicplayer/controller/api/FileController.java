package com.starnet.musicplayer.controller.api;


import com.starnet.musicplayer.common.enums.ResultCode;
import com.starnet.musicplayer.model.vo.ResultVO;
import com.starnet.musicplayer.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@RestController
@Slf4j
public class FileController {
    /**
     * 上传文件返回url
     *
     * @param uploadFile 上传的文件
     * @return url
     */
    @PostMapping(value = "/file/upload")
    public ResultVO<String> uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        String url = null;
        try {
            String originalFilename =  uploadFile.getOriginalFilename();
            String newFilename = originalFilename;
            //如果是图片，则重命名
            assert originalFilename != null;
            if(originalFilename.matches("^\\w*\\.(png|jpeg|jpg)$")) {
                newFilename = QiniuUtils.renamePic(Objects.requireNonNull(originalFilename));
            }
            url = QiniuUtils.InputStreamUpload((FileInputStream) uploadFile.getInputStream(), newFilename);
            if (url.contains("error")) {
                return new ResultVO<>(ResultCode.FAILED);
            }
            log.info("上传文件成功"+"Filename: "+originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResultVO<>(url);
    }
}
