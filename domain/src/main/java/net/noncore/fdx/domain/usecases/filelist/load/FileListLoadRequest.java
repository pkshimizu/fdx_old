package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.Builder;
import lombok.Value;
import net.noncore.fdx.common.types.FileListSortType;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.domain.usecases.Request;

import java.util.Optional;

@Value
@Builder
public class FileListLoadRequest implements Request {
    private Optional<Path> path;
    private FileListSortType sortType;
}
