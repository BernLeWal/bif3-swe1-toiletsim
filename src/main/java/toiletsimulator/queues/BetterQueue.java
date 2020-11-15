package toiletsimulator.queues;

import toiletsimulator.Parameters;
import toiletsimulator.interfaces.JobInterface;

import java.util.concurrent.Semaphore;

public class BetterQueue extends ToiletQueue {
    private Semaphore sem = new Semaphore(Parameters.PRODUCERS * Parameters.JOBS_PER_PRODUCER );

    @Override
    public void enqueue(JobInterface job) {
        synchronized (queue) {
            queue.add(job);
        }
        sem.release();
    }

    @Override
    public JobInterface tryDequeue() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            // IGNORED
        }

        synchronized (queue) {
            if (queue.size()>0) {
                throw new UnsupportedOperationException();  // TODO !!!!! (morgen ;-) )
            } else {
                return null;
            }
        }
    }

    @Override
    public void completeAdding() {
        super.completeAdding();

        if (producersCompleted == Parameters.PRODUCERS) {
            sem.release(Parameters.CONSUMERS-1);
        }
    }
}
