package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.domain.models.FileModel;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.common.values.Size;
import net.noncore.fdx.domain.repositories.FileRepository;
import net.noncore.fdx.domain.usecases.UsecaseError;
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
        List<FileDto> files = fileRepository.findFiles(Path.USER_HOME).stream()
                .map(this::toFileModel)
                .collect(Collectors.toList());
        return FileListLoadResponse.builder()
                .files(files)
                .build();
    }

    private FileDto toFileModel(FileModel file) {
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
