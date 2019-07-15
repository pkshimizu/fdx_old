package net.noncore.fdx.data.entities.file;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public final class Folder extends FileResource {
    private final List<FileResource> resources;

    @Builder
    public Folder(Path path,
                boolean readable, boolean writable, boolean executable,
                boolean hidden, boolean exists, ZonedDateTime dateTime,
                List<FileResource> resources) {
        super(path, readable, writable, executable, hidden, exists, dateTime);
        this.resources = resources;
    }
}
