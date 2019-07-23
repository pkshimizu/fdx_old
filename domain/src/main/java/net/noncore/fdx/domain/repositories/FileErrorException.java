package net.noncore.fdx.domain.repositories;

import lombok.Getter;
import net.noncore.fdx.common.values.Path;

import java.util.Optional;

@Getter
public class FileErrorException extends RuntimeException {
    private final Path path;
    private final Optional<Path> destination;

    public FileErrorException(Path path, Throwable cause) {
        super(cause);
        this.path = path;
        this.destination = Optional.empty();
    }

    public FileErrorException(Path path, Path destination, Throwable cause) {
        super(cause);
        this.path = path;
        this.destination = Optional.ofNullable(destination);
    }

    public FileErrorException(Path path, String message) {
        super(message);
        this.path = path;
        this.destination = Optional.empty();
    }

    public FileErrorException(Path path, Path destination, String message) {
        super(message);
        this.path = path;
        this.destination = Optional.ofNullable(destination);
    }
}
