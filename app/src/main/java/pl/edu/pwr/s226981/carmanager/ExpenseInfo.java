package pl.edu.pwr.s226981.carmanager;

import java.io.Serializable;
import java.util.Date;

public class ExpenseInfo implements Serializable
{
    private Integer milage;
    private Float cost;
    private Date expenseDate;

    public ExpenseInfo(Integer milage, Float cost, Date expenseDate) {
        this.milage = milage;
        this.cost = cost;
        this.expenseDate = expenseDate;
    }

    public Float getCost() {
        return cost;
    }
}
