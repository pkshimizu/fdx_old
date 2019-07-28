package net.noncore.fdx.presentation.viewmodel;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.common.types.FileListSortType;
import net.noncore.fdx.common.types.FileType;
import net.noncore.fdx.common.utils.Case;
import net.noncore.fdx.common.values.Path;
import net.noncore.fdx.domain.usecases.filelist.load.FileDto;
import net.noncore.fdx.domain.usecases.filelist.load.FileListLoadRequest;
import net.noncore.fdx.domain.usecases.filelist.load.FileListLoadResponse;
import net.noncore.fdx.domain.usecases.filelist.load.FileListLoadUsecase;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ENTER;

@Component
@RequiredArgsConstructor
public class FileListViewModel {
    private FileListViewOperator operator;
    @NonNull
    private FileListLoadUsecase fileListLoadUsecase;
    private Path current;
    private FileListSortType sortType = FileListSortType.NAME;

    public void initialize(FileListViewOperator operator) {
        this.operator = operator;
        loadFileList(current);
    }

    public void onKeyReleased(KeyEvent event) {
        Case.of(event.getCode())
                .when(ENTER).call(code -> loadSelectFolder())
                .when(BACK_SPACE).call(code -> loadParentFolder());
    }

    public void onMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            loadSelectFolder();
        }
    }

    private void loadSelectFolder() {
        FileDto file = operator.getSelectedItem();
        if (file.getType() == FileType.FOLDER) {
            loadFileList(current.getChild(file.getName()));
        }
    }

    private void loadParentFolder() {
        current.getParent().ifPresent(this::loadFileList);
    }

    private void loadFileList(Path path) {
        FileListLoadResponse response = fileListLoadUsecase.doIt(
                FileListLoadRequest.builder()
                        .path(Optional.ofNullable(path))
                        .sortType(sortType)
                        .build());
        current = response.getPath();
        operator.updateFileList(response.getFiles());
    }

}
