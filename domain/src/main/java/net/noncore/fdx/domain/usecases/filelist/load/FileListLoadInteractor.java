package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.common.types.FileListSortType;
import net.noncore.fdx.common.utils.Case;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.domain.models.FileModel;
import net.noncore.fdx.domain.repositories.FileRepository;
import net.noncore.fdx.domain.usecases.UsecaseError;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.DateSorter;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.NameSorter;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.SizeSorter;
import net.noncore.fdx.domain.usecases.filelist.load.sorter.Sorter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileListLoadInteractor implements FileListLoadUsecase {
    @NonNull
    private final FileRepository fileRepository;

    @Override
    public FileListLoadResponse doIt(FileListLoadRequest request) throws UsecaseError {
        Path path = request.getPath().orElse(Path.USER_HOME);
        assert path != null;

        List<FileDto> files = fileRepository.findFiles(path).stream()
                .map(this::toFileDto)
                .collect(Collectors.toList());
        path.getParent().ifPresent(parent -> {
            files.add(0, toFileDto(fileRepository.find(parent), ".."));
        });
        return FileListLoadResponse.builder()
                .path(path)
                .files(sort(files, request.getSortType()))
                .build();
    }

    private FileDto toFileDto(FileModel file, String name) {
        return FileDto.builder()
                .name(name)
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

    private FileDto toFileDto(FileModel file) {
        return toFileDto(file, file.getPath().getName());
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
