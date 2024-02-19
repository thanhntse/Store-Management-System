/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchasing;

import java.util.Date;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class PurchaseReceipt implements Comparable<PurchaseReceipt> {

    private static int curNo = 0;
    private String prID;
    private Date date;

    //This constructor used for search by ID
    public PurchaseReceipt(String prID) {
        this.prID = prID;
    }

    //This constructor used for load from file
    public PurchaseReceipt(String prID, Date date) {
        this.prID = prID;
        this.date = date;
        if (curNo < MyTools.getNumberInCode(prID, "IM")) {
            curNo = MyTools.getNumberInCode(prID, "IM");
        }
    }

    public PurchaseReceipt(Date date) {
        this.prID = MyTools.generateCode("IM", 7, ++curNo);
        this.date = date;
    }

    @Override
    public String toString() {
        return prID + ", " + MyTools.toString(date, "dd-MM-yyyy");
    }

    @Override
    public boolean equals(Object obj) {
        PurchaseReceipt pr = (PurchaseReceipt) obj;
        return this.prID.equals(pr.prID);
    }

    public int compareTo(PurchaseReceipt pr) {
        return this.prID.compareTo(pr.prID);
    }

    public String getPrID() {
        return prID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
