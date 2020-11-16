package toiletsimulator.queues;

import toiletsimulator.Parameters;
import toiletsimulator.interfaces.JobInterface;

import java.time.LocalDateTime;
import java.util.Comparator;
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
                queue.sort( new Comparator<JobInterface>() {
                    @Override
                    public int compare(JobInterface a, JobInterface b) {
                        if( a==null || b==null )
                            return 0;
                        if( !a.getDueDate().equals(b.getDueDate()) )
                            return a.getDueDate().compareTo(b.getDueDate());
                        else
                            return a.getProcessingTime().compareTo(b.getProcessingTime());
                    }
                });
                return queue.remove(0);
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
