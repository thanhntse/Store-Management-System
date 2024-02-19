/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author Thai Thanh Nguyen
 */
public class MyTools {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * Parsing the input string to get a boolean date (true/false)
     *
     * @param input: input string
     * @return true if the first character in input is 'T' or '1' or 'Y'
     */
    public static boolean parseBoolean(String input) {
        input = input.trim().toUpperCase();
        char c = input.charAt(0);
        return c == 'T' || c == '1' || c == 'Y';
    }

    /**
     * Normalizing a date string: Using '-' to separate date parts only
     *
     * @param dateStr: input date string
     * @return new string demo:" 7 .... ---2 ///// 2023 " --> "7-2-2023"
     */
    public static String normalizeDateStr(String dateStr) {
        //Loại bỏ tất cả khoảng trống trong chuỗi. 
        //Regex sử dụng là [\\s]+ nghĩa khoảng trắng xuất hiện 1 hoặc nhiều lần.
        String result = dateStr.replaceAll("[\\s]+", "");
        //Thay thế tất cả các nhóm kí tự . / - bằng '-'. 
        //Regex sử dụng là [./-]+ nghĩa kí tự xuất hiện 1 hoặc nhiều lần.
        result = result.replaceAll("[./-]+", "-");
        return result;
    }

    /**
     * Parsing the input string to date data
     *
     * @param inputStr: date string input
     * @param dateFormat: chosen date format
     * @return date object if successful and null if failed
     */
    public static Date parseDate(String inputStr, String dateFormat) {
        inputStr = normalizeDateStr(inputStr);
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(inputStr);

        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Convert a Date object to string using a given date format
     *
     * @param date: Date object
     * @param dateFormat: chosen date format
     * @return date string in the given format
     */
    public static String toString(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    /**
     * Getting year of the date data
     *
     * @param d: Date data
     * @param calendarPart: date part is declared in the class Calendar
     * @return year of date data
     */
    public static int getPart(Date d, int calendarPart) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(calendarPart);
    }

    //METHODS FOR READING DATA FROM KEYBOARD
    // Reading a boolean data
    public static boolean readBoolean(String prompt) {
        System.out.print(prompt + ": ");
        return parseBoolean(sc.nextLine());
    }

    /**
     * Reading a string using a pre-define pattern
     *
     * @param prompt: prompt will be printed before inputting
     * @param pattern: pre-defined pattern of input
     * @return an input string which matches the pattern
     */
    public static String readStr(String prompt, String pattern) {
        String input;
        boolean valid = false;
        do {
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            valid = input.matches(pattern);
        } while (valid == false);
        return input;
    }

    /**
     * Reading a date data using a pre-define date format dd-MM-yyyy /
     * MM-dd-yyyy/ yyyy-MM-dd
     *
     * @param prompt: prompt will be printed before inputting
     * @param pattern: pre-defined pattern of input
     * @return an input string which matches the pattern
     */
    public static Date readDate(String prompt, String dateFormat) {
        String input;
        Date d;
        do {
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            if (d == null) {
                System.out.println("Date data is not valid!");
            }
        } while (d == null);
        return d;
    }

    //Enter the date after the given date
    public static Date readDateAfter(String prompt, String dateFormat, Date marketDate) {
        String input;
        Date d;
        boolean ok = false;
        do {
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            ok = (d != null && d.after(marketDate));
            if (d == null) {
                System.out.println("Date data is not valid!");
            }
        } while (!ok);
        return d;
    }

    //Enter the date before the given date
    public static Date readDateBefore(String prompt, String dateFormat, Date marketDate) {
        String input;
        Date d;
        boolean ok = false;
        do {
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            ok = (d != null && d.before(marketDate));
            if (d == null) {
                System.out.println("Date data is not valid!");
            }
        } while (!ok);
        return d;
    }

    //Automatically generating an increasing code
    //Ex: P0000123 -> prefix: P, length=7, curNumber=123
    public static String generateCode(String prefix, int length, int curNumber) {
        String formatStr = "%0" + length + "d"; //Ex: %07d
        return prefix + String.format(formatStr, curNumber);
    }

    public static int getNumberInCode(String code, String prefix) {
        return Integer.parseInt(code.substring(prefix.length()));
    }

    public static int getNumberInCode(String code, int prefixLen) {
        return Integer.parseInt(code.substring(prefixLen));
    }

    //TESTS
    public static void main(String[] args) {
        System.out.println("Test boolean string:");
        System.out.println(parseBoolean("    TrUE    "));
        System.out.println(parseBoolean("    fALse    "));
        System.out.println(parseBoolean("    1234    "));
        System.out.println(parseBoolean("    01 23    "));
        System.out.println(parseBoolean("    total    "));
        System.out.println(parseBoolean("    cosine    "));
        System.out.println("Test normalizeDateStr(String):");
        String S = "  7.... ------2   /// 2023   ";
        System.out.println(S + " --> " + normalizeDateStr(S));
        S = "  7....2   ////// 2023   ";
        System.out.println(S + " --> " + normalizeDateStr(S));
        //Test date <--> String
        System.out.println("\nTest Date <--> String:");
        String[] formats = {"yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy"};
        String[] dStrs = {" 2023-02-21 ", "12.--23 - 2023 ", " 7.. 2 // 2023"};
        Date d = null;
        for (int i = 0; i < 3; i++) {
            System.out.println(dStrs[i] + "(" + formats[i] + ")");
            d = parseDate(dStrs[i], formats[i]);
            if (d != null) {
                System.out.println("Year: " + getPart(d, Calendar.YEAR));
                System.out.println("----> Result: " + d);
                System.out.println("----> In the format: " + formats[i] + ": " + toString(d, formats[i]));
            } else {
                System.out.println("Parsing error!");
            }
        }
        //Test reading a boolean
        System.out.println("Test reading a boolean data");
        boolean b = readBoolean("Input a boolean data (T/F, 1/0, Y/N)");
        System.out.println(b + " inputted");
        //Test input a date data
        System.out.println("Test input a date data");
        d = readDate("Input a date data (dd-MM-yyyy)", "dd-MM-yyyy");
        System.out.println("Inputted date:");
        System.out.println("In format dd-MM-yyyy: " + toString(d, "dd-MM-yyyy"));
        System.out.println("In format MM-dd-yyyy: " + toString(d, "MM-dd-yyyy"));
        System.out.println("In format yyyy-MM-dd: " + toString(d, "yyyy-MM-dd"));
        //Test inputting a phone number including 9..11 digits
        String phoneNo = readStr("Phone number (9..11 digits)", "[\\d]{9,11}");
        System.out.println("Inputted phone no. :" + phoneNo);
        //Testing for generating an automatic code
        System.out.println("Testing for generating an automatic code");
        String prefix = "P";
        int curNumber = 25;
        int len = 7;
        String code = generateCode(prefix, len, curNumber);
        curNumber++;
        System.out.println("Generated code: " + code);
        code = generateCode(prefix, len, curNumber);
        System.out.println("Generated code: " + code);
        //Test reading date data before and after today
        System.out.println("Testing reading date data before and after today");
        Date today = new Date();
        System.out.println("Today: " + MyTools.toString(d, "dd-MM-yyyy"));
        Date dBefore = MyTools.readDateBefore("Date before today", "dd-MM-yyyy", today);
        System.out.println(MyTools.toString(dBefore, "dd-MM-yyyy"));
        Date dAfter = MyTools.readDateAfter("Date after today", "dd-MM-yyyy", today);
        System.out.println(MyTools.toString(dAfter, "dd-MM-yyyy"));

    }
}
