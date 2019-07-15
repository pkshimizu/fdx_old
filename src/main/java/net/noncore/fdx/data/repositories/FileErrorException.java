package net.noncore.fdx.data.repositories;

import lombok.Getter;
import net.noncore.fdx.data.entities.file.Path;

@Getter
public class FileErrorException extends RuntimeException {
    private final Path path;

    public FileErrorException(Path path, Throwable cause) {
        super(cause);
        this.path = path;
    }

    public FileErrorException(Path path, String message) {
        super(message);
        this.path = path;
    }
}
