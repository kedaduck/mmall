package com.leoren.mmall.service.impl;

import com.google.common.collect.Lists;
import com.leoren.mmall.service.IFileService;
import com.leoren.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName FileServiceImpl
 * @Auther Leoren
 * @Date 2019/2/24 20:50
 * @Desc :
 * @Version v1.0
 **/

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件..\n文件名：{},上传的路径：{},新文件名:{}",fileName, path, uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);//赋予写权限
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到我们的FTP服务器上

            //上传完成后 删除upload 下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件失败", e);
            return null;
        }
        return targetFile.getName();
    }


}
