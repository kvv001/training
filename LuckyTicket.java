package sber.example;

import java.util.Scanner;

public class LuckyTicket {
    static int digitsSum(int number) {
        int sum = 0;

        for (; number != 0; number /= 10) {
            sum += number % 10;
        }

        return sum;
    }

    @SuppressWarnings("checkstyle:MethodParamPad")
    public static void main(String[] args) {
        System.out.print("Ticket: ");
        //InputStream tt = System.in;
        Scanner in = new Scanner(System.in);
        //System.out.print("Input a number: ");
        int num = in.nextInt();


        System.out.println(validateInputTicketNumber(num));
        in.close();
    }

    public static boolean validateInputTicketNumber(int ticketNum) {
        boolean isLucky = false;
        if (ticketNum >= 1 && ticketNum <= 999999) {
            isLucky = digitsSum(ticketNum / 1000) == digitsSum(ticketNum % 1000);
        }
        return isLucky;
    }
}
