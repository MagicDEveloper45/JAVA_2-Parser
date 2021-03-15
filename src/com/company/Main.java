package com.company;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void introduction()
    {
        System.out.println("Hello, this calculator can" +
                "\ncount: 2+2 " +
                "\nsubtract: 2-2 " +
                "\nmultiply: 2*2 " +
                "\ndivide: 2/2 " +
                "\ncalculate the root of a number: sqrt(number), " +
                "\ncalculate the square of a number square(number) " +
                "\nand also allows you to use brackets" +
                "\nfor example: 5 + 7 - 6 * (square(2) - sqrt(16)), result = 12.0  " +
                "\n----------------------------------------------------------------------");
    }


    public static void main(String[] args) {

        introduction();

        System.out.println("Enter your problem");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        Parser problem = new Parser();

        List<String> expression = problem.parseInfix(input);

        boolean flag = problem.flag;

        if (flag)
        {
            System.out.println(problem.calc(expression));
        }
        return;
    }
}
