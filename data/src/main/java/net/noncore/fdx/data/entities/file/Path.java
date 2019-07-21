package net.noncore.fdx.data.entities.file;

import lombok.Data;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Data
public class Path {
    private final String name;
    private final String absolutePath;

    public static final Path USER_HOME = Path.of(System.getProperty("user.home"));

    public static Path of(String text) {
        if (isEmpty(text)) {
            return null;
        }
        java.io.File file = new java.io.File(text);
        return new Path(file.getName(), file.getAbsolutePath());
    }

    public Optional<Path> getParent() {
        java.io.File file = new java.io.File(absolutePath);
        return file.getParent() == null ?
                Optional.empty() :
                Optional.ofNullable(Path.of(file.getParent()));
    }
}
