package com.companyname.bank;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class AccountBenchmark {
    @Param({"100", "200"})
    public int balance;
    @Param({"10", "20"})
    public int iterator;
    public Account account;

    @Setup(Level.Invocation)
    public void SetUp(){
        account = new Account(balance);
    }
}
