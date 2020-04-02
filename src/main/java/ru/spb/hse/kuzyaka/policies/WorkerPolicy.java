package ru.spb.hse.kuzyaka.policies;

import ru.spb.hse.kuzyaka.entities.Schedule;
import ru.spb.hse.kuzyaka.entities.Train;

import java.util.List;

public interface WorkerPolicy {
    Schedule findBestSchedule(List<Train> trains);
}
