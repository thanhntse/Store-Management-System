/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import java.io.IOException;
import tools.Menu;
import tools.MyTools;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class SaleMng {

    public static void main(String[] args) throws IOException {
        String pd = "data/products.txt";
        String ex = "data/exports.txt";
        String bsP = "data/bsproducts.txt";
        String[] options = {"Exports product", "Show sale products",
            "Save to file", "Quit the program"};
        BsProductList bsL = new BsProductList();
        bsL.loadFromFile(ex, bsP);
        int choice;
        boolean changed = false; //data change check
        do {
            System.out.println("\nMain menu");
            choice = Menu.getChoiceInt(options);
            switch (choice) {
                case 1:
                    bsL.exportProducts();
                    changed = true;
                    break;
                case 2:
                    bsL.showBsProduct();
                    break;
                case 3:
                    bsL.saveBsToFile(ex);
                    bsL.saveBsPToFile(bsP);
                    changed = false;
                    break;
                default:
                    if (changed) {
                        boolean save = MyTools.readBoolean("Data change. Save to file? (Y/N)");
                        if (save) {
                            bsL.saveBsToFile(ex);
                            bsL.saveBsPToFile(bsP);
                        }
                    }
                    System.out.println("BYE!");
            }
        } while (choice > 0 && choice < options.length);
    }
}
