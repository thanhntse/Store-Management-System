/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import store.Product;
import store.ProductList;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class BsProductList extends ArrayList<BsProduct> {

    private ProductList pdList;
    private ArrayList<BillOfSale> bList;

    public BsProductList() throws IOException {
        pdList = new ProductList();
        //load file
        pdList.loadFromFile("data/imports.txt", "data/products.txt");
        bList = new ArrayList<>();
    }

    public void exportProducts() {
        //Create Bill Of Sale
        System.out.println("Create Bill of Sale:");
        Date today = new Date();
        Date date = MyTools.readDateBefore("Date(dd-MM-yyyy)", "dd-MM-yyyy", today);
        BillOfSale bs = new BillOfSale(date);
        //add to bill of sale list
        bList.add(bs);
        int numberOfProducts = Integer.parseInt(MyTools.readStr("Number of products to be sold", "([1-9][\\d]*)|([0]+[\\d]+)"));
        while (numberOfProducts-- != 0) {
            System.out.println("\nProduct Sold detail:");
            int pos;
            String pID;
            do {
                pID = MyTools.readStr("ID for sold", ".*").toUpperCase();
                pos = pdList.indexOf(new Product(pID));
                if (pos < 0) {
                    System.out.println("Not found!");
                }
            } while (pos < 0);
            //get product to be sold
            Product pSold = pdList.get(pos);

            double price;
            do {
                System.out.println("Purchasing price: " + pSold.getPurchasePrice());
                price = Double.parseDouble(MyTools.readStr("Selling price", "[\\d]+[.]?[\\d]*"));
                if (price < pSold.getPurchasePrice()) {
                    System.out.println("Selling price must greater than or equal Purchasing price!");
                }
            } while (price < pSold.getPurchasePrice());

            int quantity;
            do {
                System.out.println("Current quantity: " + pSold.getCurQuantity());
                quantity = Integer.parseInt(MyTools.readStr("Selling quantity", "([1-9][\\d]*)|([0]+[\\d]+)"));
                if (quantity > pSold.getCurQuantity()) {
                    System.out.println("Selling quantity must less than or equal current quantity!");
                }
            } while (quantity > pSold.getCurQuantity());

            //Create bill sale product
            BsProduct bsP = new BsProduct(bs.getBsID(), pID, price, quantity);
            //add to bsProduct list
            this.add(bsP);
            //set current quantity of product
            pSold.setCurQuantity(pSold.getCurQuantity() - quantity);
            System.out.println("Product sold successfully!");
        }
        System.out.println("Exported successfully!\n");
    }

    public void showBsProduct() {
        if (this.isEmpty()) {
            System.out.println("Empty!");
            return;
        }
        for (BsProduct bsP : this) {
            System.out.println(bsP);
        }
    }

    public void loadFromFile(String fname1, String fname2) throws FileNotFoundException, IOException {

        File fEX = new File(fname1);
        File fBSP = new File(fname2);
        if (!fEX.exists() || !fBSP.exists()) {
            System.out.println("File not exist!");
            return;
        }
        //read exports file
        try {
            FileReader frEX = new FileReader(fEX);
            BufferedReader bfEX = new BufferedReader(frEX);
            String line;
            //bsID, date
            while ((line = bfEX.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String bsID = stk.nextToken().trim().toUpperCase();
                    Date date = MyTools.parseDate(stk.nextToken().trim(), "dd-MM-yyyy");
                    BillOfSale bs = new BillOfSale(bsID, date);
                    bList.add(bs);
                }
            }
            bfEX.close();
            frEX.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //read bs products file
        try {
            FileReader frBSP = new FileReader(fBSP);
            BufferedReader bfBSP = new BufferedReader(frBSP);
            String line;
            //bsID, pID, price, quantity
            while ((line = bfBSP.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String bsID = stk.nextToken().trim().toUpperCase();
                    String pID = stk.nextToken().trim().toUpperCase();
                    double price = Double.parseDouble(stk.nextToken().trim());
                    int quantity = Integer.parseInt(stk.nextToken().trim());

                    BsProduct bsP = new BsProduct(bsID, pID, price, quantity);
                    this.add(bsP);

                }
            }
            bfBSP.close();
            frBSP.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveBsToFile(String fname) throws IOException {
        if (bList.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        try {
            File f = new File(fname);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (BillOfSale bs : bList) {
                pw.println(bs);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveBsPToFile(String fname) throws IOException {
        if (this.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        try {
            File f = new File(fname);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (BsProduct bsP : this) {
                pw.println(bsP);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        pdList.saveProductToFile("data/products.txt");
    }

}
