package toiletsimulator;

import toiletsimulator.interfaces.JobInterface;

import java.time.Duration;

public class Statistics {
    private static final Object locker = new Object();
    private static int jobs = 0;
    private static int starvedJobs = 0;
    private static Duration totalWaitingTime = Duration.ZERO;

    public static void reset() {
        synchronized (locker) {
            jobs = 0;
            starvedJobs = 0;
            totalWaitingTime = Duration.ZERO;
        }
    }

    public static void countJob(JobInterface job) {
        synchronized (locker) {
            jobs++;
            if (job.getProcessedDate().isAfter(job.getDueDate()) ) {
                starvedJobs++;
            }
            totalWaitingTime = totalWaitingTime.plus( job.getWaitingTime() );
        }
    }

    public static void display() {
        synchronized (locker) {
            System.out.println();
            System.out.println("Statistics:");
            System.out.println("---------");
            System.out.println("Jobs:                      " + jobs);
            if( jobs>0 ) {
                System.out.println("Starved Jobs:              " + starvedJobs);
                System.out.println("Starvation Ratio:          " + starvedJobs / ((double) jobs));
                System.out.println("Total Waiting Time:        " + totalWaitingTime);
                System.out.println("Mean Waiting Time:         " + Duration.ofMillis(totalWaitingTime.toMillis() / jobs));
            }
        }
    }
}
