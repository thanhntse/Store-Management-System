/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import java.util.Date;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class BillOfSale {

    private static int curNo = 0;
    private String bsID;
    private Date bsDate;

    public BillOfSale(String bsID) {
        this.bsID = bsID;
    }

    public BillOfSale(Date bsDate) {
        this.bsDate = bsDate;
        this.bsID = MyTools.generateCode("BS", 6, ++curNo);
    }

    public BillOfSale(String bsID, Date bsDate) {
        this.bsID = bsID;
        this.bsDate = bsDate;
        if (curNo < MyTools.getNumberInCode(bsID, "BS")) {
            curNo = MyTools.getNumberInCode(bsID, "BS");
        }
    }

    @Override
    public boolean equals(Object obj) {
        BillOfSale bs = (BillOfSale) obj;
        return this.bsID.equals(bs.bsID);
    }

    @Override
    public String toString() {
        return bsID + ", " + MyTools.toString(bsDate, "dd-MM-yyyy");
    }

    public int compareTo(BillOfSale bs) {
        return this.bsID.compareTo(bs.bsID);
    }

    public String getBsID() {
        return bsID;
    }

    public Date getBsDate() {
        return bsDate;
    }

    public void setBsDate(Date bsDate) {
        this.bsDate = bsDate;
    }

}
