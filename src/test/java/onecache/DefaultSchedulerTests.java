package onecache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRunnable implements Runnable {
    public int iterations = 0;

    @Override
    public void run() {
        this.iterations++;
    }
}

class DefaultSchedulerTests {

    @Test
    void schedule() throws InterruptedException {

        Scheduler scheduler = new DefaultScheduler();
        TestRunnable t = new TestRunnable();

        // Schedule some work
        scheduler.RunOnceAfter(t, 15);
        Runnable cancel = scheduler.RunOnceAfter(t, 30);
        scheduler.RunOnceAfter(t, 35);
        assertEquals(0, t.iterations);

        // Sleep to ensure it has run
        Thread.sleep(20);
        assertEquals(1, t.iterations);

        // Cancel the second call
        cancel.run();

        // Sleep to allow the remaining (third) call to complete
        Thread.sleep(20);
        assertEquals(2, t.iterations);
    }
}
