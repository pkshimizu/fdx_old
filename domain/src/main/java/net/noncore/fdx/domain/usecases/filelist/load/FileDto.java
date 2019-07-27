package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.Builder;
import lombok.Value;
import net.noncore.fdx.common.types.FileType;
import net.noncore.fdx.common.values.Size;

import java.time.ZonedDateTime;
import java.util.Optional;

@Value
@Builder
public class FileDto {
    private final String name;
    private final FileType type;
    private final Optional<Size> size;
    private final boolean readable;
    private final boolean writable;
    private final boolean executable;
    private final boolean hidden;
    private final boolean exists;
    private final ZonedDateTime dateTime;
}
