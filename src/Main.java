import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import src.ReminderStarter;

public class Main extends JFrame {

    private JLabel clockLabel;
    private JLabel clockTextLabel;
    private JLabel workTimeLabel;
    private Timer workTimer;
    private Timer clockTimer;
    private int workSeconds = 0;
    private boolean isReminderActive = false;

    private static Main instance = null;

    public Main() {
        if (instance != null) {
            instance.setVisible(true);
            instance.setState(JFrame.NORMAL);
            instance.toFront();
            return;
        }
        instance = this;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("برنامه محافظ چشم");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        Color lavender = new Color(200, 180, 255);

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(lavender);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // ===== دکمه مینیمایز =====
        JButton minimizeButton = new JButton("min");
        minimizeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        minimizeButton.setForeground(Color.WHITE);
        Color minimizeBtnDefaultColor = new Color(146, 123, 255);
        Color minimizeBtnHoverColor = new Color(100, 100, 255);
        minimizeButton.setBackground(minimizeBtnDefaultColor);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setPreferredSize(new Dimension(70, 35));
        minimizeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        minimizeButton.addActionListener(e -> this.setState(JFrame.ICONIFIED));
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                minimizeButton.setBackground(minimizeBtnHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                minimizeButton.setBackground(minimizeBtnDefaultColor);
            }
        });

        // ===== دکمه خروج =====
        JButton exitButton = new JButton("x");
        exitButton.setFont(new Font("Vazir", Font.BOLD, 18));
        exitButton.setForeground(Color.WHITE);
        Color exitBtnDefaultColor = new Color(176, 103, 255);
        Color exitBtnHoverColor = new Color(220, 20, 60);
        exitButton.setBackground(exitBtnDefaultColor);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setPreferredSize(new Dimension(45, 35));
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> {
            // cleanup before exit
            cleanup();
            System.exit(0);
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                exitButton.setBackground(exitBtnHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                exitButton.setBackground(exitBtnDefaultColor);
            }
        });

        // ===== پنل دکمه‌های بالا =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        topPanel.setBackground(lavender);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        topPanel.add(minimizeButton);
        topPanel.add(exitButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JLabel message = new JLabel("می‌خوای شروع کنی؟");
        message.setForeground(new Color(80, 0, 130));
        message.setFont(new Font("Vazir", Font.BOLD, 40));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(message, BorderLayout.CENTER);

        ImageIcon gifIcon = null;
        URL gifUrl = Main.class.getResource("/help.gif");

        if (gifUrl == null) {
            System.err.println("گیف پیدا نشد!");
        } else {
            gifIcon = new ImageIcon(gifUrl);
        }

        JLabel gifLabel;
        if (gifIcon != null) {
            gifLabel = new JLabel(gifIcon);
            gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gifLabel.setPreferredSize(new Dimension(340, 330));
        } else {
            gifLabel = new JLabel("GIF not found");
            gifLabel.setForeground(Color.RED);
            gifLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        }
        panel.add(gifLabel, BorderLayout.EAST);

        Font clockFont = new Font("Vazir", Font.BOLD, 40);
        Color clockColor = new Color(90, 0, 160);

        clockLabel = new JLabel();
        clockLabel.setFont(clockFont);
        clockLabel.setForeground(clockColor);

        clockTextLabel = new JLabel("ساعت :");
        clockTextLabel.setFont(clockFont);
        clockTextLabel.setForeground(clockColor);

        workTimeLabel = new JLabel("زمان کار: 00:00");
        workTimeLabel.setFont(clockFont);
        workTimeLabel.setForeground(clockColor);

        JPanel leftClockPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftClockPanel.setBackground(lavender);
        leftClockPanel.add(workTimeLabel);

        JPanel rightClockPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightClockPanel.setBackground(lavender);
        rightClockPanel.add(clockLabel);
        rightClockPanel.add(clockTextLabel);

        JPanel clockPanel = new JPanel(new BorderLayout());
        clockPanel.setBackground(lavender);
        clockPanel.add(leftClockPanel, BorderLayout.WEST);
        clockPanel.add(rightClockPanel, BorderLayout.EAST);

        panel.add(clockPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(lavender);

        JButton startButton = createStyledButton("شروع", new Color(150, 120, 255));
        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        startButton.addActionListener(e -> startReminder());

        JButton stopButton = createStyledButton("پایان", new Color(100, 80, 180));
        stopButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        stopButton.addActionListener(e -> stopReminder());

        JButton contactButton = createStyledButton("تماس با ما", new Color(130, 90, 220));
        contactButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contactButton.addActionListener(e -> openContactPage());

        JButton helpButton = createStyledButton("راهنما", new Color(110, 70, 200));
        helpButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        helpButton.addActionListener(e -> openGuidePage());

        buttonPanel.add(Box.createVerticalStrut(40));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(stopButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(contactButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(helpButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        startClock();

        MouseAdapter ma = new MouseAdapter() {
            Point mouseDownCompCoords = null;

            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        };
        panel.addMouseListener(ma);
        panel.addMouseMotionListener(ma);

        setVisible(true);
    }

    private void startReminder() {
        ReminderStarter.start();
        isReminderActive = true;


        if (workTimer == null) {
            workSeconds = 0;
            startWorkTimer();
        }

        this.setState(JFrame.ICONIFIED);
    }

    private void stopReminder() {
        ReminderStarter.stop();
        isReminderActive = false;

        if (workTimer != null) {
            workTimer.cancel();
            workTimer = null;
        }

    }

    private void startWorkTimer() {
        if (workTimer != null) {
            workTimer.cancel();
        }

        workTimer = new Timer();
        workTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                workSeconds++;
                int minutes = workSeconds / 60;
                int seconds = workSeconds % 60;
                String timeText = String.format("زمان کار: %02d:%02d", minutes, seconds);
                SwingUtilities.invokeLater(() -> {
                    if (workTimeLabel != null) {
                        workTimeLabel.setText(timeText);
                    }
                });
            }
        }, 0, 1000);
    }

    private void openContactPage() {

        SwingUtilities.invokeLater(() -> new ContactPage());
    }

    private void openGuidePage() {

        SwingUtilities.invokeLater(() -> new GuidePage());
    }

    private void startClock() {
        if (clockTimer != null) {
            clockTimer.cancel();
        }

        clockTimer = new Timer();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        clockTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (clockLabel != null) {
                        clockLabel.setText(LocalTime.now().format(timeFormat));
                    }
                });
            }
        }, 0, 1000);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Vazir", Font.BOLD, 22));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void cleanup() {
        if (workTimer != null) {
            workTimer.cancel();
            workTimer = null;
        }
        if (clockTimer != null) {
            clockTimer.cancel();
            clockTimer = null;
        }
        ReminderStarter.stop();
    }


    public static Main getInstance() {
        return instance;
    }


    public static void returnToMain() {
        if (instance != null) {
            instance.setVisible(true);
            instance.setState(JFrame.NORMAL);
            instance.toFront();
        } else {
            new Main();
        }
    }

    @Override
    public void dispose() {
        cleanup();
        instance = null;
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}