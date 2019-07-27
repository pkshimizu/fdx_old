package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.Builder;
import lombok.Value;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.domain.usecases.Response;

import java.util.List;

@Value
@Builder
public class FileListLoadResponse implements Response {
    private Path path;
    private List<FileDto> files;
}
