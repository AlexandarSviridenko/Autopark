package com.Autopark.parser;

import com.Autopark.Auto.Vehicle;

import java.util.List;
import java.util.Map;

public interface ParserBreakingInterface {
    void writeInFileRemainingBreaking(Vehicle vehicle, List<String> result);

    void loadOrders(Vehicle vehicle, Map<String, Integer> result);
}
