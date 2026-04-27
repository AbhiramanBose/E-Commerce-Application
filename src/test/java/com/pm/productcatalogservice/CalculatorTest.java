package com.pm.productcatalogservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    //test_when_then
    //give propercontexttouser
    @Test
    public void Test_WhenTwoIntegersAreAdded_RunsSuccessfully(){
        //Arrange
        Calculator calculator=new Calculator();

        //Act
        int result=calculator.add(1,2);

        //Assert
        assertEquals(3,result,"The result of addition is not 30");
    }

    @Test
    public void test_DivideByZero_ResultsInArithmeticException(){
        //Arrange
        Calculator calculator=new Calculator();

        //Act&Assert
        assertThrows(ArithmeticException.class,()->calculator.divide(1,0));
    }

}