package net.noncore.fdx.domain.repositories;

import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.common.values.Size;
import net.noncore.fdx.domain.models.FileModel;

import java.util.List;

public interface FileRepository {
    FileModel find(Path path);
    List<FileModel> getRoots();
    List<FileModel> findFiles(Path path);
    FileModel createFile(Path path);
    FileModel createFolder(Path path);
    FileModel copyFile(FileModel target, Path destination);
    FileModel copyFolder(FileModel target, Path destination);
    FileModel moveFile(FileModel target, Path destination);
    FileModel moveFolder(FileModel target, Path destination);
    void deleteFile(Path path);
    void deleteFolder(Path path);
    Size computeSize(Path path);
}
