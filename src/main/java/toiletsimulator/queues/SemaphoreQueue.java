package toiletsimulator.queues;

import toiletsimulator.interfaces.JobInterface;
import toiletsimulator.interfaces.ToiletQueueInterface;

import java.util.concurrent.Semaphore;

public class SemaphoreQueue extends ToiletQueue {

    private Semaphore semaphore = new Semaphore(1);

    @Override
    public void enqueue(JobInterface job) {
        try {
            semaphore.acquire();
            queue.add(job);
            semaphore.release();
        } catch (InterruptedException e) {
            // IGNORED
        }
    }

    @Override
    public JobInterface tryDequeue() {
        try {
            semaphore.acquire();
            if (queue.size()>0) {
                return queue.remove(0);
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            // IGNORED
            return null;
        } finally {
            semaphore.release();
        }
    }

}
