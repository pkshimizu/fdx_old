package net.noncore.fdx.domain.usecases.filelist.load.sorter;

import net.noncore.fdx.domain.usecases.filelist.load.FileDto;

public class NameSorter implements Sorter {
    @Override
    public int sortByProperty(FileDto file1, FileDto file2) {
        return file1.getName().compareToIgnoreCase(file2.getName());
    }
}
