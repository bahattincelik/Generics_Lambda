package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();

        products.add(new Product("Laptop", 1000.0));

        products.add(new Product("Phone", 500.0));

        products.add(new Product("Tablet", 300.0));

        products.add(new Product("Computer",750.0));

        List<Product> filteredProducts = filterByPrice(products, u-> u.getPrice() > 500.0);

        System.out.println("Filtered Products:");
        filteredProducts.forEach(System.out::println);


    }

    public static <T> List<T> filterByPrice(List<T> list, Predicate<T> critera){
        return list.stream()
                .filter(critera)
                .collect(Collectors.toList());
    }
}