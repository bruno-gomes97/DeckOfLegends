package utils;

public class Delay {
    public static void runWithDelay(Runnable action, int delay) {
        action.run();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
