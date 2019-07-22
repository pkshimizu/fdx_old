package net.noncore.fdx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.noncore.fdx.presentation.views.MainView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FdxApplication extends Application {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(FdxApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainView mainView = context.getBean(MainView.class);
        primaryStage.setScene(new Scene(mainView.getRoot()));
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.stop();
    }
}
