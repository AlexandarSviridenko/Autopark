package com.Autopark.repairAuto;

import com.Autopark.entity.Vehicles;
import com.Autopark.parser.ParserBreakingInterface;
import com.Autopark.parser.ParserBreakingsFromFile;
//import com.Autopark.Auto.Vehicle;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.service.OrdersService;

import java.util.*;

import static com.Autopark.parser.ParserBreakingsFromFile.*;

public class MechanicService implements Fixer {
    public static final String[] details = {"Фильтр", "Втулка", "Вал", "Ось",
            "Свеча", "Масло", "ГРМ", "ШРУС"};

    @Autowired
    private ParserBreakingInterface parser;

    public MechanicService() {
    }

    public ParserBreakingInterface getParser() {
        return parser;
    }

    public void setParser(ParserBreakingInterface parser) {
        this.parser = parser;
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        Map<String, Integer> result = new HashMap<>();
       // parser.loadOrders(vehicle,result);/* генерирует поломку и записывает в файл*/
       //сгенерировать и загрузить в БД

//        OrdersService ordersService = new OrdersService();
//        ordersService.save(result);

//        parser.createRandomBrokenDetailsAndReturnSum(vehicle, result);
//        //vehicle.setSumOfBrokenParts(sum);
//        result = parser.writeInFileBrokenDetails(vehicle, result);
        return result;
    }

    @Override
    public void repair(Vehicles vehicle) {
        List<String> result = new ArrayList<>();
        long counter = 0;
//        counter = parser.counter(vehicle);
//        parser.counterMoreNull(vehicle, counter);
//        parser.writeInFileRemainingBreaking(vehicle, result);
//        parser.counterEqualNull(vehicle, counter);
    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        if (/*vehicle.getBroken()*/ true) {
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
