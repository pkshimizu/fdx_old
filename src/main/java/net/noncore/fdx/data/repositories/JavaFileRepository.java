package net.noncore.fdx.data.repositories;

import net.noncore.fdx.data.entities.file.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFileRepository implements FileRepository {
    @Override
    public FileResource find(Path path) {
        return createFileResource(Paths.get(path.getAbsolutePath()).toFile());
    }

    private FileResource createFileResource(java.io.File file) {
        if (file.isDirectory()) {
            return Folder.builder()
                    .path(Path.of(file.getAbsolutePath()))
                    .readable(file.canRead())
                    .writable(file.canWrite())
                    .executable(file.canExecute())
                    .hidden(file.isHidden())
                    .exists(file.exists())
                    .dateTime(toZonedDateTime(file.lastModified()))
                    .resources(toResources(file.listFiles()))
                    .build();
        }
        return File.builder()
                .path(Path.of(file.getAbsolutePath()))
                .readable(file.canRead())
                .writable(file.canWrite())
                .executable(file.canExecute())
                .hidden(file.isHidden())
                .exists(file.exists())
                .dateTime(toZonedDateTime(file.lastModified()))
                .size(Size.of(file.length()))
                .build();
    }

    private List<FileResource> toResources(java.io.File[] files) {
        return Stream.of(files)
                .map(this::createFileResource)
                .collect(Collectors.toList());
    }

    private ZonedDateTime toZonedDateTime(long millis) {
        return ZonedDateTime.ofInstant(
                FileTime.fromMillis(millis).toInstant(),
                ZoneId.systemDefault());
    }

    @Override
    public List<Folder> getRoots() {
        return toResources(java.io.File.listRoots()).stream()
                .filter(Folder.class::isInstance)
                .map(file -> (Folder)file)
                .collect(Collectors.toList());
    }

    @Override
    public File createFile(Folder folder, String name) {
        java.nio.file.Path path = Paths.get(folder.getPath().getAbsolutePath(), name);
        try {
            Files.createFile(path);
            return (File) createFileResource(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(Path.of(path.toString()), e);
        }
    }

    @Override
    public Folder createFolder(Folder folder, String name) {
        java.nio.file.Path path = Paths.get(folder.getPath().getAbsolutePath(), name);
        try {
            Files.createDirectories(path);
            return (Folder) createFileResource(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(Path.of(path.toString()), e);
        }
    }

    @Override
    public File copyFile(File target, Folder destination) {
        try {
            java.nio.file.Path path = Files.copy(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getPath().getAbsolutePath()));
            return (File) createFileResource(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public Folder copyFolder(Folder target, Folder destination) {
        try {
            java.nio.file.Path path = Files.copy(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getPath().getAbsolutePath()));
            return (Folder) createFileResource(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public File moveFile(File target, Folder destination) {
        try {
            java.nio.file.Path path = Files.move(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getPath().getAbsolutePath()));
            return (File) createFileResource(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public Folder moveFolder(Folder target, Folder destination) {
        try {
            java.nio.file.Path path = Files.move(
                    Paths.get(target.getPath().getAbsolutePath()),
                    Paths.get(destination.getPath().getAbsolutePath()));
            return (Folder) createFileResource(path.toFile());
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public void deleteFile(File target) {
        try {
            Files.delete(Paths.get(target.getPath().getAbsolutePath()));
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public void deleteFolder(Folder target) {
        try {
            Files.delete(Paths.get(target.getPath().getAbsolutePath()));
        } catch (IOException e) {
            throw new FileErrorException(target.getPath(), e);
        }
    }

    @Override
    public Size computeSize(Folder folder) {
        long bites = folder.getResources().stream()
                .filter(File.class::isInstance)
                .map(File.class::cast)
                .map(File::getSize)
                .map(Size::getBites)
                .mapToLong(l -> l)
                .sum();
        return Size.of(bites);
    }
}
