package toiletsimulator.queues;

import toiletsimulator.Parameters;
import toiletsimulator.interfaces.JobInterface;
import toiletsimulator.interfaces.ToiletQueueInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class ToiletQueue implements ToiletQueueInterface {

    protected final List<JobInterface> queue = new ArrayList<>();
    protected int producersCompleted;

    @Override
    public int getCount() {
        synchronized (queue) {
            return queue.size();
        }
    }

    @Override
    public synchronized void completeAdding() {
        producersCompleted++;
    }

    @Override
    public boolean isCompleted() {
        synchronized (queue) {
            return queue.size()==0 && producersCompleted== Parameters.PRODUCERS;
        }
    }
}
