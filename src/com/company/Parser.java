package com.company;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.lang.*;

public class Parser {

    private static String operators = "+-*/";           // basic symbols of math operation
    private static String brackets = "() " + operators; // separating brackets
    public static boolean flag = true;                  // mark of successful completion of the calculation

    private static List<String> postfix;                // input string converted to postfix form
    private static Deque<String> stack;


    // checking whether the symbol is a operators
    private static boolean isOperator(@NotNull String symbol) {
        if (symbol.equals("u-")) {
            return true;
        }
        for (int i = 0; i < operators.length(); i++) {
            if (symbol.charAt(0) == operators.charAt(i)) return true;
        }

        return false;
    }

    // checking whether the symbol is a brackets
    private static boolean isBrackets(@NotNull String symbol) {
        if (symbol.length() < 1 && symbol.length() > 1) {
            return false;
        }
        for (int i = 0; i < brackets.length(); i++) {
            if (symbol.charAt(0) == brackets.charAt(i)) return true;
        }
        return false;
    }



    // checking whether a symbol is a declared function
    private static boolean isFunction(@NotNull String symbol) {
        if (symbol.equals("sqrt") || symbol.equals("square")) {
            return true;
        }
        return false;
    }

    // priorities of mathematical operators
    private static int priorities(@NotNull String symbol) {
        if (symbol.equals("(")) {
            return 1;
        }
        if (symbol.equals("+") || symbol.equals("-")) {
            return 2;
        }
        if (symbol.equals("*") || symbol.equals("/")) {
            return 3;
        }
        return 4; // for other symbols
    }

    /* Summary
     This method is translated into reverse Polish notation
     We read all the symbols in turn, removing the spaces along the way;
     And we place each character depending on its value
     so, the stack includes: functions (sqrt, square), opening parenthesis, etc.
     similarly for the resulting string
    */
    public static List<String> parseInfix(@NotNull String inputInfixStr) {
        postfix = new ArrayList<String>();
        stack = new ArrayDeque<String>();

        StringTokenizer tokenizer = new StringTokenizer(inputInfixStr, brackets, true);
        String prev = ""; String curr = "";

        while (tokenizer.hasMoreTokens()) {

            curr = tokenizer.nextToken();

            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                System.out.println("Error_1. Input string - invalid");
                flag = false;
                return postfix;
            }

            if (curr.equals(" ")) continue; // ignoring spaces

            if (isFunction(curr)){
                stack.push(curr);
            }
            else if (isBrackets(curr))
            {
                if (curr.equals("(")) {
                    stack.push(curr);
                }
                else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Error_2. Incorrect input brackets");
                            flag = false;
                            return postfix;
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } else {
                    if (curr.equals("-") && (prev.equals("") || (isBrackets(prev) && !prev.equals(")")))) {
                        // taking into consideration the unary minus sign
                        curr = "u-";
                    } else {
                        while (!stack.isEmpty() && (priorities(curr) <= priorities(stack.peek()))) {
                            postfix.add(stack.pop());
                        }
                    }
                    stack.push(curr);
                }
            } else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                System.out.println("Error_2. Incorrect input brackets");
                flag = false;
                return postfix;
            }
        }
        return postfix;
    }

    /*Summary
    This is a method for calculating the value of an expression in reverse Polish notation:
    * if a number is found in the record, then we put it on the stack;
    * if a function or a unary minus is found in the record, then
        applying a function or multiplication by minus to the top element of the stack and putting the element back on the stack;
    * if a mathematical operator is found in the entry, then
        perform the found action with 2 elements from the top of the stack and we put the result of the operation in the stack;
    * finally output the last element of the stack - result
    */
    public Double calc(@NotNull List<String> inputPostfixStr) {

        Deque<Double> stack = new ArrayDeque<Double>();

        for (String x : inputPostfixStr) {
            if (x.equals("sqrt")){
                stack.push(Math.sqrt(stack.pop()));
            }
            else if (x.equals("square")) {
                Double tmp = stack.pop(); stack.push(tmp * tmp);
            }
            else if (x.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            }
            else if (x.equals("-")) {
                Double top = stack.pop(), bott = stack.pop(); stack.push(bott - top);
            } else if (x.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            }
            else if (x.equals("/")) {
                Double top = stack.pop(), bott = stack.pop(); stack.push(bott / top);
            } else if (x.equals("u-")){
                stack.push(-stack.pop());
            }
            else stack.push(Double.valueOf(x));
        }

        return stack.pop();
    }
}



