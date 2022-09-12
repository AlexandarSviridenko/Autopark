//package com.Autopark.Comparator;
//
//import com.Autopark.Auto.Vehicle;
//
//import java.util.Comparator;
//
//public class ComparatorByDefectCount implements Comparator<Vehicle> {
//
//    @Override
//    public int compare(Vehicle o1, Vehicle o2) {
//        return Integer.compare(o1.getSumOfBrokenParts(), o2.getSumOfBrokenParts());
//    }
//
//    @Override
//    public Comparator<Vehicle> reversed() {
//        return Comparator.super.reversed();
//    }
//}
