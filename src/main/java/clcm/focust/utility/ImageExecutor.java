package clcm.focust.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImageExecutor {

    private final ExecutorService executorService;

    public ImageExecutor(int threads) {
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    public void submit(Runnable runnable) {
        executorService.submit(runnable);
    }

    public void shutdown() {
        executorService.shutdown();
    }







}
