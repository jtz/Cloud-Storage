package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer addFile(File file) { return fileMapper.insert(file);}

    public Integer updateFile(File file) {
        return fileMapper.update(file);
    }

    public Integer deleteFile(Integer fileId) {
        return fileMapper.delete(fileId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<File> getAllFiles(User user) {
        return fileMapper.getAllFilesByUser(user.getUserId());
    }

    public boolean isFileNameAvailable(String fileName) {
        return fileMapper.getFileByName(fileName) == null;
    }
}
