package toiletsimulator;

import toiletsimulator.consumer.Toilet;
import toiletsimulator.producer.PeopleGenerator;
import toiletsimulator.queues.BetterQueue;
import toiletsimulator.queues.ConcurrentToiletQueue;
import toiletsimulator.queues.SimpleQueue;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SimpleQueue queue = new SimpleQueue();
        //BetterQueue queue = new BetterQueue();
        //ConcurrentToiletQueue queue = new ConcurrentToiletQueue();

        int randomSeed = new Random().nextInt();
        Random random = new Random(randomSeed);

        PeopleGenerator[] producers = new PeopleGenerator[Parameters.PRODUCERS];
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new PeopleGenerator("People Generator " + i, queue, random);
        }

        Toilet[] consumers = new Toilet[Parameters.CONSUMERS];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Toilet("Toilet " + i, queue);
        }

        Statistics.reset();
        for (PeopleGenerator producer : producers) {
            producer.produce();
        }
        for (Toilet consumer : consumers) {
            consumer.consume();
        }

        for (Toilet consumer : consumers) {
            consumer.join();
        }

        Statistics.display();

        System.out.println();
    }
}
