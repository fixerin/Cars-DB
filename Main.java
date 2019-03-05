package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Fuel_DB");

        VBox vbox = new VBox(20); // elements set in vertical order
        Scene scene = scena(vbox, primaryStage);

        primaryStage.setScene(scene);

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


        final TextField szukajMarkaText = new TextField();
        final TextField szukajModelText = new TextField();
        final TextField szukajRocznikText = new TextField();
        CheckBox czyUzywany = new CheckBox("Nowy");

        //new ArrayList();
        List<String> arrayListMarki = daoCarsTable.readMarka();
        arrayListMarki.add("Wszystkie");
        ObservableList<String> marki = FXCollections.observableArrayList(arrayListMarki);

        ComboBox szukajMarkaCB = new ComboBox(marki);

        szukajHbox.getChildren().add(szukajButton);
        szukajHbox.getChildren().add(szukaMarka);
        szukajHbox.getChildren().add(szukajMarkaCB);
        //szukajHbox.getChildren().add(szukajMarkaText);
        szukajHbox.getChildren().add(szukajModel);
        szukajHbox.getChildren().add(szukajModelText);
        szukajHbox.getChildren().add(szukajRocznik);
        szukajHbox.getChildren().add(szukajRocznikText);
        szukajHbox.getChildren().add(czyUzywany);

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
        opisCol.setMinWidth(300);
        opisCol.setCellValueFactory(new PropertyValueFactory("opis"));

        carsTable.getColumns().addAll(new TableColumn[]{idCol, markaCol, modelCol, przebiegCol, rocznikCol, opisCol});
        vbox.getChildren().add(carsTable);





        Scene scene = new Scene(vbox, 700, 500);
        return scene;
    }



}