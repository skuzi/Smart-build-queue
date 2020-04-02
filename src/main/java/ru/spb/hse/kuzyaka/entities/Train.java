package ru.spb.hse.kuzyaka.entities;

public class Train {
    private int id;
    private double arrivalTime;
    private double dischargeTime;
    private double departureTime;
    private double cost;

    public Train(int id, double arrivalTime, double dischargeTime, double cost) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.dischargeTime = dischargeTime;
        this.cost = cost;
        this.departureTime = arrivalTime + dischargeTime;
    }

    public int getId() {
        return id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getDischargeTime() {
        return dischargeTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("Train %d: arrives at %.2f, departs at %.2f, profit gain is %.2f",
                getId(),
                getArrivalTime(),
                getDepartureTime(),
                getCost());
    }
}
