package de.dhbw.pricetracker.adapters.timekeeper;

import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UIEventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadedTimeKeeper implements TimeKeeper
{
    private ScheduledExecutorService executor;
    private Runnable runnable;
    private ScheduledFuture task;
    private int interval = 1;
    private UIEventListener listener;

    public ThreadedTimeKeeper()
    {
        executor = Executors.newSingleThreadScheduledExecutor();
        runnable = new Runnable()
        {
            public void run()
            {
                listener.onUpdatePriceEvent();
            }
        };
    }

    @Override
    public void setUIEventListener(UIEventListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void start()
    {
        task = executor.scheduleAtFixedRate(runnable, interval, interval, TimeUnit.MINUTES);
    }

    @Override
    public void stop()
    {
        task.cancel(false);
    }

    @Override
    public void setIntervall(int minutes)
    {
        stop();
        this.interval = minutes;
        start();
    }
}
