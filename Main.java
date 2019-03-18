package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Dialog;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.control.ChoiceDialog;

import java.awt.*;
//import java.awt.TextArea;
import java.awt.Dialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
    static DatabaseManager db;
    TableView<CarsTable> carsTable;
    static DaoCarsTable daoCarsTable;

    static DatabaseManager databaseManager;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        new CarsTable();
        db = new DatabaseManager();
        daoCarsTable = new DaoCarsTable();
        daoCarsTable.setConn(db.connect("pojazdy", "localhost", "root", "1234"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Fuel_DB");

        VBox vbox = new VBox(20); // elements set in vertical order
        Scene scene = scena(vbox, primaryStage);

        primaryStage.setScene(scene);

        fillTable(carsTable, "1=1"); // warunek - prawda (czyli brak)
        primaryStage.show();
    }

    public Scene scena (VBox vbox, Stage primaryStage){

        vbox.setPadding(new Insets(20));

        //Label helloLabel = new Label("Baza danych aut");
        //vbox.getChildren().add(helloLabel);

        HBox szukajHbox = new HBox(15);

        Button szukajButton = new Button("Szukaj auta");
        szukajButton.setPrefHeight(20);
        szukajButton.setDefaultButton(true);
        szukajButton.setPrefWidth(100);

        Label szukaMarka = new Label("Marka: ");
        Label szukajModel = new Label("Model: ");
        Label szukajRocznik = new Label ("Rocznik: ");
        Label cenaLb = new Label("Cena: ");

        //new ArrayList();
        List<String> arrayListMarki = daoCarsTable.readMarka();
        arrayListMarki.add("Wszystkie");
        ObservableList<String> marki = FXCollections.observableArrayList(arrayListMarki);

        List<String> arrayListModele = daoCarsTable.readModel();
        arrayListModele.add("Wszystkie");
        ObservableList<String> modele = FXCollections.observableArrayList(arrayListModele);

        List<String> arrayListRoczniki = daoCarsTable.readRocznik();
        arrayListRoczniki.add("Wszystkie");
        ObservableList<String> roczniki = FXCollections.observableArrayList(arrayListRoczniki);

        //String[] ceny = new String[10];

        ObservableList<String> cenyOpcje =
                FXCollections.observableArrayList("0 - 4999", "5000 - 9999",
                        "10000 - 14999", "15000 - 19999", "20000 - 29999", "30000 - 49999",
                        "50000 - 74999", "75000 - 99999", "100000 - 199999", "200000 - 1M", "Wszystkie");


        ComboBox szukajMarkaCB = new ComboBox(marki);
        ComboBox modeleCB = new ComboBox(modele);
        ComboBox rocznikCB = new ComboBox(roczniki);
        ComboBox cenaCB = new ComboBox(cenyOpcje);

        szukajHbox.getChildren().add(szukajButton);
        szukajHbox.getChildren().add(szukaMarka);
        szukajHbox.getChildren().add(szukajMarkaCB);
        szukajHbox.getChildren().add(szukajModel);
        szukajHbox.getChildren().add(modeleCB);
        szukajHbox.getChildren().add(szukajRocznik);
        szukajHbox.getChildren().add(rocznikCB);
        szukajHbox.getChildren().add(cenaLb);
        szukajHbox.getChildren().add(cenaCB);

        vbox.getChildren().add(szukajHbox);

        this.carsTable = new TableView<CarsTable>();
        this.carsTable.setEditable(true);

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(40);
        idCol.setMaxWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn markaCol = new TableColumn("marka");
        markaCol.setMinWidth(80);
        markaCol.setCellValueFactory(new PropertyValueFactory("marka"));

        TableColumn modelCol = new TableColumn("model");
        modelCol.setMaxWidth(80);
        modelCol.setCellValueFactory(new PropertyValueFactory("model"));

        TableColumn przebiegCol = new TableColumn("przebieg");
        przebiegCol.setMinWidth(80);
        przebiegCol.setCellValueFactory(new PropertyValueFactory("przebieg"));

        TableColumn rocznikCol = new TableColumn("rocznik");
        rocznikCol.setMinWidth(80);
        rocznikCol.setCellValueFactory(new PropertyValueFactory("rocznik"));

        TableColumn czyUzywanyCol = new TableColumn("czy_uzywany");
        czyUzywanyCol.setMinWidth(80);
        czyUzywanyCol.setCellValueFactory(new PropertyValueFactory("czy_uzywany"));

        TableColumn opisCol = new TableColumn("opis");
        opisCol.setMinWidth(400);
        opisCol.setCellValueFactory(new PropertyValueFactory("opis"));

        TableColumn cenaCol = new TableColumn("cena");
        cenaCol.setMinWidth(100);
        cenaCol.setCellValueFactory(new PropertyValueFactory("cena"));

        carsTable.getColumns().addAll(new TableColumn[]{idCol, markaCol, modelCol, przebiegCol, rocznikCol, opisCol, cenaCol});
        vbox.getChildren().add(carsTable);

        HBox buttonsHbox = new HBox(20);

        Button dodajButton = new Button("Dodaj");
        dodajButton.setPrefHeight(20);
        dodajButton.setPrefWidth(100);

        Button edytujButton = new Button("Edytuj");
        edytujButton.setPrefHeight(20);
        edytujButton.setPrefWidth(100);

        Button usunButton = new Button("Usuń");
        usunButton.setPrefHeight(20);
        usunButton.setPrefWidth(100);

        buttonsHbox.getChildren().add(dodajButton);
        buttonsHbox.getChildren().add(edytujButton);
        buttonsHbox.getChildren().add(usunButton);
        vbox.getChildren().add(buttonsHbox);

        szukajButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cleanTab(carsTable);
                String warunek = ""; // do budowy warunku

                String marka, model;
                String cenaOd, cenaDo, rocznik;

                if (szukajMarkaCB.getValue() != null && szukajMarkaCB.getValue() != "Wszystkie"){
                    marka = szukajMarkaCB.getValue().toString();
                }
                else {
                    marka = "";
                }
                if (modeleCB.getValue() != null && modeleCB.getValue() != "Wszystkie"){
                    model = modeleCB.getValue().toString();
                }
                else {
                    model = "";
                }

                if (rocznikCB.getValue() != null && rocznikCB.getValue() != "Wszystkie"){
                    rocznik = rocznikCB.getValue().toString();
                }
                else {
                    rocznik = "";
                }

                if (cenaCB.getValue() != null && cenaCB.getValue() != "Wszystkie") {
                    String x = cenaCB.getValue().toString();
                    switch (x) {
                        case "0 - 4999":
                            cenaOd = "0";
                            cenaDo = "4999";
                            break;
                        case "5000 - 9999":
                            cenaOd = "5000";
                            cenaDo = "9999";
                            break;
                        case "10000 - 14999":
                            cenaOd = "10000";
                            cenaDo = "14999";
                            break;
                        case "15000 - 19999":
                            cenaOd = "15000";
                            cenaDo = "19999";
                            break;
                        case "20000 - 29999":
                            cenaOd = "20000";
                            cenaDo = "29999";
                            break;
                        case "30000 - 49999":
                            cenaOd = "30000";
                            cenaDo = "49999";
                            break;
                        case "50000 - 74999":
                            cenaOd = "50000";
                            cenaDo = "74999";
                            break;
                        case "75000 - 99999":
                            cenaOd = "75000";
                            cenaDo = "99999";
                            break;
                        case "100000 - 199999":
                            cenaOd = "100000";
                            cenaDo = "199999";
                            break;
                        case "200000 - 1M":
                            cenaOd = "200000";
                            cenaDo = "1000000";
                            break;

                        default:
                            cenaOd = "";
                            cenaDo = "";
                    }
                }
                else {
                    cenaOd = "";
                    cenaDo = "";
                }

                //if (

                warunek = warunekWhere(marka, model, rocznik, cenaOd, cenaDo);

                fillTable(carsTable, warunek);
            }
        });

        dodajButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CarsTable newCar = new CarsTable();
                newCar = edytujAutoOkno(newCar, "Dodaj", "DODAJ AUTO");
                daoCarsTable.insertCar(newCar);
                cleanTab(carsTable);
                fillTable(carsTable, "1=1");

            }
        });

        edytujButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CarsTable car = carsTable.getSelectionModel().getSelectedItem();

                if (car != null){
                    car = edytujAutoOkno(car, "Edytuj", "EDYTUJ AUTO");
                    daoCarsTable.updateCar(car);

                    cleanTab(carsTable);
                    fillTable(carsTable, "1=1");

                }
            }
        });

        usunButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CarsTable car = carsTable.getSelectionModel().getSelectedItem();

                if (car != null){

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno usunąć?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        daoCarsTable.deleteCar(car);

                        cleanTab(carsTable);
                        fillTable(carsTable, "1=1");
                    }
                }
            }
        });


        Scene scene = new Scene(vbox, 1000, 500);
        return scene;
    }


    public CarsTable edytujAutoOkno(CarsTable car, String dialogTitle, String dialogHeader){


        javafx.scene.control.Dialog<CarsTable> dialog;
        dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.setHeaderText(dialogHeader);
        dialog.setResizable(true);
//-----------------------------------------------------------------------------------------DO DODANIA
        List<String> makeArrayList = new ArrayList<>();
        makeArrayList = daoCarsTable.readAllMake();
        ObservableList<String> marki = FXCollections.observableArrayList(makeArrayList);

        final ComboBox markiCB = new ComboBox(marki);
//------------------------------------------------------------------------------------------DO DODANIA
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(15);


        Label markaLb = new Label("Marka: ");
        TextField markaTF = new TextField();
        if (car.getMarka() != null ) markaTF.setText(car.getMarka()); // jeżeli jest puste - nie wypełniaj
        gridPane.add(markaLb, 0,0);
        gridPane.add(markaTF, 1,0);
        gridPane.setMargin(markaLb, new Insets(0, 0,0,15));
        gridPane.setMargin(markaTF, new Insets(10, 15, 0, 5));

        Label modelLb = new Label("Model: ");
        TextField modelTF = new TextField();
        if (car.getModel() != null) modelTF.setText(car.getModel());
        gridPane.add(modelLb,0,1);
        gridPane.add(modelTF, 1, 1);
        gridPane.setMargin(modelLb, new Insets(0,0,0,15));
        gridPane.setMargin(modelTF, new Insets(5, 15, 0, 5));

        Label przebiegLb = new Label("Przebieg: ");
        TextField przebiegTF = new TextField();
        if (car.getPrzebieg() != 0) przebiegTF.setText(Integer.toString(car.getPrzebieg())); // jeżeli równe 0 - nie wypełniaj
        gridPane.add(przebiegLb,0,2);
        gridPane.add(przebiegTF, 1,2);
        gridPane.setMargin(przebiegLb, new Insets(0,0,0,15));
        gridPane.setMargin(przebiegTF, new Insets(5, 15, 0, 5));

        Label rocznikLb = new Label("Rocznik: ");
        TextField rocznikTF = new TextField();
        if (car.getRocznik() != 0) rocznikTF.setText(Integer.toString(car.getRocznik()));
        gridPane.add(rocznikLb,0,3);
        gridPane.add(rocznikTF, 1, 3);
        gridPane.setMargin(rocznikLb, new Insets(0,0,0,15));
        gridPane.setMargin(rocznikTF, new Insets(5, 15, 0, 5));

        Label opisLb = new Label("Opis: ");
        TextArea opisTA = new TextArea();
        if (car.getOpis() != null) opisTA.setText(car.getOpis());
        gridPane.add(opisLb,0,4);
        gridPane.add(opisTA, 1, 4);
        gridPane.setMargin(opisLb, new Insets(0,0,0,15));
        gridPane.setMargin(opisTA, new Insets(5, 15, 0, 5));

        Label cenaLb = new Label("Cena: ");
        TextField cenaTF = new TextField();
        if (car.getCena() != 0) cenaTF.setText(Integer.toString(car.getCena()));
        gridPane.add(cenaLb,0,5);
        gridPane.add(cenaTF, 1, 5);
        gridPane.setMargin(cenaLb, new Insets(0,0,0,15));
        gridPane.setMargin(cenaTF, new Insets(5, 15, 18, 5));

        dialog.getDialogPane().setContent(gridPane);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        Button btOk = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, CarsTable>() {
            @Override
            public CarsTable call(ButtonType param) {
                if (param == buttonTypeOk){

                    car.setMarka(markaTF.getText());
                    car.setModel(modelTF.getText());
                    car.setOpis(opisTA.getText());
                    car.setPrzebieg(Integer.parseInt(przebiegTF.getText()));
                    car.setRocznik(Integer.parseInt(rocznikTF.getText()));
                    car.setCena(Integer.parseInt(cenaTF.getText()));

                    return car;
                }else {
                    return null;
                }
            }
        });

        ButtonType buttonTypeExit = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeExit);

        Optional<CarsTable> result = dialog.showAndWait();

        if(result.isPresent()){
            return (result.get());
        } else {
            return null;
        }

        //return newCar;
    }

    //metoda do wypełnienia widoku tabeli
    public void fillTable(TableView tableV, String warunek){
        List<CarsTable> carsList; // zadeklarowanie listy aut

        // wpisanie rekordow do listy (z db)
        carsList = daoCarsTable.readAll(warunek);

        // stwarzenie widoku tabeli na podstawie listy
        for (CarsTable t : carsList){
            tableV.getItems().add(t);
        }
    }


    public void cleanTab(TableView tableView){
        tableView.getItems().clear();
    }

    public String warunekWhere(String marka, String model, String rocznik, String cenaOd, String cenaDo){

        String warunek = "";  // budowanie zapytania warunku sql

        if (!(marka == null || marka.trim().isEmpty())){
            if (!warunek.isEmpty()) warunek = warunek + " AND ";

            warunek = warunek + "marka like '" + "%" + marka + "%" + "'";

        }

        if(!(model == null || model.trim().isEmpty())){
            if (!warunek.isEmpty()) warunek = warunek + " AND ";

            warunek = warunek + "model like '" + "%" + model + "%" + "'";
        }

        if (!(rocznik == null || rocznik.trim().isEmpty())){
            if (!warunek.isEmpty()) warunek = warunek + " AND ";

            warunek = warunek + "rocznik like " + rocznik;
        }

        if(!(cenaOd == null || cenaOd.trim().isEmpty())){
            if (!warunek.isEmpty()) warunek = warunek + " AND ";

            warunek = warunek + "cena BETWEEN " + cenaOd + " AND " + cenaDo;
        }



        return warunek;
    }


}