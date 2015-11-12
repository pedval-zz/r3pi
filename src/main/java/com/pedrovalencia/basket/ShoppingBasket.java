package com.pedrovalencia.basket;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by pedrovalencia
 * on 10/11/2015.
 */
public class ShoppingBasket {

    public String getBasketBill(List<String> basket) {

        if(basket == null || basket.isEmpty()) {
            return "";
        }

        //Add to map Items and occurrences
        Map<String, Integer> item_amount = basket
                .stream()
                .collect(Collectors.toMap(key -> key, key -> 1, Integer::sum));

        //Add to map Item and price
        Map<String, Double> item_price = item_amount.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        item -> {
                            return calculatePrice.apply(item.getKey(), item.getValue());
                        },
                        Double::sum));

        //Sum all prices
        double totalPrice = item_price.entrySet()
                .stream()
                .mapToDouble(Map.Entry::getValue)
                .sum();

        //Generate a bill from the map
        String bill = item_amount.entrySet()
                .stream()
                .map(item -> generateBill.apply(item.getKey(), item.getValue()))
                .collect(Collectors.joining("\n"));


        return bill + String.format("Total = %1$,.2f", totalPrice);
    }


    private BiFunction<String, Integer, String> generateBill = (item, amount) -> {
        switch (item) {
            case "Apple": {
                return amount + " x Apple = " + (0.25 * amount)+"\n";
            }
            case "Orange": {
                return amount + " x Orange = " + (0.30 * amount)+"\n";
            }
            case "Garlic": {
                return amount + " x Garlic = " + (0.15 * amount)+"\n";
            }
            case "Papaya": {
                double papayaPrice = amount % 3 == 0 ? (2 * 0.50 * amount/3) : 0.5 * amount;
                StringBuilder papayaBill = new StringBuilder(amount + " x Papaya = " + papayaPrice);
                if(amount % 3 == 0) {
                    papayaBill.append(" (discount 3x2)");
                }
                papayaBill.append("\n");
                return papayaBill.toString();

            }
            default: {
                return ("Item "+ item+ " not found\n");
            }
        }
    };

    private BiFunction<String, Integer, Double> calculatePrice = (item,amount) -> {
        switch (item) {
            case "Apple": {
                return 0.25 * amount;
            }
            case "Orange": {
                return 0.30 * amount;
            }
            case "Garlic": {
                return 0.15 * amount;
            }
            case "Papaya": {
                return amount % 3 == 0 ? (2 * 0.50 * amount/3) : 0.5 * amount;
            }
            default: {
                return 0.0;
            }
        }
    };

}
