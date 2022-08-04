package com.Autopark;

import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static com.Autopark.ParserBreakingsFromFile.*;

public class MechanicService implements Fixer {
    static final String[] details = {"Фильтр", "Втулка", "Вал", "Ось",
            "Свеча", "Масло", "ГРМ", "ШРУС"};

    @Autowired
    private ParserBreakingsFromFile parser;

    public MechanicService() {
    }

    public ParserBreakingsFromFile getParser() {
        return parser;
    }

    public void setParser(ParserBreakingsFromFile parser) {
        this.parser = parser;
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> result = new HashMap<>();
        int sum = parser.createRandomBrokenDetailsAndReturnSum(vehicle, result);
        vehicle.setSumOfBrokenParts(sum);
        result = parser.writeInFileBrokenDetails(vehicle, result);
        return result;
    }

    @Override
    public void repair(Vehicle vehicle) {
        List<String> result = new ArrayList<>();
        int counter = 0;
        counter = counter(vehicle);
        parser.counterMoreNull(vehicle, counter);
        parser.writeInFileRemainingBreaking(vehicle, result);
        parser.counterEqualNull(vehicle, counter);
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        if (vehicle.getBroken()) {
            return true;
        } else {
            return false;
        }
    }

    @InitMethod
    public void init() {
        parser = new ParserBreakingsFromFile();
    }
}
