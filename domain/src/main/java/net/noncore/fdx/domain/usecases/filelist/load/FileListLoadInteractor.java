package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.common.types.FileListSortType;
import net.noncore.fdx.common.utils.Case;
import net.noncore.fdx.domain.models.FileModel;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.common.values.Size;
import net.noncore.fdx.domain.repositories.FileRepository;
import net.noncore.fdx.domain.usecases.UsecaseError;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.DateSorter;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.NameSorter;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.SizeSorter;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.Sorter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileListLoadInteractor implements FileListLoadUsecase {
    @NonNull
    private final FileRepository fileRepository;

    @Override
    public FileListLoadResponse doIt(FileListLoadRequest request) throws UsecaseError {
        Path path = request.getPath().orElse(Path.USER_HOME);
        List<FileDto> files = fileRepository.findFiles(path).stream()
                .map(this::toFileModel)
                .collect(Collectors.toList());
        return FileListLoadResponse.builder()
                .path(path)
                .files(sort(files, request.getSortType()))
                .build();
    }

    private FileDto toFileModel(FileModel file) {
        return FileDto.builder()
                .name(file.getPath().getName())
                .type(file.getType())
                .size(file.getSize())
                .dateTime(file.getDateTime())
                .readable(file.isReadable())
                .writable(file.isWritable())
                .executable(file.isExecutable())
                .hidden(file.isHidden())
                .executable(file.isExecutable())
                .exists(file.isExists())
                .build();
    }

    private List<FileDto> sort(List<FileDto> files, FileListSortType sortType) {
        Sorter sorter = Case.of(sortType)
                .when(FileListSortType.NAME).then(new NameSorter())
                .when(FileListSortType.SIZE).then(new SizeSorter())
                .when(FileListSortType.DATE).then(new DateSorter())
                .end();
        files.sort(sorter::sort);
        return files;
    }
}
