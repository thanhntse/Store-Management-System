/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.util.Date;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class Product implements Comparable<Product> {

    private static int curNo = 0;

    private String pID;
    private String name;
    private double purchasePrice;
    private int initialQuantity;
    private int curQuantity;
    private Date productionDate;
    private Date expirationDate;
    //Product receipt id
    private String prID;

    //This constructor used for search by ID
    public Product(String pID) {
        this.pID = pID;
    }

    //This constructor used for load from file
    public Product(String prID, String pID, String name, double purchasePrice, int initialQuantity, int curQuantity, Date productionDate, Date expirationDate) {
        this.prID = prID;
        this.pID = pID;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.initialQuantity = initialQuantity;
        this.curQuantity = curQuantity;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        if (curNo < MyTools.getNumberInCode(pID, "P")) {
            curNo = MyTools.getNumberInCode(pID, "P");
        }
    }

    //This constructor used to import new product
    public Product(String prID, String name, double purchasePrice, int initialQuantity, int curQuantity, Date productionDate, Date expirationDate) {
        this.pID = MyTools.generateCode("P", 6, ++curNo);
        this.prID = prID;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.initialQuantity = initialQuantity;
        this.curQuantity = curQuantity;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return prID + ", " + pID + ", " + name + ", " + purchasePrice + ", " + initialQuantity + ", " + curQuantity + ", " + MyTools.toString(productionDate, "dd-MM-yyyy") + ", " + MyTools.toString(expirationDate, "dd-MM-yyyy");
    }

    @Override
    public boolean equals(Object obj) {
        Product p = (Product) obj;
        return this.pID.equals(p.pID);
    }

    public int compareTo(Product p) {
        return this.pID.compareTo(p.pID);
    }

    public String getpID() {
        return pID;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public int getCurQuantity() {
        return curQuantity;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public void setCurQuantity(int curQuantity) {
        this.curQuantity = curQuantity;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPrID() {
        return prID;
    }

}
