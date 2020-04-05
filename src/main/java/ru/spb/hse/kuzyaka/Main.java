package ru.spb.hse.kuzyaka;

import ru.spb.hse.kuzyaka.entities.Schedule;
import ru.spb.hse.kuzyaka.entities.Train;
import ru.spb.hse.kuzyaka.policies.OptimalPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            List<Train> trains = new ArrayList<>();
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                int id = sc.nextInt();
                double arrivalTime = sc.nextDouble();
                double dischargeTime = sc.nextDouble();
                double cost = sc.nextDouble();
                trains.add(new Train(id, arrivalTime, dischargeTime, cost));
            }

            Schedule bestSchedule = new OptimalPolicy().findBestSchedule(trains);

            System.out.println(String.format("Maximal profit is %.2f", bestSchedule.getProfit()));
            for (Train train : bestSchedule.getTrainsInSchedule()) {
                System.out.println(train.toString());
            }
        }
    }
}
