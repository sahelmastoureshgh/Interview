package onecache;

import java.util.Timer;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultScheduler implements Scheduler {
    private ScheduledExecutorService executor;

    public DefaultScheduler() {
        // Use a thead pool of size ten for now since it should be sufficient.
        this.executor = Executors.newScheduledThreadPool(10);
    }

    @Override
    public Runnable RunOnceAfter(Runnable func, long delayMS) {
        Future future = this.executor.schedule(func, delayMS, TimeUnit.MILLISECONDS);
        return () -> future.cancel(true);
    }
}
