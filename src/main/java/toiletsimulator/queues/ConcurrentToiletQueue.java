package toiletsimulator.queues;

import toiletsimulator.Parameters;
import toiletsimulator.interfaces.JobInterface;
import toiletsimulator.interfaces.ToiletQueueInterface;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentToiletQueue implements ToiletQueueInterface {

    private final ConcurrentLinkedQueue<JobInterface> concurrentQueue = new ConcurrentLinkedQueue<>();
    protected int producersCompleted;

    @Override
    public int getCount() {
        return concurrentQueue.size();
    }

    @Override
    public boolean isCompleted() {
        return (concurrentQueue.size()==0) && (producersCompleted== Parameters.PRODUCERS);
    }

    @Override
    public void enqueue(JobInterface job) {
        concurrentQueue.add(job);
    }

    @Override
    public JobInterface tryDequeue() {
        return concurrentQueue.poll();
    }

    @Override
    public synchronized void completeAdding() {
        producersCompleted++;
    }
}
