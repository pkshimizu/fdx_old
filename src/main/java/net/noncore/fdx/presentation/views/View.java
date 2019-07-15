package net.noncore.fdx.presentation.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

import static java.lang.String.format;

public abstract class View<T extends Parent> {
    private T root;

    public T getRoot() {
        if (root == null) {
            load();
        }
        return root;
    }

    protected abstract String getFxmlName();

    protected abstract void initialize();

    protected void preload(FXMLLoader loader) {
    }

    private void load() {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        preload(loader);
        String fxmlPath = format("fxml/%s", getFxmlName());
        try {
            root =loader.load(
                    Objects.requireNonNull(
                            this.getClass().getClassLoader().getResourceAsStream(fxmlPath)));
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
