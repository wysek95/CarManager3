package pl.edu.pwr.s226981.carmanager;

import java.io.Serializable;
import java.util.Date;

public class RefuelInfo implements Serializable
{
    private Integer milage;
    private Float liters, cost;
    private Date refuelDate;

    public RefuelInfo(Integer milage, Float liters, Float cost, Date refuelDate) {
        this.milage = milage;
        this.liters = liters;
        this.cost = cost;
        this.refuelDate = refuelDate;
    }

    public Integer getMilage() {
        return milage;
    }
    public Float getLiters() {
        return liters;
    }

    public Float getCost() {
        return cost;
    }
}
