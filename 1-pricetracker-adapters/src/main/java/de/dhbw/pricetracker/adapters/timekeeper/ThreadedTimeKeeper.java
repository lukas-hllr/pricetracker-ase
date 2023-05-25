package de.dhbw.pricetracker.adapters.timekeeper;

import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadedTimeKeeper implements TimeKeeper
{
    private ScheduledExecutorService executor;
    private Runnable runnable;
    private ScheduledFuture task;
    public ThreadedTimeKeeper(){
        executor = Executors.newSingleThreadScheduledExecutor();
        runnable = new Runnable() {
            public void run() {
                // Invoke method(s) to do the work
                //doPeriodicWork();
            }
        };

        task = executor.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
    }
    @Override
    public void setIntervall(int minutes) {
        task.cancel(false);
        task = executor.scheduleAtFixedRate(runnable, 0, minutes, TimeUnit.MINUTES);
    }
}
