package com.company;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @org.junit.jupiter.api.Test
    void calc1() {

        //arrange
        int expected = 15;

        //action
        String input = "5+2*sqrt(25)";

        Parser problem = new Parser();
        List<String> expression = problem.parseInfix(input);

        boolean flag = problem.flag;
        double action = 0;
        if (flag){
            action = problem.calc(expression);
        }

        //assert
        assertEquals(expected, action);
    }

    @org.junit.jupiter.api.Test
    void calc2() {

        //arrange
        int expected = 0;

        //action
        String input = "square(3) - 9";

        Parser problem = new Parser();
        List<String> expression = problem.parseInfix(input);

        boolean flag = problem.flag;
        double action = 0;
        if (flag){
            action = problem.calc(expression);
        }

        //assert
        assertEquals(expected, action);
    }

    @org.junit.jupiter.api.Test
    void calc3() {

        //arrange
        int expected = 26;

        //action
        String input = "2*(16-sqrt(square(14/7) + 10/2))";

        Parser problem = new Parser();
        List<String> expression = problem.parseInfix(input);

        boolean flag = problem.flag;
        double action = 0;
        if (flag){
            action = problem.calc(expression);
        }

        //assert
        assertEquals(expected, action);
    }

}