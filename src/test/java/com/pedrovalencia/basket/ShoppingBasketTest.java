package com.pedrovalencia.basket;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pedrovalencia on 10/11/2015.
 */
public class ShoppingBasketTest {

    private static ShoppingBasket shoppingBasket;

    @BeforeClass
    public static void setUp() throws Exception {
        shoppingBasket = new ShoppingBasket();
    }

    @Test
    public void testEmptyBasket() throws Exception {
        List<String> basket = new ArrayList<String>();

        String bill = shoppingBasket.getBasketBill(basket);
        Assert.assertEquals("", bill);

    }

    @Test
    public void testNullBasket() throws Exception {

        String bill = shoppingBasket.getBasketBill(null);
        Assert.assertEquals("", bill);
    }

    @Test
    public void test3ApplesInBasket() throws Exception {
        List<String> basket = Arrays.asList("Apple","Apple","Apple");

        String bill = shoppingBasket.getBasketBill(basket);
        Assert.assertEquals("3 x Apple = 0.75\nTotal = 0.75", bill);

    }

    @Test
    public void testAllItemsInBasketWithNoOffer() throws Exception {
        List<String> basket = Arrays.asList("Apple","Orange","Garlic","Papaya");
        String bill = shoppingBasket.getBasketBill(basket);

        Assert.assertTrue(bill.contains("1 x Apple = 0.25\n"));
        Assert.assertTrue(bill.contains("1 x Orange = 0.3\n"));
        Assert.assertTrue(bill.contains("1 x Garlic = 0.15\n"));
        Assert.assertTrue(bill.contains("1 x Papaya = 0.5\n"));
        Assert.assertTrue(bill.contains("Total = 1.2"));
    }

    @Test
    public void testAllItemsInBasketWithOffer() throws Exception {
        List<String> basket = Arrays.asList("Papaya","Apple","Orange","Papaya","Garlic","Papaya");
        String bill = shoppingBasket.getBasketBill(basket);

        Assert.assertTrue(bill.contains("1 x Apple = 0.25\n"));
        Assert.assertTrue(bill.contains("1 x Orange = 0.3\n"));
        Assert.assertTrue(bill.contains("1 x Garlic = 0.15\n"));
        Assert.assertTrue(bill.contains("3 x Papaya = 1.0 (discount 3x2)\n"));
        Assert.assertTrue(bill.contains("Total = 1.7"));
    }

    @Test
    public void testFakeItem() throws Exception {
        List<String> basket = Arrays.asList("Papaya","Apple","Orange","Papaya","Garlic","Chirimoya");
        String bill = shoppingBasket.getBasketBill(basket);

        Assert.assertTrue(bill.contains("1 x Apple = 0.25\n"));
        Assert.assertTrue(bill.contains("1 x Orange = 0.3\n"));
        Assert.assertTrue(bill.contains("1 x Garlic = 0.15\n"));
        Assert.assertTrue(bill.contains("2 x Papaya = 1.0\n"));
        Assert.assertTrue(bill.contains("Total = 1.7"));
        Assert.assertTrue(bill.contains("Item Chirimoya not found"));
    }



}
