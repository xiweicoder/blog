package com.lfs.service;

import com.lfs.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    //    上传文件(图像图片)
    ResponseResult uploadImg(MultipartFile img);
}
