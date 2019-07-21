package net.noncore.fdx.data.repositories;

import net.noncore.fdx.data.entities.file.File;
import net.noncore.fdx.data.entities.file.FileType;
import net.noncore.fdx.data.entities.file.Path;
import net.noncore.fdx.data.entities.file.Size;
import net.noncore.fdx.helper.Case;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JavaFileRepository implements FileRepository {
    @Override
    public File find(Path path) {
        return toFile(Paths.get(path.getAbsolutePath()).toFile());
    }

    private File toFile(java.io.File file) {
        return File.builder()
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

    private FileType toType(java.io.File file) {
        return Case.of(file)
                .when(java.io.File::isDirectory).then(FileType.FOLDER)
                .when(java.io.File::isFile).then(FileType.FILE)
                .other().then(FileType.OTHER)
                .end();
    }

    private Optional<Size> getSize(java.io.File file) {
        if (file.isFile()) {
            return Optional.of(Size.of(file.length()));
        }
        return Optional.empty();
    }

    private List<File> toFiles(java.io.File[] files) {
        return Stream.of(files)
                .map(this::toFile)
                .collect(Collectors.toList());
    }

    private ZonedDateTime toZonedDateTime(long millis) {
        return ZonedDateTime.ofInstant(
                FileTime.fromMillis(millis).toInstant(),
                ZoneId.systemDefault());
    }

    @Override
    public List<File> getRoots() {
        return toFiles(java.io.File.listRoots());
    }

    @Override
    public List<File> findFiles(Path path) {
        return toFiles(Paths.get(path.getAbsolutePath()).toFile().listFiles());
    }

    @Override
    public File createFile(Path path) {
        java.nio.file.Path filePath = Paths.get(path.getAbsolutePath());
        try {
            Files.createFile(filePath);
            return toFile(filePath.toFile());
        } catch (IOException e) {
            throw new FileErrorException(Path.of(path.toString()), e);
        }
    }

    @Override
    public File createFolder(Path path) {
        java.nio.file.Path filePath = Paths.get(path.getAbsolutePath());
        try {
            Files.createDirectories(filePath);
            return toFile(filePath.toFile());
        } catch (IOException e) {
            throw new FileErrorException(Path.of(path.toString()), e);
        }
    }

    @Override
    public File copyFile(File target, Path destination) {
        try {
            java.nio.file.Path path = Files.copy(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return (File) toFile(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public File copyFolder(File target, Path destination) {
        try {
            java.nio.file.Path path = Files.copy(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFile(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public File moveFile(File target, Path destination) {
        try {
            java.nio.file.Path path = Files.move(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFile(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public File moveFolder(File target, Path destination) {
        try {
            java.nio.file.Path path = Files.move(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getAbsolutePath()));
            return toFile(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
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
                .map(File::getSize)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Size::getBites)
                .mapToLong(l -> l)
                .sum();
        return Size.of(bites);
    }
}
