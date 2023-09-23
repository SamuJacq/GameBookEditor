package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.*;

/**
 *vue de la création d'un chapitre
 */
public class EditBookView extends BorderPane implements EditBookInterface {

    private EditBookPresenter presenters;
    private BookViewModel model;

    public void setPresenters(EditBookPresenter presenters){
        presenters.setView(this);
        this.presenters = presenters;
    }

    private Label messageCreate = new Label("");
    private Label messagePage = new Label("");
    private Label messageChoice = new Label("");
    private Label profil = new Label("");

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

    private TextArea pageInput = new TextArea();{
        pageInput.setPrefRowCount(2);
        pageInput.setPrefColumnCount(15);
    }

    private TextArea choiceInput = new TextArea();{
        pageInput.setPrefRowCount(2);
        pageInput.setPrefColumnCount(20);
    }

    private Button leaveEdition = new Button("quitter");{
        leaveEdition.setOnAction( action -> presenters.switchHome());
    }

    private Button updateBook = new Button("maj Book");{
        updateBook.setOnAction( action -> presenters.updateBook(
                titreInput.getText(), isbnInput.getText(), resumeInput.getText(),model.getIsbn()));
    }

    private Button addPage = new Button("ajouter page");{
        addPage.setOnAction(action -> newPage());
    }

    private Button deletePage = new Button("supprimer page");{
        deletePage.setOnAction(action -> removePage());
    }

    private Button upPage = new Button("monter page");{
        upPage.setOnAction(action -> movePage(true));
    }

    private Button downPage = new Button("descendre page");{
        downPage.setOnAction(action -> movePage(false));
    }

    private Button confirmer = new Button("confirmer");{
        confirmer.setOnAction(action -> removePage());
    }

    private Button annuler = new Button("annuler");{
        annuler.setOnAction(action -> showBottom(false));
    }

    private Button editChoice = new Button("édition choix");{
        editChoice.setOnAction(action -> lookAddChoice());
    }

    private Button associerChoice = new Button("associer choix");{
        associerChoice.setOnAction(action -> addChoice());
    }

    private Button deleteChoice = new Button("supprimer le choix");{
        deleteChoice.setOnAction(action -> removeChoice());
    }

    private Button saveEdit = new Button("enregistrer");{
        saveEdit.setOnAction(action -> presenters.saveEdit());
    }

    private Button publier = new Button("publier");{
        publier.setOnAction(action -> presenters.publierBook());
    }

    private Spinner<Integer> numPageAssocier = new Spinner<Integer>();

    private ObservableList<String> oListePage = FXCollections.observableArrayList();

    private ListView<String> vueEnListePage = new ListView<>();

    private ObservableList<String> oListeChoice = FXCollections.observableArrayList();

    private ListView<String> vueEnListeChoice = new ListView<>();

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
        updateBook.getStyleClass().add("button");
        bodyCreate.getChildren().add(messageCreate);
        bodyCreate.getStyleClass().add("body");
        bodyCreate.getStyleClass().add("width");

        bodyCreate.getChildren().add(updateBook);
    }
    private GridPane headerEdition = new GridPane();{
        Label titleEdition = new Label("Edition :");
        headerEdition.add(titleEdition,0,0);
        headerEdition.getStyleClass().add("title");
        headerEdition.add(profil,1,0);
        headerEdition.add(leaveEdition,2,0);
    }

    private GridPane bodyEditionPage = new GridPane();{
        Label titlePage = new Label("Edition Page");
        Label labelPage = new Label("contenu de la page");
        bodyEditionPage.add(titlePage,0,0);
        bodyEditionPage.add(vueEnListePage,0,2);
        bodyEditionPage.add(labelPage, 1,1);
        bodyEditionPage.add(pageInput,1,2);
        bodyEditionPage.add(addPage,0,3);
        bodyEditionPage.add(upPage,0,4);
        bodyEditionPage.add(downPage,0,5);
        bodyEditionPage.add(editChoice,1,3);
        bodyEditionPage.add(deletePage,1,5);
        bodyEditionPage.add(messagePage,0,6);
        bodyEditionPage.add(confirmer,0,7);
        bodyEditionPage.add(annuler,1,7);
        confirmer.setVisible(false);
        annuler.setVisible(false);
        bodyEditionPage.add(publier,0,9);
        bodyEditionPage.add(saveEdit,1,9);
        bodyEditionPage.getStyleClass().add("formulaire");
    }

    private VBox leftBottomEdition = new VBox();{
        leftBottomEdition.getChildren().add(vueEnListeChoice);
        leftBottomEdition.getStyleClass().add("widthBottom");
    }
    private VBox centerBottomEdition = new VBox();{
        Label numPageLabel = new Label("numero de la page");
        Label titleChoice = new Label("titre du choix");
        centerBottomEdition.getChildren().add(numPageLabel);
        centerBottomEdition.getChildren().add(numPageAssocier);
        centerBottomEdition.getChildren().add(titleChoice);
        centerBottomEdition.getChildren().add(choiceInput);
        centerBottomEdition.getStyleClass().add("widthBottom");
    }
    private VBox rightBottomEdition = new VBox();{
        rightBottomEdition.getChildren().add(associerChoice);
        rightBottomEdition.getChildren().add(deleteChoice);
        rightBottomEdition.getChildren().add(messageChoice);
        rightBottomEdition.getStyleClass().add("widthBottom");
    }

    private BorderPane bottomEdition = new BorderPane();{
        bottomEdition.setLeft(leftBottomEdition);
        bottomEdition.setCenter(centerBottomEdition);
        bottomEdition.setRight(rightBottomEdition);
        bottomEdition.getStyleClass().add("height");
        bottomEdition.setVisible(false);
    }

    private void setVueEnListePage(){
        int index = 1;
        vueEnListePage.getItems().clear();
        for(var page : presenters.getPages()){
            oListePage.add("num : " + index + " = "+ page.getContains());
            ++index;
        }
        vueEnListePage.setItems(oListePage);
    }

    private void lookAddChoice(){
        MultipleSelectionModel<String> listViewModel = vueEnListePage.getSelectionModel();
        if(listViewModel.isEmpty()){
            messagePage.setText("veuillez sélectioner une choix");
            bottomEdition.setVisible(false);
        }else{
            messagePage.setText("");
            bottomEdition.setVisible(true);
            presenters.setCurrentPage(listViewModel.getSelectedIndex());
            initSpinner(numPageAssocier, vueEnListePage.getItems().size());
            setVueEnListeChoice();
        }
    }

    private void setVueEnListeChoice(){
        vueEnListeChoice.getItems().clear();
        for(var choice : presenters.getChoices()){
            oListeChoice.add(choice.getSummary());
        }
        vueEnListeChoice.setItems(oListeChoice);
    }

    private void initSpinner(Spinner<Integer> spin, int borneSup){
        spin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, borneSup));
        spin.getValueFactory().setValue(1);
    }

    private void addChoice(){
        presenters.addChoice(numPageAssocier.getValue(), choiceInput.getText());
        setVueEnListeChoice();
    }

    private void removeChoice(){
        MultipleSelectionModel<String> listViewModel = vueEnListeChoice.getSelectionModel();
        if(listViewModel.isEmpty()){
            presenters.removeChoice(-1);
        }else{
            presenters.removeChoice(listViewModel.getSelectedIndex());
        }
        setVueEnListeChoice();
    }

    private void newPage(){
        presenters.checkPage(pageInput.getText());
        hideChoiceView();
        setVueEnListePage();
    }

    private void movePage(boolean choice){
        MultipleSelectionModel<String> listViewModel = vueEnListePage.getSelectionModel();
        hideChoiceView();
        if(listViewModel.isEmpty()){
            messagePage.setText("veuillez sélectioner une page");
        }else{
            messagePage.setText("");
            presenters.movePage(choice, listViewModel.getSelectedIndex());
            setVueEnListePage();
        }

    }

    private void removePage(){
        MultipleSelectionModel<String> listViewModel = vueEnListePage.getSelectionModel();
        hideChoiceView();
        messagePage.setText("");
        if(listViewModel.isEmpty()){
            presenters.removePage(-1);
        }else {
            if (confirmer.isVisible()) {
                presenters.removePage(listViewModel.getSelectedIndex());
                setVueEnListePage();
                showBottom(false);
                messagePage.setText("");
            } else {
                if(presenters.haveChoice(listViewModel.getSelectedIndex())){
                    messagePage.setText("cette page est référencé par des choix");
                }
                showBottom(true);
                deletePage.setVisible(false);
            }
        }
    }

    private void hideChoiceView(){
        bottomEdition.setVisible(false);
    }

    private void showBottom(boolean show){
        confirmer.setVisible(show);
        annuler.setVisible(show);
        messagePage.setText("");
        deletePage.setVisible(true);
    }

    public BorderPane switchEditBook(){
        return this;
    }

    @Override
    public void setPresenter(Presenters p) {

    }

    @Override
    public void alert(String s) {
        if(s.contains("page")){
            messageChoice.setText(s);
        }else if(s.contains("choix")){
            messagePage.setText(s);
        }else{
            messageCreate.setText(s);
        }
    }

    @Override
    public void success(String s) {
        if(s.contains("page")){
            messagePage.setText(s);
        }else if(s.contains("choix")){
            messageChoice.setText(s);
        }else{
            messageCreate.setText(s);
        }
    }

    @Override
    public void goTo(String newPane) {
        messagePage.setText("");

        setVueEnListePage();
        profil.setText(presenters.getAuthor());

        model = presenters.getBook();

        titreInput.setText(model.getTitle());
        isbnInput.setText(model.getIsbn());
        resumeInput.setText(model.getResume());

        this.setTop(headerEdition);
        this.setLeft(bodyEditionPage);
        this.setRight(bodyCreate);
        this.setBottom(bottomEdition);
    }

}
