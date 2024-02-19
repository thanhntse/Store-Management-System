/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchasing;

import java.io.IOException;
import store.ProductList;
import tools.Menu;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class PurchaseMng {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String im = "data/imports.txt";
        String pd = "data/products.txt";
        String[] options = {"Import product", "Show warehouse",
            "Show products nearly expired", "Search available products",
            "Show unavailable products", "Show products with quantity less than",
            "Update product", "Save to file", "Quit the program"};
        ProductList pl = new ProductList();
        pl.loadFromFile(im, pd);
        int choice;
        boolean changed = false; //data change check
        do {
            System.out.println("\nMain menu");
            choice = Menu.getChoiceInt(options);
            switch (choice) {
                case 1:
                    pl.importProducts();
                    changed = true;
                    break;
                case 2:
                    pl.showWareHouse();
                    break;
                case 3:
                    pl.showProductNearExpiration();
                    break;
                case 4:
                    pl.searchAvaiPD();
                    break;
                case 5:
                    pl.showUnavaiPD();
                    break;
                case 6:
                    pl.showPDonRequired();
                    break;
                case 7:
                    pl.updatePD();
                    changed = true;
                    break;
                case 8:
                    pl.savePrToFile(im);
                    pl.saveProductToFile(pd);
                    changed = false;
                    break;
                default:
                    if (changed) {
                        boolean save = MyTools.readBoolean("Data change. Save to file? (Y/N)");
                        if (save) {
                            pl.savePrToFile(im);
                            pl.saveProductToFile(pd);
                        }
                    }
                    System.out.println("BYE!");
            }
        } while (choice > 0 && choice < options.length);
    }

}
