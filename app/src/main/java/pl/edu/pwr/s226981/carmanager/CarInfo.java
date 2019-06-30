package pl.edu.pwr.s226981.carmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarInfo implements Serializable
{
    private String brand, model, color, plate;
    private List<RefuelInfo> refuelInfo;
    private List<ExpenseInfo> expenseInfo;

    public CarInfo(String brand, String model, String color, String plate) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.plate = plate;
        refuelInfo = new ArrayList<>();
    }

    public List<RefuelInfo> getRefuelInfo() {
        return refuelInfo;
    }

    public void setRefuelInfo(List<RefuelInfo> refuelInfo) {
        this.refuelInfo = refuelInfo;
    }


    @Override
    public String toString()
    {
        return brand + " " + model + " " + color + " " + plate;
    }
}

