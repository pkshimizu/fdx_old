package net.noncore.fdx.presentation.views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.presentation.viewmodel.FileListViewModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileListView extends View<AnchorPane> {
    @NonNull
    private FileListViewModel viewModel;
    @FXML
    private ListView listView;

    @Override
    protected String getFxmlName() {
        return "FileListView.fxml";
    }

    @Override
    protected void initialize(AnchorPane root) {
        viewModel.initialize(listView);
    }
}
