package com.Autopark.repairAuto;

import com.Autopark.parser.ParserBreakingsFromFile;
import com.Autopark.Auto.Vehicle;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;

import java.util.*;

import static com.Autopark.parser.ParserBreakingsFromFile.*;

public class MechanicService implements Fixer {
    public static final String[] details = {"Фильтр", "Втулка", "Вал", "Ось",
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
        long counter = 0;
        counter = parser.counter(vehicle);
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
