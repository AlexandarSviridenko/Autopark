package com.Autopark;

import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.Autopark.MechanicService.details;

public class ParserBreakingsFromFile {
    private static final String ORDERS_PATH = "src/main/resources/File.csv/orders.csv";

    public ParserBreakingsFromFile() {
    }

    @InitMethod
    public void init() {
    }

    public static void loadOrders(Vehicle vehicle, Map<String, Integer> result) {
        createRandomBrokenDetailsAndReturnSum(vehicle, result);
        writeInFileBrokenDetails(vehicle, result);
    }

    public static int createRandomBrokenDetailsAndReturnSum(Vehicle vehicle, Map<String, Integer> result) {
        int sum = 0;
        for (String s : details) {
            int randomNumber = (int) (Math.random() * 2);
            result.put(s, randomNumber);
            sum = calculateSumBrokenDetails(randomNumber, sum);
            if (randomNumber > 0) vehicle.setBroken(true);
        }
        return sum;
    }

    public static int calculateSumBrokenDetails(int randomNumber, int sum) {
        sum += randomNumber;
        return sum;
    }

    public static Map writeInFileBrokenDetails(Vehicle vehicle, Map<String, Integer> result) {
        try {
            FileWriter fileWriter = new FileWriter(ORDERS_PATH, true);
            StringBuilder details = new StringBuilder();
            details.append(vehicle.getId());
            for (String key : result.keySet()) {
                details.append(",").append(key).append(",").append(result.get(key));
            }
            details.append("\n");
            fileWriter.write(details.toString());
            fileWriter.flush();
            vehicle.setBrokenParts(details.substring(2, details.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int counter(Vehicle vehicle) {
        String[] details = vehicle.getBrokenParts().split(",");
        details[details.length - 1] = details[details.length - 1].replace("\n", "");
        int counter = 0;
        for (int i = 1; i < details.length; i += 2) {
            if (Integer.parseInt(details[i]) != 0) {
                details[i] = "0";
                counter++;
            }
        }
        return counter;
    }

    public static void counterMoreNull(Vehicle vehicle, int counter) {
        if (counter > 0) {
            System.out.println("Vehicle '" + vehicle.getModelName() + "' was fixed" + "    Repair details: " + vehicle.getSumOfBrokenParts());
            vehicle.setBroken(false);
        }
    }

    public static void counterEqualNull(Vehicle vehicle, int counter) {
        if (counter == 0) {
            System.out.println("Vehicle is healthy");
        }
    }

    public static void writeInFileRemainingBreaking(Vehicle vehicle, List<String> result) {
        try {
            File writer = new File(ORDERS_PATH);
            FileWriter file = new FileWriter(writer, false);
            Scanner scanner = new Scanner(writer);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (vehicle.getId() != Integer.parseInt(str.substring(0))) {
                    result.add(str + "\n");
                }
            }
            for (String s : result) {
                file.write(s);
            }
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
