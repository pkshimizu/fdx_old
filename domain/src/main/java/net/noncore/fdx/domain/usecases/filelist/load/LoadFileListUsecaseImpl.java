package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.data.entities.file.File;
import net.noncore.fdx.data.entities.file.Path;
import net.noncore.fdx.data.entities.file.Size;
import net.noncore.fdx.data.repositories.FileRepository;
import net.noncore.fdx.domain.dtos.FileDto;
import net.noncore.fdx.domain.usecases.UsecaseError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoadFileListUsecaseImpl implements LoadFileListUsecase {
    @NonNull
    private final FileRepository fileRepository;

    @Override
    public LoadFileListResponse doIt(LoadFileListRequest request) throws UsecaseError {
        List<FileDto> files = fileRepository.findFiles(Path.USER_HOME).stream()
                .map(this::toFileDto)
                .collect(Collectors.toList());
        return LoadFileListResponse.builder()
                .files(files)
                .build();
    }

    private FileDto toFileDto(File file) {
        return FileDto.builder()
                .name(file.getPath().getName())
                .size(file.getSize().map(Size::getBites))
                .dateTime(file.getDateTime())
                .readable(file.isReadable())
                .writable(file.isWritable())
                .executable(file.isExecutable())
                .hidden(file.isHidden())
                .executable(file.isExecutable())
                .exists(file.isExists())
                .build();
    }
}
