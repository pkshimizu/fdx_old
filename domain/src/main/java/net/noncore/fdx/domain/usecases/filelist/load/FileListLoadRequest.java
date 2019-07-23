package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.Value;
import net.noncore.fdx.domain.usecases.Request;

@Value
public class FileListLoadRequest implements Request {
    private String url;
}
