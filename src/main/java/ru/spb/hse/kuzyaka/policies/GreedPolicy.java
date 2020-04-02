package ru.spb.hse.kuzyaka.policies;

import ru.spb.hse.kuzyaka.entities.Schedule;
import ru.spb.hse.kuzyaka.entities.Train;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class GreedPolicy implements WorkerPolicy {
    @Override
    public Schedule findBestSchedule(List<Train> trains) {
        List<Train> trainList = new ArrayList<>(trains);
        trainList.sort(Comparator.comparingInt(o -> (int) o.getArrivalTime()));

        Stack<Train> dischargedTrains = new Stack<>();
        double profit = 0;


        for (Train train : trainList) {
            if (dischargedTrains.isEmpty() || train.getArrivalTime() >= dischargedTrains.peek().getDepartureTime()) {
                dischargedTrains.add(train);
                profit += train.getCost();
            } else if (train.getCost() > dischargedTrains.peek().getCost()) {
                profit -= dischargedTrains.pop().getCost();
                dischargedTrains.add(train);
                profit += train.getCost();
            }
        }

        return new Schedule(profit, dischargedTrains);
    }
}
