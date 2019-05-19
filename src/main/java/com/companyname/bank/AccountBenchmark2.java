package com.companyname.bank;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class AccountBenchmark2 {
    @Param({"200", "500"})
    public int iterator;
    public Account a;
    public Account b;

    @Setup(Level.Invocation)
    public void SetUp (){
        a = new Account(20000);
        b = new Account(0);
    }

}
