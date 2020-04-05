package ru.spb.hse.kuzyaka.policies;

import ru.spb.hse.kuzyaka.entities.Schedule;
import ru.spb.hse.kuzyaka.entities.Train;

import java.util.List;

/** Interface representing policy of one worker discharging trains */
public interface WorkerPolicy {
    /**
     * Finds best schedule according to policy.
     * @param trains list of trains to select a schedule from
     * @return best schedule according to policy
     */
    Schedule findBestSchedule(List<Train> trains);
}
