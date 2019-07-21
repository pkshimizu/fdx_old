package net.noncore.fdx.gui.views;

import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class StorageInfoView extends CustomView<HBox> {
    @Override
    protected HBox createRoot() {
        return new HBox();
    }

    @Override
    protected String getFxmlName() {
        return "StorageInfoView.fxml";
    }

    @Override
    protected void initialize() {

    }
}
