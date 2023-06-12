package com.sf.quickbuy;

public class MockQuickBuy {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 40; i++) {
            new QuickBuyThread().start();
        }
       /* Thread.sleep(2000);
        for (int i = 0; i < 40; i++) {
            new QuickBuyThread().start();
        }
        Thread.sleep(5000);
        for (int i = 0; i < 40; i++) {
            new QuickBuyThread().start();
        }*/
    }
}
