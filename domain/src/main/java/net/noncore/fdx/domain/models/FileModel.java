package net.noncore.fdx.domain.models;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Optional;

@Value
@Builder
public class FileModel {
    private final String name;
    private final Optional<Long> size;
    private final boolean readable;
    private final boolean writable;
    private final boolean executable;
    private final boolean hidden;
    private final boolean exists;
    private final ZonedDateTime dateTime;
}
