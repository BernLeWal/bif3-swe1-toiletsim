package toiletsimulator.interfaces;

import java.time.Duration;
import java.time.LocalDateTime;

public interface JobInterface {
    String getId();     // unique ID
    LocalDateTime getCreationDate();   // time stamp when the job was created
    LocalDateTime getDueDate();        // time stamp when the job has to be completed
    Duration getProcessingTime();      // time span how long the job processing takes
    LocalDateTime getProcessedDate();  // time stamp when the job was completed
    Duration getWaitingTime();         // time span how long the job had to wait

    void process();                 // processing method invoked by the consumer
}
