package net.noncore.fdx.common.values;

import lombok.Data;

@Data(staticConstructor = "of")
public class Size {
    private final long bites;
}
