package net.noncore.fdx.domain.usecases.filelist.load;

import lombok.Builder;
import lombok.Value;
import net.noncore.fdx.domain.models.FileModel;
import net.noncore.fdx.domain.usecases.Response;

import java.util.List;

@Value
@Builder
public class LoadFileListResponse implements Response {
    private List<FileModel> files;
}
