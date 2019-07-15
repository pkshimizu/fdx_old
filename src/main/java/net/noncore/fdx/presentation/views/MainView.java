package net.noncore.fdx.presentation.views;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainView extends View<AnchorPane> {
    @NonNull
    private FileInfoView fileInfoView;
    @NonNull
    private FileListView fileListView;
    @NonNull
    private StorageInfoView storageInfoView;
    @NonNull
    private SystemInfoView systemInfoView;
    @FXML
    private VBox header;
    @FXML
    private BorderPane layout;

    @Override
    protected String getFxmlName() {
        return "MainView.fxml";
    }

    @Override
    protected void initialize() {
        header.getChildren().addAll(
            systemInfoView.getRoot(),
            storageInfoView.getRoot(),
            fileInfoView.getRoot()
        );
        layout.setCenter(fileListView.getRoot());
    }
}
