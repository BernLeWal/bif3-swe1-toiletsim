package toiletsimulator.interfaces;

public interface ToiletQueueInterface {
    int getCount();     // number of queued jobs
    void enqueue(JobInterface job); // enqueue a new job
    JobInterface tryDequeue();  // fetch next job
    void completeAdding();
    boolean isCompleted();
}
