package com.example.skshubhamiitkmr.bloodbankproject;

/**
 * Created by skshubhamiitkmr on 04-04-2018.
 */

public class Inventory {


    private String group;
    private String stock;
    private String donor;

    public Inventory(String group, String stock, String donor) {
        this.group = group;
        this.stock = stock;
        this.donor = donor;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }
}
