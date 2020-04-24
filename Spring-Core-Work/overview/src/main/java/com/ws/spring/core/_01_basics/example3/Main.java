package com.ws.spring.core._01_basics.example3;


import com.ws.spring.core._01_basics.example3.impl.HelloMoon;
import com.ws.spring.core._01_basics.example3.impl.HelloTest;
import com.ws.spring.core._01_basics.example3.impl.HelloWorld;

public class Main {

    /*
        Example demonstrates the use of interfaces
     */

    public static void main(String[] args) {

        // Let's program against an interface
//        Hello hello = new HelloWorld();
//        Hello hello = new HelloMoon();
        Hello hello = new HelloTest();

        ReusableBot reusableBot = new ReusableBot(hello);
        System.out.println(reusableBot.greeting());

    }

}
