package net.noncore.fdx.data.entities.file;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZonedDateTime;

@Getter
public final class File extends FileResource {
    private final Size size;

    @Builder
    public File(Path path,
                boolean readable, boolean writable, boolean executable,
                boolean hidden, boolean exists, ZonedDateTime dateTime,
                InputStream inputStream, OutputStream outputStream, Size size) {
        super(path, readable, writable, executable, hidden, exists, dateTime);
        this.size = size;
    }
}
