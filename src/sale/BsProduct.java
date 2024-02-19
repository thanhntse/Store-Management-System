/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class BsProduct {

    private String bsID;
    private String pID;
    private double bsPrice;
    private int quantity;

    public BsProduct(String bsID, String pID, double bsPrice, int quantity) {
        this.bsID = bsID;
        this.pID = pID;
        this.bsPrice = bsPrice;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        BsProduct bs = (BsProduct) obj;
        return this.bsID.equals(bs.bsID) && this.pID.equals(bs.pID);
    }

    @Override
    public String toString() {
        return bsID + ", " + pID + ", " + bsPrice + ", " + quantity;
    }

    public String getBsID() {
        return bsID;
    }

    public String getpID() {
        return pID;
    }

    public double getBsPrice() {
        return bsPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setBsPrice(double bsPrice) {
        this.bsPrice = bsPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
