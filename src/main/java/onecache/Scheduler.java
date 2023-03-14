package onecache;

public interface Scheduler {

    /**
     * RunOnceAfter work to be run one time asynchronously after a certain amount of time.
     *
     * @param func Function to call
     * @param delayMS How long to wait before running the function
     * @return  A cancellation function that prevents func from running
     */
    Runnable RunOnceAfter(Runnable func, long delayMS);
}
