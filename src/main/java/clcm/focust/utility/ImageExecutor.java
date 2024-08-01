package clcm.focust.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImageExecutor {

    private final ExecutorService executorService;

    public ImageExecutor() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void submit(Runnable runnable) {
        executorService.submit(runnable);
    }

    public void shutdown() {
        executorService.shutdown();
    }







}
