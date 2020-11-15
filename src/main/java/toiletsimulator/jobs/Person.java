package toiletsimulator.jobs;

import lombok.Getter;
import lombok.SneakyThrows;
import toiletsimulator.Parameters;
import toiletsimulator.Statistics;
import toiletsimulator.interfaces.JobInterface;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Random;

public class Person implements JobInterface, Comparator<Person>, Comparable<Person> {
    @Getter private final String id;
    @Getter private final LocalDateTime creationDate;
    @Getter private final LocalDateTime dueDate;
    @Getter private final Duration processingTime;
    @Getter private Duration waitingTime;
    @Getter private LocalDateTime processedDate;

    public Person(Random random, String id) {
        this.id = id;
        this.creationDate = LocalDateTime.now();
        this.waitingTime = ChronoUnit.FOREVER.getDuration();
        this.processedDate = LocalDateTime.MAX;

        // ATTENTION: Usually the times calculated here need to be evenly distributed (NormalDistribution) to get compareable
        // results for different runs. This is known, but was omitted, as this sample should just show the handling of
        // threads and concurrent collections.

        // calculate due date
        Duration dueTime = Duration.ofMillis(random.nextInt(Parameters.MAX_RANDOM_VALUE-Parameters.MIN_RANDOM_VALUE) + Parameters.MIN_RANDOM_VALUE);
        this.dueDate = creationDate.plus(dueTime);

        // calculate required processing time
        this.processingTime = Duration.ofMillis(random.nextInt(Parameters.MAX_RANDOM_VALUE-Parameters.MIN_RANDOM_VALUE) + Parameters.MIN_RANDOM_VALUE);
    }

    @Override
    public void process() {
        waitingTime = Duration.between(creationDate, LocalDateTime.now());

        if ( Parameters.DISPLAY_JOB_PROCESSING ) {
            System.out.println(id + ": Processing ...   ");
        }

        try {
            Thread.sleep(processingTime.toMillis());    // simulate processing
        } catch (InterruptedException e) {
            // IGNORED
        }
        processedDate = LocalDateTime.now();

        if (Parameters.DISPLAY_JOB_PROCESSING) {
            if (processedDate.isBefore( dueDate )) {
                System.out.println(id + ": Ahhhhhhh, much better ...");
            } else {
                System.out.println(id + ": OOOh no, too late .......");
            }
        }

        Statistics.countJob(this);
    }

    @Override
    public int compare(Person o1, Person o2) {
        return 0;
    }

    @Override
    public int compareTo(Person other) {
        if (other!=null)
            return this.id.compareTo(other.getId());
        else
            return 1;
    }
}
