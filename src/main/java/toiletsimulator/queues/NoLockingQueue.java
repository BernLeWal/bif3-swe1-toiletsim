package toiletsimulator.queues;

import toiletsimulator.interfaces.JobInterface;

public class NoLockingQueue extends ToiletQueue {

    @Override
    public void enqueue(JobInterface job) {
        queue.add(job);
    }

    @Override
    public JobInterface tryDequeue() {
        if (queue.size()>0) {
            return queue.remove(0);
        } else {
            return null;
        }
    }
}