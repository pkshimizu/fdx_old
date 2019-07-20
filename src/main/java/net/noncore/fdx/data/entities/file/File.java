package net.noncore.fdx.data.entities.file;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
@Builder
public final class File {
    private final Path path;
    private final Optional<Size> size;
    private final boolean readable;
    private final boolean writable;
    private final boolean executable;
    private final boolean hidden;
    private final boolean exists;
    private final ZonedDateTime dateTime;
}
