package com.pm.productcatalogservice;

public class Calculator {

    Calculator(){}

    int add(int a, int b){
        return a+b;
    }

    public int divide(int a, int b){
        try{
            return a/b;
        } catch (ArithmeticException e) {
            throw e;
        }
    }
}
