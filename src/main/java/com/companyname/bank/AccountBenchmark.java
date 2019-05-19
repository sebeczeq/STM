package com.companyname.bank;

import org.openjdk.jmh.annotations.*;

import java.util.Random;


@State(Scope.Benchmark)
public class AccountBenchmark {
    @Param({"100"})
    public int balance;
    @Param({"200", "500"})
    public int iterator;
    public Account account1;
    public Random r;

    @Setup(Level.Invocation)
    public void SetUp(){
        r = new Random();
        account1 = new Account(balance);
    }

}
