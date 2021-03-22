package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesForUser(Integer id) {
        return fileMapper.getFilesForUser(id);
    }

    public int createFile(MultipartFile uploadFile, Integer userid) throws IOException {
        File file = new File();
        file.setFileName(uploadFile.getOriginalFilename());
        file.setContentType(uploadFile.getContentType());
        file.setFileData(uploadFile.getBytes());
        file.setFileSize(uploadFile.getSize());
        file.setUserId(userid);
        return fileMapper.insert(file);
    }

    public int deleteFile(Integer fileId) {
        return fileMapper.delete(fileId);
    }

    public File getFileById(Integer id) {
        return fileMapper.getFileId(id);
    }

    public boolean filenameAvailable(String fileName, Integer userId) {
        return fileMapper.getFileByName(fileName, userId) != null;
    }

}
