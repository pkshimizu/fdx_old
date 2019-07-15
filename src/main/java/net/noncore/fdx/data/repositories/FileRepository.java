package net.noncore.fdx.data.repositories;

import net.noncore.fdx.data.entities.file.*;

import java.util.List;

public interface FileRepository {
    FileResource find(Path path);
    List<Folder> getRoots();
    File createFile(Folder folder, String name);
    Folder createFolder(Folder folder, String name);
    File copyFile(File target, Folder destination);
    Folder copyFolder(Folder target, Folder destination);
    File moveFile(File target, Folder destination);
    Folder moveFolder(Folder target, Folder destination);
    void deleteFile(File target);
    void deleteFolder(Folder target);
    Size computeSize(Folder folder);
}
