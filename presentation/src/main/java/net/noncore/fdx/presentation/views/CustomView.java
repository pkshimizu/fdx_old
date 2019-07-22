package net.noncore.fdx.presentation.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class CustomView<T extends Parent> extends View<T> {
    protected abstract T createRoot();

    @Override
    final protected void preload(FXMLLoader loader) {
        loader.setRoot(createRoot());
    }
}
