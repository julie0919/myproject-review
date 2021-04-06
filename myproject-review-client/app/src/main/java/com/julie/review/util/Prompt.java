package com.julie.review.util;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {
  static Scanner sc = new Scanner(System.in);

  public static String printString (String title) {
    System.out.print(title);
    return sc.nextLine();
  }

  public static int printInt (String title) {
    return Integer.parseInt(printString(title));
  }

  public static Date printDate (String title) {
    return Date.valueOf(printString(title));
  }

  public static void close () {
    sc.close();
  }

}
