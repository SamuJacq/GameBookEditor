/**
 * je n'ai pas de dispense
 */
package org.helmo.gbeditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.helmo.gbeditor.infrastructures.DataStorageFactory;
import org.helmo.gbeditor.presenters.Presenters;
import org.helmo.gbeditor.views.EditBookView;
import org.helmo.gbeditor.views.HomeView;
import org.helmo.gbeditor.views.LoginView;
import org.helmo.gbeditor.views.MainView;
/**
 * class qui va initialiser et lancé l'application
 */
public class App extends Application {
    @Override
    public void start (Stage primaryStage) {
        MainView mainView = new MainView(new LoginView(), new HomeView(), new EditBookView());
        DataStorageFactory storage = DataStorageFactory.createFactory();

        Presenters p = new Presenters(mainView, storage);
        Scene scene = mainView.getScene();

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Éditeur de livre");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * méthode main qui lance le programme
     */
    public static void main (String[] args) {
        launch(args);
    }
}
