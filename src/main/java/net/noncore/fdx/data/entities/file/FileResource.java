package net.noncore.fdx.data.entities.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public abstract class FileResource {
    private final Path path;
    private final boolean readable;
    private final boolean writable;
    private final boolean executable;
    private final boolean hidden;
    private final boolean exists;
    private final ZonedDateTime dateTime;
}
