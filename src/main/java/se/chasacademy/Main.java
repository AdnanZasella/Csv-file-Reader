package se.chasacademy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        List<Customer> customers = new ArrayList<>();

        String path = "boilerroom.csv";

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



        String content = customers.stream()
                .filter(c -> c.getOrderValue() >= 1000)
                .sorted(java.util.Comparator.comparingInt(Customer::getOrderValue).reversed())
                .limit(10)
                .map(c -> c.getName() + " (" + c.getCity() + "): " + "Amount: " + c.getOrderValue() + " SEK")
                .collect(Collectors.joining(System.lineSeparator()));




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


    }
}