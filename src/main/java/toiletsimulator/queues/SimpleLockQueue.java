package toiletsimulator.queues;

import toiletsimulator.interfaces.JobInterface;

public class SimpleLockQueue extends ToiletQueue {

    @Override
    public void enqueue(JobInterface job) {
        synchronized (queue) {
            queue.add(job);
        }
    }

    @Override
    public JobInterface tryDequeue() {
        synchronized (queue) {
            if (queue.size()>0) {
                return queue.remove(0);
            } else {
                return null;
            }
        }
    }
}
