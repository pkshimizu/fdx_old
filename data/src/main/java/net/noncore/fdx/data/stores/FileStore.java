package net.noncore.fdx.data.stores;

import net.noncore.fdx.common.types.FileType;
import net.noncore.fdx.common.utils.Case;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.common.values.Size;
import net.noncore.fdx.domain.models.FileModel;
import net.noncore.fdx.domain.repositories.FileErrorException;
import net.noncore.fdx.domain.repositories.FileRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileStore implements FileRepository {
    @Override
    public FileModel find(Path path) {
        return toFileModel(Paths.get(path.getAbsolutePath()).toFile());
    }

    private FileModel toFileModel(File file) {
        return FileModel.builder()
                .type(toType(file))
                .path(Path.of(file.getAbsolutePath()))
                .readable(file.canRead())
                .writable(file.canWrite())
                .executable(file.canExecute())
                .hidden(file.isHidden())
                .exists(file.exists())
                .dateTime(toZonedDateTime(file.lastModified()))
                .size(getSize(file))
                .build();
    }

    private FileType toType(File file) {
        return Case.of(file)
                .when(File::isDirectory).then(FileType.FOLDER)
                .when(File::isFile).then(FileType.FILE)
                .other().then(FileType.OTHER)
                .end();
    }

    private Optional<Size> getSize(File file) {
        if (file.isFile()) {
            return Optional.of(Size.of(file.length()));
        }
        return Optional.empty();
    }

    private List<FileModel> toFileModels(File[] files) {
        if (ArrayUtils.isEmpty(files)) {
            return Collections.emptyList();
        }
        return Stream.of(files)
                .map(this::toFileModel)
                .collect(Collectors.toList());
    }

    private ZonedDateTime toZonedDateTime(long millis) {
        return ZonedDateTime.ofInstant(
                FileTime.fromMillis(millis).toInstant(),
                ZoneId.systemDefault());
    }

    @Override
    public List<FileModel> getRoots() {
        return toFileModels(File.listRoots());
    }

    @Override
    public List<FileModel> findFiles(Path path) {
        return toFileModels(Paths.get(path.getAbsolutePath()).toFile().listFiles());
    }

    @Override
    public FileModel createFile(Path path) {
        java.nio.file.Path filePath = Paths.get(path.getAbsolutePath());
        try {
            Files.createFile(filePath);
            return toFileModel(filePath.toFile());
        } catch (IOException e) {
            throw new FileErrorException(Path.of(path.toString()), e);
        }
    }

    @Override
    public FileModel createFolder(Path path) {
        java.nio.file.Path filePath = Paths.get(path.getAbsolutePath());
        try {
            Files.createDirectories(filePath);
            return toFileModel(filePath.toFile());
        } catch (IOException e) {
            throw new FileErrorException(Path.of(path.toString()), e);
        }
    }

    @Override
    public FileModel copyFile(FileModel target, Path destination) {
        try {
            java.nio.file.Path path = Files.copy(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFileModel(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), destination, e);
        }
    }

    @Override
    public FileModel copyFolder(FileModel target, Path destination) {
        try {
            java.nio.file.Path path = Files.copy(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFileModel(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), destination, e);
        }
    }

    @Override
    public FileModel moveFile(FileModel target, Path destination) {
        try {
            java.nio.file.Path path = Files.move(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFileModel(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), destination, e);
        }
    }

    @Override
    public FileModel moveFolder(FileModel target, Path destination) {
        try {
            java.nio.file.Path path = Files.move(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFileModel(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), destination, e);
        }
    }

    @Override
    public void deleteFile(Path target) {
        try {
            Files.delete(Paths.get(target.getAbsolutePath()));
        } catch (IOException e) {
            throw new FileErrorException(target, e);
        }
    }

    @Override
    public void deleteFolder(Path target) {
        try {
            Files.delete(Paths.get(target.getAbsolutePath()));
        } catch (IOException e) {
            throw new FileErrorException(target, e);
        }
    }

    @Override
    public Size computeSize(Path path) {
        long bites = findFiles(path).stream()
                .map(FileModel::getSize)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Size::getBites)
                .mapToLong(l -> l)
                .sum();
        return Size.of(bites);
    }
}
