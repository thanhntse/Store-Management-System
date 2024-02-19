/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;
import purchasing.PurchaseReceipt;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class ProductList extends ArrayList<Product> {

    private ArrayList<PurchaseReceipt> prList;

    public ProductList() {
        prList = new ArrayList<>();
    }

    public void importProducts() {
        //Create Purchase Receipt
        System.out.println("Create Purchase Receipt:");
        Date today = new Date();
        Date date = MyTools.readDateBefore("Date(dd-MM-yyyy)", "dd-MM-yyyy", today);
        PurchaseReceipt pr = new PurchaseReceipt(date);
        //add to purchase receipt list
        prList.add(pr);
        int numberOfProducts = Integer.parseInt(MyTools.readStr("Number of products purchased", "([1-9][\\d]*)|([0]+[\\d]+)"));
        while (numberOfProducts-- != 0) {
            System.out.println("\nProduct Information:");
            String name = MyTools.readStr("Name", "[a-zA-Z].*").toUpperCase();
            double price = Double.parseDouble(MyTools.readStr("Price", "([1-9][\\d]*[.]?[\\d]*)|([0][.][\\d]+)"));
            int initialQ = Integer.parseInt(MyTools.readStr("Initial Quantity", "([1-9][\\d]*)|([0]+[\\d]+)"));
            Date productionDate = MyTools.readDateBefore("Production date", "dd-MM-yyyy", date);
            Date expirationDate = MyTools.readDateAfter("Expiration date", "dd-MM-yyyy", date);
            //curQ=initialQ
            int curQ = initialQ;
            //Create product
            Product p = new Product(pr.getPrID(), name, price, initialQ, curQ, productionDate, expirationDate);
            //add to product list
            this.add(p);
            System.out.println("Product added successfully!");
        }
        System.out.println("Imported successfully!\n");
    }

    public void showWareHouse() {
        if (this.isEmpty()) {
            System.out.println("Empty!");
            return;
        }
        Comparator<Product> com = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1.getPrID().equals(o2.getPrID())) {
                    return o1.getpID().compareTo(o2.getpID());
                }
                return o1.getPrID().compareTo(o2.getPrID());
            }
        };
        ProductList list = (ProductList) this.clone();
        Collections.sort(list, com);
        for (Product p : list) {
            if (p.getCurQuantity() > 0) {
                System.out.println(p);
            }
        }
    }

    public void loadFromFile(String fname1, String fname2) throws FileNotFoundException, IOException {

        File fIM = new File(fname1);
        File fPD = new File(fname2);
        if (!fIM.exists() || !fPD.exists()) {
            System.out.println("File not exist!");
            return;
        }
        //read imports file
        try {
            FileReader frIM = new FileReader(fIM);
            BufferedReader bfIM = new BufferedReader(frIM);
            String line;
            //prID, date
            while ((line = bfIM.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String prID = stk.nextToken().trim().toUpperCase();
                    Date date = MyTools.parseDate(stk.nextToken().trim(), "dd-MM-yyyy");
                    PurchaseReceipt pr = new PurchaseReceipt(prID, date);
                    prList.add(pr);
                }
            }
            bfIM.close();
            frIM.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //read products file
        try {
            FileReader frPD = new FileReader(fPD);
            BufferedReader bfPD = new BufferedReader(frPD);
            String line;
            //prID, pID, name, price, iniQuantity, curQuantity, productionD, expirationD
            while ((line = bfPD.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String prID = stk.nextToken().trim().toUpperCase();
                    String pID = stk.nextToken().trim().toUpperCase();
                    String name = stk.nextToken().trim().toUpperCase();
                    double price = Double.parseDouble(stk.nextToken().trim());
                    int initialQ = Integer.parseInt(stk.nextToken().trim());
                    int curQ = Integer.parseInt(stk.nextToken().trim());
                    Date pdDate = MyTools.parseDate(stk.nextToken().trim(), "dd-MM-yyyy");
                    Date exDate = MyTools.parseDate(stk.nextToken().trim(), "dd-MM-yyyy");

                    Product p = new Product(prID, pID, name, price, initialQ, curQ, pdDate, exDate);
                    this.add(p);
                }
            }
            bfPD.close();
            frPD.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void savePrToFile(String fname) throws IOException {
        if (prList.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        try {
            File f = new File(fname);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (PurchaseReceipt pr : prList) {
                pw.println(pr);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveProductToFile(String fname) throws IOException {
        if (this.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        try {
            File f = new File(fname);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Product p : this) {
                pw.println(p);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showProductNearExpiration() {
        boolean found = false;
        Date today = new Date();
        for (Product p : this) {
            //within 10 days from today
            if (p.getExpirationDate().getTime() - today.getTime() <= (10 * 24 * 60 * 60 * 1000)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Empty!");
        }
    }

    public void searchAvaiPD() {
        String name = MyTools.readStr("Name for search", ".*").toUpperCase();
        boolean found = false;
        for (Product p : this) {
            if (p.getName().contains(name) && p.getCurQuantity() > 0) {
                System.out.println("Found: " + p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Not found!");
        }
    }

    public void showUnavaiPD() {
        boolean found = false;
        for (Product p : this) {
            if (p.getCurQuantity() == 0) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Empty!");
        }
    }

    public void showPDonRequired() {
        boolean found = false;
        int n = Integer.parseInt(MyTools.readStr("Min quantity", "([1-9][\\d]*)|([0]+[\\d]+)"));
        for (Product p : this) {
            if (p.getCurQuantity() < n) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Empty!");
        }
    }

    public void updatePD() {
        String ID = MyTools.readStr("ID for update", ".*").toUpperCase();
        //find product
        int pos = this.indexOf(new Product(ID));
        if (pos < 0) {
            System.out.println("Not found!");
            return;
        }
        boolean check = false;
        //Update data
        Product p = this.get(pos);
        String newName = MyTools.readStr("New name, Press Enter for bypass", ".*").toUpperCase();
        if (!newName.isEmpty() && newName.matches("[a-zA-Z].*")) {
            p.setName(newName);
            check = true;
        }

        String newPrice = MyTools.readStr("New price, Press Enter for bypass", ".*");
        if (!newPrice.isEmpty() && newPrice.matches("([1-9][\\d]*[.]?[\\d]*)|([0][.][\\d]+)")) {
            p.setPurchasePrice(Double.parseDouble(newPrice));
            check = true;
        }

        String newQuantity = MyTools.readStr("New initial quantity, Press Enter for bypass", ".*");
        if (!newQuantity.isEmpty() && newQuantity.matches("([1-9][\\d]*)|([0]+[\\d]+)")) {
            int extraProduct = Integer.parseInt(newQuantity) - p.getInitialQuantity();
            p.setInitialQuantity(Integer.parseInt(newQuantity));
            p.setCurQuantity(p.getCurQuantity() + extraProduct);
            check = true;
        }
        if (check) {
            System.out.println("Update successfully!");
        } else {
            System.out.println("No update!");
        }
    }

}
