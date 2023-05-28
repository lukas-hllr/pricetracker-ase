package de.dhbw.pricetracker.application.timekeeper;

import de.dhbw.pricetracker.application.ui.UIEventListener;

public interface TimeKeeper
{

    public void setUIEventListener(UIEventListener listener);

    public void start();

    public void stop();

    public void setIntervall(int minutes);

}
