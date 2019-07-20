package net.noncore.fdx.data.repositories;

import net.noncore.fdx.data.entities.file.*;

import java.util.List;

public interface FileRepository {
    File find(Path path);
    List<File> getRoots();
    List<File> findFiles(Path path);
    File createFile(Path path);
    File createFolder(Path path);
    File copyFile(File target, Path destination);
    File copyFolder(File target, Path destination);
    File moveFile(File target, Path destination);
    File moveFolder(File target, Path destination);
    void deleteFile(Path path);
    void deleteFolder(Path path);
    Size computeSize(Path path);
}
