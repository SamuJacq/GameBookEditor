package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.*;

/**
 * vue du menu principal
 */
public class HomeView extends BorderPane implements HomeInterface {

    private HomePresenter presenters;

    private BookViewModel model;

    public void setPresenters(HomePresenter presenters){
        presenters.setView(this);
        this.presenters = presenters;
    }

    private Label messageHome = new Label("");
    private Label messageBottomHome = new Label("");
    private Label messageCreate = new Label("");
    private Label profil = new Label("");


    private ObservableList<String> oListeBook = FXCollections.observableArrayList();

    private ListView<String> vueEnListe = new ListView<>();

    private ObservableList<String> oListeBookInfo = FXCollections.observableArrayList();

    private ListView<String> vueBookInfo = new ListView<>();

    private BorderPane viewHome = new BorderPane();{
        viewHome.getStyleClass().add("width");
    }

    private TextArea titreInput = new TextArea();{
        titreInput.setPrefRowCount(5);
        titreInput.setPrefColumnCount(20);
    }
    private TextField isbnInput = new TextField();{
        isbnInput.setPrefColumnCount(20);
    }
    private TextArea resumeInput = new TextArea();{
        resumeInput.setPrefRowCount(5);
        resumeInput.setPrefColumnCount(20);
    }

    private Button buttonInfo = new Button("info");{
        buttonInfo.setOnAction(action -> showBook());
    }

    private Button create = new Button("créer livre");{
        create.setOnAction( action -> actionCreate());
    }

    private Button editionBook = new Button("edition du livre");{
        editionBook.setOnAction( action -> presenters.switchEditBook());
    }

    private HBox headerApp = new HBox(); {
        Label title = new Label("GBEditor");
        title.getStyleClass().add("title");
        headerApp.getChildren().add(title);
        headerApp.getChildren().add(profil);
        headerApp.getStyleClass().add("header");
    }

    private VBox bodyHome = new VBox();{
        bodyHome.getStyleClass().add("width");
        bodyHome.getStyleClass().add("formulaire");
        bodyHome.getChildren().add(messageHome);
        bodyHome.getChildren().add(buttonInfo);
        bodyHome.getChildren().add(vueEnListe);
    }

    private VBox bodyCreate = new VBox();{
        bodyCreate.getStyleClass().add("formulaire");
        Label titleBook = new Label("titre : 150 caractere max :");
        Label isbn = new Label("ISBN format : 2200017## : \n# = nombre au choix");
        Label resume = new Label("Resume : 500 caractere max :");
        bodyCreate.getChildren().add(titleBook);
        titreInput.getStyleClass().add("textArea");
        bodyCreate.getChildren().add(titreInput);
        bodyCreate.getChildren().add(isbn);
        isbnInput.getStyleClass().add("input");
        bodyCreate.getChildren().add(isbnInput);
        bodyCreate.getChildren().add(resume);
        resumeInput.getStyleClass().add("textArea");
        bodyCreate.getChildren().add(resumeInput);
        create.getStyleClass().add("button");
        bodyCreate.getChildren().add(messageCreate);
        bodyCreate.getStyleClass().add("body");
        bodyCreate.getStyleClass().add("width");

        bodyCreate.getChildren().add(create);
    }

    private HBox bottomHome = new HBox();{
        bottomHome.getChildren().add(vueBookInfo);
        bottomHome.getChildren().add(editionBook);
        bottomHome.getChildren().add(messageBottomHome);
        bottomHome.getStyleClass().add("body");
        bottomHome.getStyleClass().add("height");
        bottomHome.setVisible(false);
    }

    private void resetInputCreate(){
        titreInput.setText("");
        isbnInput.setText("");
        resumeInput.setText("");
    }

    private void actionCreate(){
        presenters.checkCreateBook(titreInput.getText(), isbnInput.getText(), resumeInput.getText());
        loadBook();
    }

    private void loadBook(){
        vueEnListe.getItems().clear();
        if(presenters.loadBook().isEmpty()){
            messageHome.setText("vous n'avez pas de livre");
        }else{
            for(var book : presenters.loadBook()){
                oListeBook.add(book.getTitle()  + " " + (book.isPublier() ? "(publié)" : ""));
            }
        }
        vueEnListe.setItems(oListeBook);
    }

    public void showBook() {
        MultipleSelectionModel<String> listViewModel = vueEnListe.getSelectionModel();
        if (listViewModel.isEmpty()) {
            messageHome.setText("veuillez sélectioner un livre");
            bottomHome.setVisible(false);
        } else {
            bottomHome.setVisible(true);
            vueBookInfo.getItems().clear();
                model = presenters.getBookSelected(listViewModel.getSelectedIndex());
                oListeBookInfo.add(model.getTitle());
                oListeBookInfo.add(model.getIsbn());
                oListeBookInfo.add(model.getAuthor());
                listViewModel.clearSelection();
                vueBookInfo.setItems(oListeBookInfo);

                if (model.isPublier()) {
                    editionBook.setVisible(false);
                    messageBottomHome.setText("ce livre est publié, vous ne pouvez plus le modifier");
                } else {
                    editionBook.setVisible(true);
                    messageBottomHome.setText("pour sauver en BD les modif des pages et choix, cliquer sur le boutons enregistrer");
                }
        }
    }

    public BorderPane switchHome(){
        return this;
    }

    @Override
    public void setPresenter(Presenters p) {

    }

    @Override
    public void alert(String s) {
        messageCreate.setText(s);
    }

    @Override
    public void success(String s) {
        messageCreate.setText(s);
        resetInputCreate();
    }

    @Override
    public void goTo(String newPane) {
        resetInputCreate();
        profil.setText(presenters.getAuthor());
        loadBook();
        viewHome.setLeft(bodyHome);
        viewHome.getStyleClass().add("formulaire");

        create.setVisible(true);

        this.setTop(headerApp);
        this.setLeft(viewHome);
        this.setRight(bodyCreate);
        this.setBottom(bottomHome);
        bottomHome.setVisible(false);
        this.setCenter(null);
    }
}
