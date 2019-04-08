package com.companyname.bank;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AccountTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void givenAccount_whenDecrement_thenShouldReturnProperValue() {
        Account a = new Account(10);
        a.adjustBy(-5);

        assertEquals(5, (int) a.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAccount_whenDecrementTooMuch_thenShouldThrow() {
        Account a = new Account(10);
        a.adjustBy(-11);
    }

    @Test
    public void twoAccounts() throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        Account a = new Account(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean exceptionThrown = new AtomicBoolean(false);

        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                a.adjustBy(-6);
            } catch (IllegalArgumentException e) {
                exceptionThrown.set(true);
            }
        });
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                a.adjustBy(-5);
            } catch (IllegalArgumentException e) {
                exceptionThrown.set(true);
            }
        });
        countDownLatch.countDown();
        ex.awaitTermination(1, TimeUnit.SECONDS);
        ex.shutdown();

        assertTrue(exceptionThrown.get());
    }

    @Test
    public void transfer(){
        Account a = new Account(10);
        Account b = new Account(10);

        a.transferTo(b, 5);

        assertEquals(5, (int) a.getBalance());
        assertEquals(15, (int) b.getBalance());
    }

    @Test
    public void synch() throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        Account a = new Account(10);
        Account b = new Account(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.transferTo(b, 10);
        });
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.transferTo(a, 1);

        });
        countDownLatch.countDown();
        ex.awaitTermination(1, TimeUnit.SECONDS);
        ex.shutdown();

        assertEquals(11, (int) a.getBalance());
        assertEquals(9, (int) b.getBalance());
    }
}
