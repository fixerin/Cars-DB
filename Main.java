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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

        HBox szukajHbox = new HBox(20);

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

                warunek = warunekWhere(marka, model, cenaOd, cenaDo);

                fillTable(carsTable, warunek);
            }
        });


        Scene scene = new Scene(vbox, 1000, 500);
        return scene;
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

    public String warunekWhere(String marka, String model, String cenaOd, String cenaDo){

        String warunek = "";  // budowanie zapytania warunku sql

        if (!(marka == null || marka.trim().isEmpty())){
            if (!warunek.isEmpty()) warunek = warunek + " AND ";

            warunek = warunek + "marka like '" + "%" + marka + "%" + "'";

        }

        if(!(model == null || model.trim().isEmpty())){
            if (!warunek.isEmpty()) warunek = warunek + " AND ";

            warunek = warunek + "model like '" + "%" + model + "%" + "'";
        }





        return warunek;
    }


}