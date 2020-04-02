package ru.spb.hse.kuzyaka.entities;

import java.util.List;

public class Schedule {
    private double profit;
    private List<Train> trainsInSchedule;

    public Schedule(double profit, List<Train> trainsInSchedule) {
        this.profit = profit;
        this.trainsInSchedule = trainsInSchedule;
    }

    public double getProfit() {
        return profit;
    }

    public List<Train> getTrainsInSchedule() {
        return trainsInSchedule;
    }
}
