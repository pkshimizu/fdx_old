package net.noncore.fdx.data.entities.file;

import lombok.Builder;
import lombok.Data;

@Data(staticConstructor = "of")
public class Size {
    private final long bites;
}
