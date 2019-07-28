package net.noncore.fdx.presentation.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

@Getter
public class FileListCellView extends View<AnchorPane> {
    @FXML
    private Label name;
    @FXML
    private Label size;
    @FXML
    private Label date;

    @Override
    protected String getFxmlName() {
        return "FileListCellView.fxml";
    }

    @Override
    protected void initialize(AnchorPane root) {
    }
}
