package com.ws.spring.core._01_basics.example2;

import com.ws.spring.core._01_basics.example2.impl.HelloMoon;

public class Main {

    /*
        Example demonstrates how to reduce the coupling with DI/IoC
     */

    public static void main(String[] args) {
        HumanBot humanBot = new HumanBot();
        System.out.println(humanBot.greeting());

        //....
        // More tight implementations here, very hard times! =(

        // Now, let's think for a moment ... what if we do a small change (better practices =D)
        HelloMoon helloMoon = new HelloMoon();

        ReusableBot reusableBot = new ReusableBot(helloMoon);
        System.out.println(reusableBot.greeting());

    }

}
