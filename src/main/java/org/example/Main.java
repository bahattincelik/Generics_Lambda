package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();

        products.add(new Product("Laptop", 1000.0));

        products.add(new Product("Phone", 500.0));

        products.add(new Product("Tablet", 300.0));

        products.add(new Product("Computer",750.0));

        List<Product> filteredProducts = filterByPrice(products, u-> u.getPrice() > 500.0);

        List<Product> newProducts = changeType(products, u-> {
            double newPrice = u.getPrice() * 1.1;
            return new Product(u.getName(), newPrice);
        });

        List<Product> oldProducts = mapAndFilterUnary(products, u1 -> {
            double newPrice = u1.getPrice() * 0.9;
            return new Product(u1.getName(), newPrice);
                });

        makeConsumer(filteredProducts, product -> {
            System.out.println("Name: " + product.getName());
            System.out.println("New Price: " + product.getPrice());
            System.out.println("-----------------------------");
        });

        Product neueProduct = produceSupplier(() -> new Product("Neuer Gerät", 750.0));
        System.out.println("Neuer Gerät:");
        System.out.println("Name: " + neueProduct.getName());
        System.out.println("Preis: " + neueProduct.getPrice());

        System.out.println("Filtered Products:");
        filteredProducts.forEach(System.out::println);

        List<Product> neueProducts = new ArrayList<>();
        neueProducts.add(new Product("Neuer Computer", 1200.0));
        neueProducts.add(new Product("Neuer Tablet", 600.0));
        neueProducts.add(new Product("Tisch", 600.0));
        neueProducts.add(new Product("Kühlschrank", 300.0));
        neueProducts.add(new Product("Schrank", 500.0));
        neueProducts.add(new Product("Glass", 500));

        List<Double> totalPrices = mapAndFilter(products, neueProducts, (u1, u2) -> u1.getPrice() + u2.getPrice());

        List<Product> combinedProductList = mapAndFilterBinary(products, neueProducts, (u1, u2) ->{
            String newName = u1.getName() + " + " + u2.getName();
            double totalPrice = u1.getPrice() + u2.getPrice();
            return new Product(newName, totalPrice);
        });

        makeBiConsumer(filteredProducts, combinedProductList, (product1, product2) -> {
            System.out.println(product1.getName() + " + " + product2.getName());
            System.out.println("Total Price: " + product1.getPrice() + " + " + product2.getPrice());
            System.out.println("-----------------------------");
        });

        System.out.println("Total Prices:");
        totalPrices.forEach(f-> System.out.println("Total Price " + f));

    }

    /*public static <T> List<T> filterByPrice(List<T> list, Predicate<T> kriterien ){
        return list.stream()
                .filter(kriterien)
                .collect(Collectors.toList());
    }*/

    public static <T> List<T> filterByPrice(List<T> list, kriterien<T> kriterien){
        List<T> result = new ArrayList<>();
        for(T t: list){
            if(kriterien.test(t)){
                result.add(t);
            }
        }
        return result;
    }

    public static <T, R> List<R> changeType(List<T> list, Function<T, R> mapper){
        List<R> result = new ArrayList<>();
        for(T t: list){
            result.add(mapper.apply(t));
        }
        return result;
    }

    public static <T, U, R> List<R> mapAndFilter(List<T> list1, List<U> list2, BiFunction<T, U, R> mapper){
        List<R> result = new ArrayList<>();
        int size = Math.min(list1.size(), list2.size());
        for(int i = 0; i < size; i++){
            result.add(mapper.apply(list1.get(i), list2.get(i)));
        }
        return result;
    }

    public static <T> List<T> mapAndFilterUnary(List<T> list, UnaryOperator<T> operator){
        List<T> result = new ArrayList<>();
        for(T t: list){
            result.add(operator.apply(t));
        }
        return result;
    }

    public static <T> List<T> mapAndFilterBinary(List<T> list1, List<T> list2, BinaryOperator<T> operator){
        List<T> result = new ArrayList<>();
        int size = Math.min(list1.size(), list2.size());
        for(int i = 0; i < size; i++){
            result.add(operator.apply(list1.get(i), list2.get(i)));
        }
        return result;
    }

    public static <T> void makeConsumer(List<T> list, Consumer<T> consumer){
        for(T t: list){
            consumer.accept(t);
        }
    }

    public static <T, U> void makeBiConsumer(List<T> list1, List<U> list2, BiConsumer<T, U> consumer){
        int size = Math.min(list1.size(), list2.size());
        for(int i = 0; i < size; i++){
            consumer.accept(list1.get(i), list2.get(i));
        }
    }

    public static <T> T produceSupplier(Supplier<T> supplier){
        return supplier.get();
    }
}






