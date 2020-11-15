package toiletsimulator.consumer;

import lombok.Getter;
import toiletsimulator.interfaces.ConsumerInterface;
import toiletsimulator.interfaces.JobInterface;
import toiletsimulator.interfaces.ToiletQueueInterface;

public class Toilet implements ConsumerInterface, Runnable {
    @Getter private final String name;
    @Getter private final ToiletQueueInterface queue;

    private Thread thread;

    public Toilet(String name, ToiletQueueInterface queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void consume() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while( !queue.isCompleted() ) {
            JobInterface job = queue.tryDequeue();
            if ( job!=null ) {
                job.process();
            }
        }
    }

    @Override
    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            // IGNORED
        }
    }
}
