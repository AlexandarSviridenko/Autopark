package com.Autopark.parser;

//import com.Autopark.Auto.Vehicle;
import com.Autopark.entity.Vehicles;

import java.util.List;
import java.util.Map;

public interface ParserBreakingInterface {
//    void writeInFileRemainingBreaking(Vehicles vehicle, List<String> result);
//    //void writeInFileRemainingBreaking(long id, String defect, int amount);
//
//    void loadOrders(Vehicles vehicle, Map<String, Integer> result);

    void writeOrder(long id, String defect, int amount);
    int findRow(long id);
    void deleteOrderString(int rowNumber);
}
