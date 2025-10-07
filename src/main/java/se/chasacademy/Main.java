package se.chasacademy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {

        //Variablar
        boolean endlessLoop = true;
        String path = "boilerroom.csv";


        //List
        List<Customer> customers = new ArrayList<>();



        //Importerar in kundens csv fil och läser den.
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean firstLine = true; // för att hoppa över header
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(","); // dela raden på komma
                String name = parts[0].trim();
                String city = parts[1].trim();
                int orderValue = Integer.parseInt(parts[2].trim());

                customers.add(new Customer(name, city, orderValue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }





        //Filtrerar kundens csv fil.
        String content = customers.stream()
                .filter(c -> c.getOrderValue() >= 1000)
                .sorted(java.util.Comparator.comparingInt(Customer::getOrderValue).reversed())
                .limit(10)
                .map(c -> c.getName() + " (" + c.getCity() + "): " + "Amount: " + c.getOrderValue() + " SEK")
                .collect(Collectors.joining(System.lineSeparator()));





        //Loop för menysystem
        while (endlessLoop) {


            printmenu(); //visar menyn

            Scanner scanner  = new Scanner(System.in);

            int val = scanner.nextInt();
            switch (val) {

                case 1:
                    customers.forEach(System.out::println);
                    System.out.println("");

                    break;

                case 2:
                    customers.stream()
                            .filter(c -> c.getOrderValue() >= 1000)
                            .sorted(java.util.Comparator.comparingInt(Customer::getOrderValue).reversed())
                            .limit(10)
                            .map(c -> c.getName() + " (" + c.getCity() + "): " + "Amount: " + c.getOrderValue() + " SEK")
                            .forEach(System.out::println);

                    System.out.println("");

                    break;

                case 3:
                    try {

                        File file = new File ("report.txt");

                        if (file.exists()) {
                            System.out.println("File already exists.");
                        } else {
                            FileWriter myWriter = new FileWriter (file);
                            myWriter.write(content);
                            myWriter.close();
                            System.out.println("File written.");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case 4:
                    System.out.println("Come back soon!");
                    endlessLoop = false;

                    break;

                default:
                    System.out.println("Default");
            }
        }

    }


    public static void printmenu(){

        System.out.println("Welcome to the csv fil reader program");

        System.out.println("1) Show current CSV-list");
        System.out.println("2) Show & filter CSV-list");
        System.out.println("3) Export current CSV-list");
        System.out.println("4) Exit program");

    }

}