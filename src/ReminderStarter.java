package src;

import src.ShortReminder;
import src.LongReminder;

import javax.swing.SwingUtilities;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderStarter {

    private static int shortReminderCount = 0;
    private static Timer timer;
    private static boolean firstTime = true;
    private static boolean isRunning = false;

    private static long nextReminderTime = 0;
    private static final long INTERVAL = 1 * 60 * 1000; // 1 دقیقه برای تست

    public static void start() {

        if (isRunning) {
            return;
        }


        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer("ReminderTimer");
        isRunning = true;

        long now = System.currentTimeMillis();


        if (firstTime) {
            try {
                SwingUtilities.invokeLater(() -> ShortReminder.showFirstTime());
                firstTime = false;
                shortReminderCount = 0;

                nextReminderTime = now + INTERVAL;
                System.out.println("اولین reminder (شروع کنیم) نمایش داده شد.");
                System.out.println("Reminder بعدی در: " + new java.util.Date(nextReminderTime));
            } catch (Exception e) {
                System.err.println("خطا در نمایش reminder اولیه: " + e.getMessage());
            }
        } else {

            nextReminderTime = now + INTERVAL;
            System.out.println("Restart شد. Reminder بعدی در: " + new java.util.Date(nextReminderTime));
        }


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    long now = System.currentTimeMillis();
                    long timeUntilNext = nextReminderTime - now;

                    // Debug info
                    System.out.println("زمان باقی‌مانده: " + (timeUntilNext / 1000) + " ثانیه");

                    if (now >= nextReminderTime) {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                if (shortReminderCount < 2) {
                                    ShortReminder.showReminderWithTimer();
                                    shortReminderCount++;
                                    System.out.println("Short reminder نمایش داده شد. تعداد: " + shortReminderCount);
                                } else {
                                    LongReminder.show();
                                    shortReminderCount = 0;
                                    System.out.println("Long reminder نمایش داده شد. Counter ریست شد.");
                                }
                            } catch (Exception e) {
                                System.err.println("خطا در نمایش reminder: " + e.getMessage());
                            }
                        });



                        nextReminderTime += INTERVAL;
                        System.out.println("Reminder بعدی در: " + new java.util.Date(nextReminderTime));
                    }
                } catch (Exception e) {
                    System.err.println("خطا در timer task: " + e.getMessage());
                }
            }
        }, 5 * 1000, 5 * 1000);

        System.out.println("ReminderStarter شروع شد. FirstTime: " + firstTime + ", Count: " + shortReminderCount);
    }

    public static void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isRunning = false;


        firstTime = true;
        shortReminderCount = 0;
        nextReminderTime = 0;

        System.out.println("ReminderStarter متوقف شد. برای شروع بعدی پیام اولیه نمایش داده خواهد شد.");
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static void reset() {
        stop();
        shortReminderCount = 0;
        firstTime = true;
        nextReminderTime = 0;
        System.out.println("ReminderStarter کاملاً ریست شد.");
    }

    public static String getStatus() {
        if (!isRunning) {
            return "متوقف";
        }

        long now = System.currentTimeMillis();
        long timeUntilNext = nextReminderTime - now;

        if (timeUntilNext <= 0) {
            return "آماده برای reminder بعدی";
        }

        int minutesLeft = (int) (timeUntilNext / 60000);
        int secondsLeft = (int) ((timeUntilNext % 60000) / 1000);

        return String.format("فعال - %d:%02d تا reminder بعدی (Count: %d)", minutesLeft, secondsLeft, shortReminderCount);
    }

    public static void debugInfo() {
        System.out.println("=== Debug Info ===");
        System.out.println("FirstTime: " + firstTime);
        System.out.println("ShortReminderCount: " + shortReminderCount);
        System.out.println("IsRunning: " + isRunning);
        System.out.println("NextReminderTime: " + new java.util.Date(nextReminderTime));
        System.out.println("CurrentTime: " + new java.util.Date(System.currentTimeMillis()));
        long timeUntilNext = nextReminderTime - System.currentTimeMillis();
        System.out.println("Time Until Next: " + (timeUntilNext / 1000) + " seconds");
        System.out.println("Interval: " + (INTERVAL / 1000) + " seconds");
        System.out.println("==================");
    }


    public static void showNextReminderTime() {
        if (!isRunning) {
            System.out.println("Timer متوقف است");
            return;
        }

        long now = System.currentTimeMillis();
        long timeUntilNext = nextReminderTime - now;

        System.out.println("Reminder بعدی در: " + new java.util.Date(nextReminderTime));
        System.out.println("باقی مانده: " + (timeUntilNext/1000) + " ثانیه");
    }
}