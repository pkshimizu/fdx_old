package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.Value;
import net.noncore.fdx.domain.usecases.Request;

@Value
public class LoadFileListRequest implements Request {
    private String url;
}
