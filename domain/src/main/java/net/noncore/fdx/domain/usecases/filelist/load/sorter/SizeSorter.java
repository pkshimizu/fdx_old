package net.noncore.fdx.domain.usecases.filelist.load.sorter;

import net.noncore.fdx.common.values.Size;
import net.noncore.fdx.domain.usecases.filelist.load.FileDto;

public class SizeSorter implements Sorter {
    @Override
    public int sortByProperty(FileDto file1, FileDto file2) {
        return Long.compare(
                file1.getSize().map(Size::getBites).orElse(0L),
                file2.getSize().map(Size::getBites).orElse(0L));
    }
}
