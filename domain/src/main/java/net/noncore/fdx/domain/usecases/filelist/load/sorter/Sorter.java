package net.noncore.fdx.domain.usecases.filelist.load.sorter;

import net.noncore.fdx.common.types.FileType;
import net.noncore.fdx.domain.usecases.filelist.load.FileDto;

public interface Sorter {
    default int sort(FileDto file1, FileDto file2) {
        if (file1.getType() != file2.getType() && (file1.getType() == FileType.FOLDER || file2.getType() == FileType.FOLDER)) {
            return file1.getType() == FileType.FOLDER ? -1 : 1;
        }
        return sortByProperty(file1, file2);
    }

    int sortByProperty(FileDto file1, FileDto file2);
}
