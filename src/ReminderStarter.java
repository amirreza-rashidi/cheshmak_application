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

    private static long nextReminderTime = 0; // زمان دقیق reminder بعدی
    private static final long INTERVAL = 1 * 60 * 1000; // 1 دقیقه برای تست - اصل 20 دقیقه

    public static void start() {
        // اگر قبلاً در حال اجرا است، دوباره شروع نکنیم
        if (isRunning) {
            return;
        }

        // پاک کردن timer قبلی اگر وجود دارد
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer("ReminderTimer");
        isRunning = true;

        long now = System.currentTimeMillis();

        // اگر اولین بار است، پیام شروع رو نشون بده و زمان بعدی رو set کن
        if (firstTime) {
            try {
                SwingUtilities.invokeLater(() -> ShortReminder.showFirstTime());
                firstTime = false;
                shortReminderCount = 0;
                // اولین reminder بعدی دقیقاً بعد از INTERVAL باشد
                nextReminderTime = now + INTERVAL;
                System.out.println("اولین reminder (شروع کنیم) نمایش داده شد.");
                System.out.println("Reminder بعدی در: " + new java.util.Date(nextReminderTime));
            } catch (Exception e) {
                System.err.println("خطا در نمایش reminder اولیه: " + e.getMessage());
            }
        } else {
            // اگر restart میکنیم، زمان بعدی رو از الان محاسبه کن
            nextReminderTime = now + INTERVAL;
            System.out.println("Restart شد. Reminder بعدی در: " + new java.util.Date(nextReminderTime));
        }

        // بررسی زمان واقعی هر 5 ثانیه
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

                        // زمان reminder بعدی رو دقیقاً INTERVAL بعد از زمان قبلی set کن
                        // نه زمان فعلی!
                        nextReminderTime += INTERVAL;
                        System.out.println("Reminder بعدی در: " + new java.util.Date(nextReminderTime));
                    }
                } catch (Exception e) {
                    System.err.println("خطا در timer task: " + e.getMessage());
                }
            }
        }, 5 * 1000, 5 * 1000); // هر 5 ثانیه بررسی کن

        System.out.println("ReminderStarter شروع شد. FirstTime: " + firstTime + ", Count: " + shortReminderCount);
    }

    public static void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isRunning = false;

        // وقتی متوقف میشه، برای شروع بعدی باید دوباره پیام اولیه رو نشون بده
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

    // Method برای نمایش زمان reminder بعدی
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