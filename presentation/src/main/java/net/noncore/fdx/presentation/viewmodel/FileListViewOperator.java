package net.noncore.fdx.presentation.viewmodel;

import net.noncore.fdx.domain.usecases.filelist.load.FileDto;

import java.util.List;

public interface FileListViewOperator {
    FileDto getSelectedItem();

    void updateFileList(List<FileDto> files);
}
