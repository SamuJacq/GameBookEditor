package org.helmo.gbeditor.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.helmo.gbeditor.presenters.IMainView;
import org.helmo.gbeditor.presenters.Presenters;

public class MainView implements IMainView {

    private Presenters presenters;

    private LoginView login;
    private HomeView home;
    private EditBookView editBook;

    private Scene scene;

    private Label message = new Label("");

    public MainView(LoginView login, HomeView home, EditBookView editBook){
        this.login = login;
        this.home = home;
        this.editBook = editBook;
    }

    @Override
    public void setPresenter(Presenters presenters) {
        this.presenters = presenters;
        login.setPresenters(presenters.getLoginP());
        home.setPresenters(presenters.getHomeP());
        editBook.setPresenters(presenters.getEditBookP());
    }

    @Override
    public void alert(String s) {
        message.setText(s);
    }

    @Override
    public void success(String s) {
        message.setText(s);
    }

    @Override
    public void goTo(String newPane) {
        if (presenters.startScene()) {
            if (newPane.equals("Login")) {
                login.goTo("");
                scene = new Scene(login.switchLogin(), 900, 700);
            } else if (newPane.equals("Home")) {
                home.goTo("");
                scene.setRoot(home.switchHome());
            } else {
                editBook.goTo("");
                scene.setRoot(editBook.switchEditBook());
            }
        } else {
            scene = new Scene(erreurConnection, 700, 300);
        }

    }

    private StackPane erreurConnection = new StackPane();{
        Label message = new Label("nous avons pas su vous connecter à la base de donnée du logiciel,\n" +
                                        "pour régler le problème, vérifier si vous êtes bien connecter au réseau d'Helmo,\n" +
                                        "vérifier aussi si vous avez activé le VPN pour se connecter au réseau d'Helmo");
        message.getStyleClass().add("textError");
        erreurConnection.getChildren().add(message);
    }

    public Scene getScene(){
        return scene;
    }


}
