package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class ShortReminder {

    public static void showFirstTime() {
        final int totalSeconds = 20;

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 400, 25, 25));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 0, 0));
        mainPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 0, 0));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel title = new JLabel("شروع کنیم! هر ۲۰ دقیقه بهت یادآوری می‌کنم.", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 246, 255));

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        ImageIcon gifIcon = new ImageIcon(ClassLoader.getSystemResource("eye.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gifLabel.setPreferredSize(new Dimension(240, 150));
        contentPanel.add(gifLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel emoji = new JLabel("تو میتونی رفیق♡");
        emoji.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);
        emoji.setForeground(new Color(255, 255, 255));
        contentPanel.add(emoji);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(0, 0, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton skipButton = new JButton("x");
        styleSkipButton(skipButton);
        skipButton.addActionListener(e -> frame.dispose());
        bottomPanel.add(skipButton, BorderLayout.WEST);

        JLabel timerLabel = new JLabel("بستن خودکار در 20 ثانیه", SwingConstants.CENTER);
        timerLabel.setForeground(new Color(0, 246, 255));
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bottomPanel.add(timerLabel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        final int[] remaining = {totalSeconds};
        new javax.swing.Timer(1000, e -> {
            remaining[0]--;
            timerLabel.setText("بستن خودکار در " + remaining[0] + " ثانیه");
            if (remaining[0] <= 0) {
                frame.dispose();
                ((javax.swing.Timer) e.getSource()).stop();
            }
        }).start();
    }

    public static void showReminderWithTimer() {
        final int totalSeconds = 20;

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 400, 25, 25));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 0, 0));
        mainPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 0, 0));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel title = new JLabel("وقتشه چشماتو استراحت بدی", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 246, 255));

        JLabel msg = new JLabel("چشماتو به چپ و راست حرکت بده و دورتر رو نگاه کن", SwingConstants.CENTER);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        msg.setForeground(new Color(255, 255, 255));

        ImageIcon gifIcon = new ImageIcon(ClassLoader.getSystemResource("eye.gif"));

        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gifLabel.setPreferredSize(new Dimension(240, 150));

        JLabel emoji = new JLabel("راستی خسته نباشی ♡");
        emoji.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);
        emoji.setForeground(new Color(255, 255, 255));

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(msg);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(gifLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(emoji);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(0, 0, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton skipButton = new JButton("x");
        styleSkipButton(skipButton);
        skipButton.addActionListener(e -> frame.dispose());
        bottomPanel.add(skipButton, BorderLayout.WEST);

        JLabel timerLabel = new JLabel("بستن خودکار در 20 ثانیه", SwingConstants.CENTER);
        timerLabel.setForeground(new Color(0, 246, 255));
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bottomPanel.add(timerLabel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        final int[] remaining = {totalSeconds};
        new javax.swing.Timer(1000, e -> {
            remaining[0]--;
            timerLabel.setText("بستن خودکار در " + remaining[0] + " ثانیه");
            if (remaining[0] <= 0) {
                frame.dispose();
                ((javax.swing.Timer) e.getSource()).stop();
            }
        }).start();
    }

    private static void styleSkipButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(new Color(150, 150, 150));
        button.setBackground(new Color(30, 30, 30));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(60, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(255, 0, 0));
                button.setForeground(new Color(255, 255, 255));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30));
                button.setForeground(new Color(255, 255, 255));
            }
        });
    }

    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(120, 120, 120));
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}