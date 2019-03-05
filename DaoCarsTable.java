package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoCarsTable {
    private Connection conn;
    private Statement stmt;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void setConn(Connection conn) {this.conn = conn;}



    //Metoda ma sluzyc do wyswietlenia wszystkich rekordow lub wybranych,
    //gdy podany jest warunek

    List<CarsTable> readAll (String s){ // String s to możliwy warunek
        List<CarsTable> result = new ArrayList<>();
        String sql;
        if (!s.isEmpty()){
            sql = "SELECT * FROM auta";
        } else {
            sql = "SELECT * FROM auta WHERE " + s;
        }

        try{
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                CarsTable carsTable = new CarsTable();
                carsTable.setId(rs.getInt("id"));
                carsTable.setMarka(rs.getString("marka"));
                carsTable.setModel(rs.getString("model"));
                carsTable.setPrzebieg(rs.getInt("przebieg"));
                carsTable.setRocznik(rs.getInt("rocznik"));
                carsTable.setCzy_uzywany(rs.getBoolean("czy_uzywany"));
                carsTable.setOpis(rs.getString("opis"));
                result.add(carsTable);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoCarsTable.class.getName()).log(Level.SEVERE, (String)null, e);
        }

        return result;
    }

    List<String> readMarka(){
        ArrayList<String> result = new ArrayList<String>();
        String sql;

        sql = "SELECT marka FROM auta";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                result.add(rs.getString("marka"));
            }

        } catch (SQLException e){
            Logger.getLogger(DaoCarsTable.class.getName()).log(Level.SEVERE, (String)null, e);
        }
        return result;
    }




    void insertCars(CarsTable carsTable){
        String sql;

        //zapytanie do serwera o dodanie rekordu
        sql = "INSERT INTO auta (marka, model, przebieg, rocznik, czy_uzywany, opis " +
                "VALUES ('"+carsTable.getMarka()+"', '"+carsTable.getModel()+"', '"+carsTable.getPrzebieg()+"', '" +
                carsTable.getRocznik()+"', '"+carsTable.isCzy_uzywany()+"', '"+carsTable.getOpis()+"')";


        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);

        } catch (SQLException e){
            Logger.getLogger(DaoCarsTable.class.getName()).log(Level.SEVERE, (String)null, e);
        }
    }


}