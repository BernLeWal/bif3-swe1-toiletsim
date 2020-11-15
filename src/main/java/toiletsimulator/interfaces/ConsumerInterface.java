package toiletsimulator.interfaces;

public interface ConsumerInterface {
    String getName();
    ToiletQueueInterface getQueue();
    void consume();
    void run();
    void join();
}
