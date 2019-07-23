package net.noncore.fdx.domain.models;

import lombok.Builder;
import lombok.Getter;
import net.noncore.fdx.common.types.FileType;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.common.values.Size;

import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
@Builder
public final class FileModel {
    private final FileType type;
    private final Path path;
    private final Optional<Size> size;
    private final boolean readable;
    private final boolean writable;
    private final boolean executable;
    private final boolean hidden;
    private final boolean exists;
    private final ZonedDateTime dateTime;
}
