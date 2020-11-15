package toiletsimulator.producer;

import lombok.Getter;
import toiletsimulator.Parameters;
import toiletsimulator.interfaces.ProducerInterface;
import toiletsimulator.interfaces.ToiletQueueInterface;
import toiletsimulator.jobs.Person;

import java.util.Random;

public class PeopleGenerator implements ProducerInterface, Runnable {

    private int idSeed;
    private final Random random;
    private final int randomThreadRun;

    @Getter private final String name;
    @Getter private final ToiletQueueInterface queue;

    public PeopleGenerator(String name, ToiletQueueInterface queue, Random random) {
        idSeed = 0;
        this.random = random;
        this.name = name;
        this.queue = queue;
        randomThreadRun = random.nextInt(Parameters.MAX_RANDOM_VALUE-Parameters.MIN_RANDOM_VALUE) + Parameters.MIN_RANDOM_VALUE;
    }

    @Override
    public void produce() {
        Thread thread = new Thread(this);
        thread.setName(name);
        thread.start();
    }

    @Override
    public void run() {
        idSeed = 0;
        for (int i=0; i < Parameters.JOBS_PER_PRODUCER; i++) {
            try {
                Thread.sleep(randomThreadRun);
            } catch (InterruptedException e) {
                // IGNORED
            }
            idSeed++;
            queue.enqueue(new Person(random, String.format("%s - Person %02d", name, idSeed)));
        }
        queue.completeAdding();
    }
}
