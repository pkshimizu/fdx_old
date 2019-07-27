package net.noncore.fdx.common.values;

import javafx.beans.property.StringProperty;
import lombok.Data;

import java.io.File;
import java.nio.file.Paths;
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
        File file = new File(text);
        return new Path(file.getName(), file.getAbsolutePath());
    }

    public Optional<Path> getParent() {
        File file = new File(absolutePath);
        return Optional.ofNullable(file.getParent()).map(Path::of);
    }

    public Path getChild(String name) {
        return Path.of(Paths.get(absolutePath, name).toString());
    }
}
