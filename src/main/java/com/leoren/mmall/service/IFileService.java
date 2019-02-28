package com.leoren.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName IFileService
 * @Auther Leoren
 * @Date 2019/2/24 20:49
 * @Desc : 文件处理Service
 * @Version v1.0
 **/

public interface IFileService {

    String upload(MultipartFile file, String path);


}
