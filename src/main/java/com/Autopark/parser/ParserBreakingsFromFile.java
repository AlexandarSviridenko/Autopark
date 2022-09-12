package com.Autopark.parser;

//import com.Autopark.Auto.Vehicle;
import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.annotations.InitMethod;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.Autopark.repairAuto.MechanicService.details;

public class ParserBreakingsFromFile implements ParserBreakingInterface {
    private static final String ORDERS_PATH = "src/main/resources/File.csv/orders.csv";

    public ParserBreakingsFromFile() {
    }

    @InitMethod
    public void init() {
    }

    @Override
    public void writeOrder(long id, String defect, int amount) {

    }

    @Override
    public int findRow(long id) {
        return 0;
    }

    @Override
    public void deleteOrderString(int rowNumber) {

    }

//    public void writeOrder(long id, String defect, int amount) {
//        try (FileWriter writer = new FileWriter(new FileWriter(ORDERS_PATH, true),
//                ',',
//                FileWriter.NO_QUOTE_CHARACTER,
//                ' ',
//                "\n"))
//        {
//            String[] line = {String.valueOf(id), defect, String.valueOf(amount)};
//            writer.writeNext(line, false);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public int findRow(long id) {
//        try (CSVReader reader = new CSVReader(new FileReader(ORDERS_PATH))) {
//
//            String [] nextLine = reader.readNext();
//            int counter = 0;
//            int idFound = 0;
//            while (nextLine != null) {
//                if (!nextLine[0].isEmpty()) {
//                    idFound = Integer.parseInt(nextLine[0]);
//                }
//                if (idFound == id) {
//                    return counter;
//                }
//                nextLine = reader.readNext();
//                counter++;
//            }
//        } catch (CsvException | IOException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    public void deleteOrderString(int rowNumber) {
//        try (CSVReader reader = new CSVReader(new FileReader(ORDERS_PATH))) {
//            List<String[]> allLines = reader.readAll();
//            allLines.remove(rowNumber);
//            CSVWriter writer = new CSVWriter(new FileWriter(ORDERS_PATH),
//                    ',',
//                    CSVWriter.NO_QUOTE_CHARACTER,
//                    ' ',
//                    "\n");
//            writer.writeAll(allLines);
//            writer.close();
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
    }

//    public void loadOrders(Vehicles vehicle, Map<String, Integer> result) {
//        createRandomBrokenDetailsAndReturnSum(vehicle, result);
//        writeInFileBrokenDetails(vehicle, result);
//    }
//
//    public void createRandomBrokenDetailsAndReturnSum(Vehicles vehicle, Map<String, Integer> result) {
//        //int sum = 0;
//        for (String s : details) {
//            int randomNumber = (int) (Math.random() * 2);
//            result.put(s, randomNumber);
////            sum = calculateSumBrokenDetails(randomNumber, sum);
//            //if (randomNumber > 0) vehicle.setBroken(true);
//        }
//        //return sum;
//    }
//
////    public int calculateSumBrokenDetails(int randomNumber, int sum) {
////        sum += randomNumber;
////        return sum;
////    }
//
//    public Map writeInFileBrokenDetails(Vehicles vehicle, Map<String, Integer> result) {
//        try {
//            FileWriter fileWriter = new FileWriter(ORDERS_PATH, true);
//            StringBuilder details = new StringBuilder();
//            details.append(vehicle.getId());
//
//            for (String key : result.keySet()) {
//                details.append(",").append(key).append(",").append(result.get(key));
//            }
//            details.append("\n");
//            fileWriter.write(details.toString());
//            fileWriter.flush();
//            //vehicle.setBrokenParts(details.substring(2, details.length()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
////    public long counter(Vehicles vehicle) {
////        String[] details = vehicle.getBrokenParts().split(",");
////        details[details.length - 1] = details[details.length - 1].replace("\n", "");
////        long counter = 0;
////        for (int i = 1; i < details.length; i += 2) {
////            if (Integer.parseInt(details[i]) != 0) {
////                details[i] = "0";
////                counter++;
////            }
////        }
////        return counter;
////    }
//
////    public void counterMoreNull(Vehicle vehicle, long counter) {
////        if (counter > 0) {
////            System.out.println("Vehicle '" + vehicle.getModelName() + "' was fixed" + "    Repair details: " + vehicle.getSumOfBrokenParts());
////            vehicle.setBroken(false);
////        }
////    }
////
////    public void counterEqualNull(Vehicle vehicle, long counter) {
////        if (counter == 0) {
////            System.out.println("Vehicle is healthy");
////        }
////    }
//
//    public void writeInFileRemainingBreaking(Vehicles vehicle, List<String> result) {
//        try {
//            File writer = new File(ORDERS_PATH);
//            FileWriter file = new FileWriter(writer, false);
//            Scanner scanner = new Scanner(writer);
//            while (scanner.hasNextLine()) {
//                String str = scanner.nextLine();
//                if (vehicle.getId() != Integer.parseInt(str.substring(0))) {
//                    result.add(str + "\n");
//                }
//            }
//            for (String s : result) {
//                file.write(s);
//            }
//            file.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


