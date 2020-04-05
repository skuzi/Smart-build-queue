package ru.spb.hse.kuzyaka.entities;

import java.util.List;

/** Class representing answer,
 * contains profit gain and trains that need to be discharged in order to achieve such profit
 */
public class Schedule {
    private double profit;
    private List<Train> trainsInSchedule;

    /**
     * Constructs a Schedule instance
     * @param profit cumulative profit of schedule
     * @param trainsInSchedule list of discharged trains, achieving such profit
     */
    public Schedule(double profit, List<Train> trainsInSchedule) {
        this.profit = profit;
        this.trainsInSchedule = trainsInSchedule;
    }

    /**
     * Returns profit
     * @return profit
     */
    public double getProfit() {
        return profit;
    }

    /**
     * Returns list of trains in schedule
     * @return list of trains in schedule
     */
    public List<Train> getTrainsInSchedule() {
        return trainsInSchedule;
    }
}
