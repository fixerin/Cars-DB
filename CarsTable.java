package sample;

import java.sql.Date;

public class CarsTable {
    private int id;
    private String marka;
    private String model;
    private String opis;
    private int przebieg;
    private int rocznik;
    private int cena;

    //Gettery

    public int getId() {
        return id;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getOpis() {
        return opis;
    }

    public int getPrzebieg() {
        return przebieg;
    }

    public int getRocznik() {
        return rocznik;
    }

    public int getCena() { return cena; }

    //Settery


    public void setId(int id) {
        this.id = id;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setPrzebieg(int przebieg) {
        this.przebieg = przebieg;
    }

    public void setRocznik(int rocznik) {
        this.rocznik = rocznik;
    }

    public void setCena(int cena) { this.cena = cena; }
}
