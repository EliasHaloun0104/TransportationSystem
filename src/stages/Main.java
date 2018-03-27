package stages;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StageManager.getInstance().showStage_1();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
