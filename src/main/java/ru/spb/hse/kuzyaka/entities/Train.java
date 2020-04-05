package ru.spb.hse.kuzyaka.entities;

/** Represents train */
public class Train {
    private int id;
    private double arrivalTime;
    private double dischargeTime;
    private double departureTime;
    private double cost;

    /**
     * Constructs a train
     * @param id identification number of tree, it may not match the train number in order of arrival
     * @param arrivalTime arrival time of a train
     * @param dischargeTime time needed to discharge this train
     * @param cost profit gain for discharging this train
     */
    public Train(int id, double arrivalTime, double dischargeTime, double cost) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.dischargeTime = dischargeTime;
        this.cost = cost;
        this.departureTime = arrivalTime + dischargeTime;
    }

    /**
     * Returns identification number of this train
     * @return identification number
     */
    public int getId() {
        return id;
    }

    /**
     * Returns arrival time of this train
     * @return arrival time
     */
    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns discharge time of this train
     * @return discharge time
     */
    public double getDischargeTime() {
        return dischargeTime;
    }

    /**
     * Returns time when this train would be discharged if it was being discharged since its arrival
     * @return departure time
     */
    public double getDepartureTime() {
        return departureTime;
    }

    /**
     * Returns profit gain of this train
     * @return profit
     */
    public double getCost() {
        return cost;
    }

    /**
     * Returns string representation of this train
     * @return string representation of this train
     */
    @Override
    public String toString() {
        return String.format("Train %d: arrives at %.2f, departs at %.2f, profit gain is %.2f",
                getId(),
                getArrivalTime(),
                getDepartureTime(),
                getCost());
    }
}
