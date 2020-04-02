package ru.spb.hse.kuzyaka.policies;

import ru.spb.hse.kuzyaka.entities.Schedule;
import ru.spb.hse.kuzyaka.utils.Pair;
import ru.spb.hse.kuzyaka.entities.Train;

import java.util.*;

public class OptimalPolicy implements WorkerPolicy {
    private List<Pair<Event, Double>> events;
    private Map<Integer, Integer> endToBeginIndexMap;

    @Override
    public Schedule findBestSchedule(List<Train> trains) {
        createEvents(trains);
        endToBeginIndexMap = new HashMap<>();
        Map<Integer, Integer> beginToIndexMap = new HashMap<>();
        Collections.sort(events);
        for (int i = 0; i < events.size(); i++) {
            Pair<Event, Double> event = events.get(i);
            if (event.getX().isStart()) {
                beginToIndexMap.put(event.getX().getIndex(), i);
            } else {
                endToBeginIndexMap.put(i, beginToIndexMap.get(event.getX().getIndex()));
            }
        }

        double[] dp = new double[events.size()];

        for (int i = 1; i < events.size(); i++) {
            Pair<Event, Double> event = events.get(i);
            if (event.getX().isEnd()) {
                dp[i] = Math.max(dp[endToBeginIndexMap.get(i)] + event.getY(), dp[i - 1]);
            } else {
                dp[i] = dp[i - 1];
            }
        }

        int last = events.size() - 1;
        List<Train> dischargedTrains = new ArrayList<>();

        while (last > 0) {
            Pair<Event, Double> event = events.get(last);
            if (event.getX().isEnd()) {
                if (dp[last] == dp[endToBeginIndexMap.get(last)] + event.getY()) {
                    dischargedTrains.add(trains.get(event.getX().getIndex() - 1));
                    last = endToBeginIndexMap.get(last);
                } else {
                    last--;
                }
            } else {
                last--;
            }
        }

        Collections.reverse(dischargedTrains);
        return new Schedule(dp[events.size() - 1], dischargedTrains);
    }

    private void createEvents(List<Train> trains) {
        events = new ArrayList<>();
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            Event arrivalEvent = new Event(train.getArrivalTime(), i + 1, true);
            Event departureEvent = new Event(train.getDepartureTime(), i + 1, false);

            Pair<Event, Double> arrival = new Pair<>(arrivalEvent, train.getCost());
            Pair<Event, Double> departure = new Pair<>(departureEvent, train.getCost());

            events.add(arrival);
            events.add(departure);
        }
    }


    private static class Event implements Comparable<Event> {
        private double time;
        private int index;
        private boolean isStart;

        public Event(double time, int index, boolean isStart) {
            this.time = time;
            this.index = index;
            this.isStart = isStart;
            if (!isStart) {
                this.index = -this.index;
            }
        }

        public double getTime() {
            return time;
        }

        public int getIndex() {
            return Math.abs(index);
        }

        public boolean isStart() {
            return isStart;
        }

        public boolean isEnd() {
            return !isStart();
        }

        @Override
        public int compareTo(Event o) {
            if (time == o.time) {
                return index - o.index;
            }
            return (int)time - (int)o.time;
        }
    }
}
