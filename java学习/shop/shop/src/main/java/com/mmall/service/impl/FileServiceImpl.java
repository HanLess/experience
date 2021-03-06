package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file,String path){
        // 原始文件名
        String fileName = file.getOriginalFilename();
        // 扩展名
        String extentionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString() + extentionName;
        logger.info("开始上传文件，上传文件名{}，上传路径{}，新文件名{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //  上传ftp
//            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 删除上传的文件
//            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件一场",e);
            return null;
        }
        return targetFile.getName();
    }
}
