package ru.spb.hse.kuzyaka;

import org.junit.jupiter.api.Test;
import ru.spb.hse.kuzyaka.entities.Schedule;
import ru.spb.hse.kuzyaka.entities.Train;
import ru.spb.hse.kuzyaka.policies.GreedyPolicy;
import ru.spb.hse.kuzyaka.policies.OptimalPolicy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    private static int RANDOM_TEST_SMALL_END = 8;
    private static int RANDOM_TEST_BIG_END = 16;

    private List<Train> trains;

    // no trains provided
    @Test
    void testEmptyList() throws IOException {
        trains = readTrainsFrom("empty");
        Schedule answer = solve(trains);
        assertEquals(0, answer.getProfit());
        assertTrue(answer.getTrainsInSchedule().isEmpty());
    }

    // only one train exists
    @Test
    void testDummy() throws IOException {
        trains = readTrainsFrom("dummy");
        Schedule answer = solve(trains);
        assertEquals(1, answer.getProfit());
        assertEquals(1, answer.getTrainsInSchedule().size());
    }

    // few trains with no time intervals interceptions
    @Test
    void testSimple1() throws IOException {
        trains = readTrainsFrom("simple1");
        Schedule answer = solve(trains);
        assertEquals(5, answer.getProfit());
        assertEquals(3, answer.getTrainsInSchedule().size());
        checkInterceptions(answer.getTrainsInSchedule());
    }

    // few trains with same time interval
    @Test
    void testSimple2() throws IOException {
        trains = readTrainsFrom("simple2");
        Schedule answer = solve(trains);
        assertEquals(5, answer.getProfit());
        assertEquals(1, answer.getTrainsInSchedule().size());
        checkInterceptions(answer.getTrainsInSchedule());
    }

    // few trains with overlapping intervals but the most expensive trains are better
    @Test
    void testSimple3() throws IOException {
        trains = readTrainsFrom("simple3");
        Schedule answer = solve(trains);
        assertEquals(600, answer.getProfit());
        assertEquals(2, answer.getTrainsInSchedule().size());
        checkInterceptions(answer.getTrainsInSchedule());
    }

    // few trains with overlapping intervals but the most expensive trains are worse
    @Test
    void testSimple4() throws IOException {
        trains = readTrainsFrom("simple4");
        Schedule answer = solve(trains);
        assertEquals(25, answer.getProfit());
        assertEquals(2, answer.getTrainsInSchedule().size());
        checkInterceptions(answer.getTrainsInSchedule());
    }

    // mostly the same as 4th test
    @Test
    void testSimple5() throws IOException {
        trains = readTrainsFrom("simple5");
        Schedule answer = solve(trains);
        assertEquals(50, answer.getProfit());
        assertEquals(4, answer.getTrainsInSchedule().size());
        checkInterceptions(answer.getTrainsInSchedule());
    }

    // intervals lay one within another
    @Test
    void testSimple6() throws IOException {
        trains = readTrainsFrom("simple6");
        Schedule answer = solve(trains);
        assertEquals(500, answer.getProfit());
        assertEquals(2, answer.getTrainsInSchedule().size());
        checkInterceptions(answer.getTrainsInSchedule());
    }

    // random not very big tests to proof this strategy is at least as optimal as greedy
    @Test
    void testRandomSmall() throws IOException {
        for (int i = 1; i <= RANDOM_TEST_SMALL_END; i++) {
            String resourceName = String.format("random%d", i);
            trains = readTrainsFrom(resourceName);
            Schedule answerOptimal = solve(trains);
            Schedule answerGreedy = new GreedyPolicy().findBestSchedule(trains);

            assertTrue(answerGreedy.getProfit() <= answerOptimal.getProfit());
        }
    }

    // random quite big tests
    @Test
    void testRandomBig() throws IOException {
        for (int i = RANDOM_TEST_SMALL_END + 1; i <= RANDOM_TEST_BIG_END; i++) {
            String resourceName = String.format("random%d", i);
            trains = readTrainsFrom(resourceName);
            Schedule answerOptimal = solve(trains);
            Schedule answerGreedy = new GreedyPolicy().findBestSchedule(trains);
            System.out.println(String.format("%f %f", answerGreedy.getProfit(), answerOptimal.getProfit()));

            assertTrue(answerGreedy.getProfit() <= answerOptimal.getProfit());
        }
    }


    private void checkInterceptions(List<Train> trainsInSchedule) {
        // to avoid traversal in O(N * N)
        List<Train> trains = new ArrayList<>(trainsInSchedule);

        for (int i = 0; i < trains.size(); i++) {
            for (int j = i + 1; j < trains.size(); j++) {
                Train first = trains.get(i);
                Train second = trains.get(j);
                // intervals intercept iff they can look like this: 1212, 2121, 1221, 2112
                assertFalse(first.getDepartureTime() > second.getArrivalTime() &&
                        first.getArrivalTime() < second.getDepartureTime() ||
                        second.getDepartureTime() > first.getArrivalTime() &&
                                second.getArrivalTime() < first.getDepartureTime());
            }
        }
    }

    private Schedule solve(List<Train> trains) {
        return new OptimalPolicy().findBestSchedule(trains);
    }

    private List<Train> readTrainsFrom(String resourceName) throws IOException {
        InputStream in = SolutionTest.class.getClassLoader().getResourceAsStream(resourceName);
        assert in != null;
        List<Train> trains = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            int n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                String[] line = reader.readLine().split(" ");
                int id = Integer.parseInt(line[0]);
                double arrivalTime = Double.parseDouble(line[1]);
                double dischargeTime = Double.parseDouble(line[2]);
                double cost = Double.parseDouble(line[3]);
                trains.add(new Train(id, arrivalTime, dischargeTime, cost));
            }
        }
        return trains;
    }
}
