package net.noncore.fdx.gui.views;

import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class FileInfoView extends CustomView<HBox> {

    @Override
    protected HBox createRoot() {
        return new HBox();
    }

    @Override
    protected String getFxmlName() {
        return "FileInfoView.fxml";
    }

    @Override
    protected void initialize() {

    }
}
