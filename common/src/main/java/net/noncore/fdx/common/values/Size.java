package net.noncore.fdx.common.values;

import lombok.Data;

@Data(staticConstructor = "of")
public class Size implements Comparable<Size> {
    private final long bites;

    @Override
    public int compareTo(Size other) {
        return Long.compare(this.getBites(), other.getBites());
    }
}
