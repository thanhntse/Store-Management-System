/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class Menu {

    private final static Scanner sc = new Scanner(System.in);

    /**
     * Get the integer choose of user
     *
     * @param options: an array of string options
     * @return a choice as an integer
     */
    public static int getChoiceInt(String... options) {
        int L = options.length;
        System.out.println("------------------------------------------------");
        for (int i = 0; i < L; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Choose (1.." + L + "): ");
        return Integer.parseInt(sc.nextLine());
    }

    /**
     * Get the object choose of user
     *
     * @param list: a list of object
     * @return the chosen object
     */
    public static Object getChoiceObject(List list) {
        int choice;
        int L = list.size();
        System.out.println("------------------------------------------------");
        for (int i = 0; i < L; i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
        choice = Integer.parseInt(sc.nextLine());
        return (choice > 0 && choice <= L) ? list.get(choice - 1) : null;
    }
}
