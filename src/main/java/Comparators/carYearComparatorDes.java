package Comparators;

import DTOs.CarClass;

import java.util.Comparator;

public class carYearComparatorDes implements Comparator<CarClass> {
    @Override
    public int compare(CarClass c1, CarClass c2) {
        int a = Integer.compare(c1.getProduction_year(), c2.getProduction_year());
        if (a == -1) return 1;
        else if (a == 1) return -1;
        else return 0;
    }
}
