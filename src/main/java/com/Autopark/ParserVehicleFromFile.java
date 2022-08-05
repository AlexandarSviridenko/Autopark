package com.Autopark;

import com.Autopark.Engine.DieselEngine;
import com.Autopark.Engine.ElectricalEngine;
import com.Autopark.Engine.GasolineEngine;
import com.Autopark.Engine.Startable;
import com.Autopark.Exception.DefectedVehicleException;
import com.Autopark.Exception.NotFindTypeById;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ParserVehicleFromFile {

    public static final String TYPES_PATH = "src/main/resources/File.csv/types.csv";
    public static final String VEHICLES_PATH = "src/main/resources/File.csv/vehicles.csv";
    public static final String RENTS_PATH = "src/main/resources/File.csv/rents.csv";
    @Autowired
    private TechnicalSpecialist specialist;

    public ParserVehicleFromFile() {
    }

    @InitMethod
    public void init() {
    }

    public List<Vehicle> getVehicles() {
        return loadVehicles();
    }

    public void sortCollection() {
        Collections.sort(getVehicles(), new VehicleComparator());
    }

    public List<VehicleType> loadTypes() {

        List<VehicleType> list = new ArrayList<>();
        List<String> csvStrings = readFile(TYPES_PATH);

        for (String csvString : csvStrings) {
            list.add(createType(csvString));
        }

        return list;
    }

    public List<Rent> loadRents() {

        List<Rent> list = new ArrayList<>();
        List<String> csvStrings = readFile(RENTS_PATH);

        for (String csvString : csvStrings) {
            list.add(createRent(csvString));
        }

        return list;
    }

    public List<Vehicle> loadVehicles() {
        List<Vehicle> list = new ArrayList<>();
        List<String> csvStrings = readFile(VEHICLES_PATH);

        for (String csvString : csvStrings) {
            list.add(createVehicle(csvString));
        }

        return list;
    }

    public VehicleType createType(String csvString) {
        int id;
        double taxCoefficient;
        String typeName;
        String[] params = parseLine(csvString);

        taxCoefficient = Double.parseDouble(params[2]);
        id = Integer.parseInt(params[0]);
        typeName = params[1];

        return new VehicleType(typeName, taxCoefficient, id);
    }

    public Rent createRent(String csvString) {
        int id;
        Date date;
        double cost;
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String[] params = parseLine(csvString);

        cost = Double.parseDouble(params[2]);
        id = Integer.parseInt(params[0]);

        try {
            date = formatter.parse(params[1]);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

        getVehicleById(id).getRents().add(new Rent(date, cost));

        return new Rent(date, cost);
    }

    public Vehicle createVehicle(String csvString) {
        int id;
        int typeId;
        Startable engine;
        String modelName;
        String registrationNumber;
        int weight;
        int manufactureYear;
        int mileage;
        Color color;
        boolean broken;

        String[] params = parseLine(csvString);

        id = Integer.parseInt(params[0]);
        modelName = params[2];
        registrationNumber = params[3];
        weight = Integer.parseInt(params[4]);
        manufactureYear = Integer.parseInt(params[5]);
        mileage = Integer.parseInt(params[6]);
        color = Color.valueOf(params[7].toUpperCase(Locale.ROOT));

        typeId = Integer.parseInt(params[1]);
        VehicleType type = getTypeById(typeId);

        engine = createEngine(params, 8);
        return new Vehicle(id, type, engine, modelName, registrationNumber, weight, manufactureYear, mileage, color);
    }

    public void insert(Vehicle v) {
        loadVehicles().add(v);
    }

    public List<VehicleType> getVehicleTypes() {
        return loadTypes();
    }

    public int delete(int index) {
        if (index >= 0 && index < loadVehicles().size()) {
            loadVehicles().remove(index);
            return index;
        }

        return -1;
    }

    double sumTotalProfit() {
        double sum = 0.0d;

        for (Vehicle vehicle : loadVehicles()) {
            sum += vehicle.getTotalProfit();
        }

        return sum;
    }

    public void display() {
        String indentsTop = "%5s %10s %20s %10s %11s %6s %8s %7s %9s %10s %9s";
        String indentsLine = "%5d %10s %20s %10s %11d %6d %8d %7s %9.2f %10.2f %10.2f";
        String indentsBottom = "%5s %102.2f";
        String idStr = "Id";
        String typeStr = "Type";
        String modelNameStr = "Model Name";
        String numberStr = "Number";
        String weightStr = "Weight (kg)";
        String yearStr = "Year";
        String mileageStr = "Mileage";
        String colorStr = "Color";
        String incomeStr = "Income";
        String taxStr = "Tax";
        String profitStr = "Profit";

        String top = String.format(indentsTop, idStr, typeStr,
                modelNameStr, numberStr, weightStr, yearStr, mileageStr,
                colorStr, incomeStr, taxStr, profitStr);

        System.out.println(top);
        for (Vehicle vehicle : loadVehicles()) {
            int id = vehicle.getId();
            String type = vehicle.getType().getName();
            String modelName = vehicle.getModelName();
            String number = vehicle.getRegistrationNumber();
            int weight = vehicle.getWeight();
            int year = vehicle.getManufactureYear();
            int mileage = vehicle.getMileage();
            String color = vehicle.getColor().toString();
            double income = vehicle.getTotalIncome();
            double tax = vehicle.getCalcTaxPerMonth();
            double profit = vehicle.getTotalProfit();

            String line = String.format(indentsLine, id, type,
                    modelName, number, weight, year, mileage,
                    color, income, tax, profit);

            System.out.println(line);
        }

        double total = countTotal();
        String bottom = String.format(indentsBottom, "Total income:", total);
        System.out.println(bottom);
    }

    private List<String> readFile(String inFile) {
        List<String> csvStrings = new ArrayList<>();
        File file = new File(inFile);

        try (Scanner in = new Scanner(file)) {
            while (in.hasNext()) {
                csvStrings.add(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return csvStrings;
    }

    private String[] parseLine(String line) {
        Pattern pattern = Pattern.compile("(?!\\B\"[^\"]*),(?![^\"]*\"\\B)");

        if (line.indexOf('"') < 0) {
            return line.split(",");
        }

        String[] params = pattern.split(line);
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].replaceAll("\"", "").replaceAll(",", ".");
        }
        return params;
    }

    private VehicleType getTypeById(int typeId) {
        try {
            for (VehicleType type : loadTypes()) {
                if (type.getId() == typeId) {
                    return type;
                }
            }
            throw new NotFindTypeById("Not find type by id: " + typeId);
        } catch (NotFindTypeById e) {
            e.printStackTrace();
        }
        return null;
    }

    private Vehicle getVehicleById(int id) {
        for (Vehicle vehicle : loadVehicles()) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        throw new NoSuchElementException();
    }

    private Startable createEngine(String[] params, int order) {
        String engineStr = params[order];

        switch (engineStr) {
            case "Electrical":
                double batterySize = Double.parseDouble(params[order + 1]);
                double consumption = Double.parseDouble(params[order + 2]);

                return new ElectricalEngine(batterySize, consumption);
            case "Diesel":
                double engineCapacity = Double.parseDouble(params[order + 1]);
                double fuelConsumptionPer100 = Double.parseDouble(params[order + 2]);
                double fuelTankCapacity = Double.parseDouble(params[order + 3]);

                return new DieselEngine(engineCapacity, fuelConsumptionPer100, fuelTankCapacity);

            case "Gasoline":

                engineCapacity = Double.parseDouble(params[order + 1]);
                fuelConsumptionPer100 = Double.parseDouble(params[order + 2]);
                fuelTankCapacity = Double.parseDouble(params[order + 3]);

                return new GasolineEngine(engineCapacity, fuelConsumptionPer100, fuelTankCapacity);

            default:
                throw new IllegalArgumentException("Name of engine: " + engineStr);

        }
    }

    private double countTotal() {
        double sum = 0.0d;

        for (Vehicle vehicle : loadVehicles()) {
            sum += vehicle.getTotalProfit();
        }

        return sum;
    }
}
