package gui;

import controller.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.statements.IStmt;

public class MainExecutionWindow {
    private static Controller controller;

    public static void launch(IStmt program) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainExecutionWindow.class.getResource("/gui/View.fxml"));
            AnchorPane root = loader.load();

            GUIController controller = loader.getController();
            controller.initializeExecution(program);

            Scene scene = new Scene(root);
            stage.setTitle("Program Execution");
            stage.setScene(scene);

            stage.setOnCloseRequest((WindowEvent event) -> Platform.exit());

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
