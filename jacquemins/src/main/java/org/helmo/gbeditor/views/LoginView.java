package org.helmo.gbeditor.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.LoginInterface;
import org.helmo.gbeditor.presenters.LoginPresenter;
import org.helmo.gbeditor.presenters.Presenters;

/**
 * vue de la connection
 */
public class LoginView extends BorderPane implements LoginInterface {

    private LoginPresenter presenters;

    public void setPresenters(LoginPresenter presenters){
        presenters.setView(this);
        this.presenters = presenters;

    }

    private TextField nameInput = new TextField();
    private TextField firstnameInput = new TextField();

    private Label messageLogin = new Label("");

    private Button bouton = new Button("connexion");{
        bouton.setOnAction( action -> presenters.checkLogin(nameInput.getText(),firstnameInput.getText()));
    }

    private VBox headerLogin = new VBox(); {
        Label title = new Label("Veuillez-sous authentifier");
        title.getStyleClass().add("title");
        headerLogin.getChildren().add(title);
        headerLogin.getStyleClass().add("header");
    }

    private GridPane bodyLogin = new GridPane();{
        bodyLogin.getStyleClass().add("formulaire");
        bodyLogin.getStyleClass().add("login");
        Label input1 = new Label("votre nom :");
        Label input2 = new Label("votre prenom :");
        bodyLogin.add(input1, 0, 0);
        nameInput.getStyleClass().add("input");
        bodyLogin.add(nameInput, 1, 0);
        bodyLogin.add(input2, 0, 1);
        firstnameInput.getStyleClass().add("input");
        bodyLogin.add(firstnameInput, 1, 1);
        bouton.getStyleClass().add("button");
        bodyLogin.add(bouton, 0, 2);
        bodyLogin.add(messageLogin, 0, 3);
        bodyLogin.getStyleClass().add("body");
    }

    public BorderPane switchLogin(){
        return this;
    }

    @Override
    public void setPresenter(Presenters p) {}

    @Override
    public void alert(String s) {
        messageLogin.setText(s);
    }

    @Override
    public void success(String s) {
        messageLogin.setText(s);
    }

    @Override
    public void goTo(String newPane) {
        this.setTop(headerLogin);
        this.setCenter(bodyLogin);
    }

}
