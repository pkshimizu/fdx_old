package net.noncore.fdx.presentation.views;

import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class SystemInfoView extends CustomView<HBox> {

    @Override
    protected HBox createRoot() {
        return new HBox();
    }

    @Override
    protected String getFxmlName() {
        return "SystemInfoView.fxml";
    }

    @Override
    protected void initialize() {

    }
}
