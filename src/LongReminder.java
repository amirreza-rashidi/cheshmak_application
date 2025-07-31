package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class LongReminder {

    public static void show() {
        final int totalSeconds = 100;

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(550, 400);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);


        frame.setShape(new RoundRectangle2D.Double(0, 0, 550, 400, 25, 25));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 0, 0));
        mainPanel.setLayout(new BorderLayout());


        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 0, 0));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel title = new JLabel("وقت استراحت طولانیه!", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(255, 215, 0));

        JLabel msg = new JLabel("یکم بیشتر استراحت کن و به خودت برس عزیزم", SwingConstants.CENTER);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        msg.setForeground(new Color(255, 255, 255));

        ImageIcon gifIcon = new ImageIcon(ClassLoader.getSystemResource("happy.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gifLabel.setPreferredSize(new Dimension(250, 150));

        JLabel emoji = new JLabel("زود بیا ادامه بدیم !");
        emoji.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);
        emoji.setForeground(new Color(255, 207, 56));

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


        JButton skipButton = new JButton("برم کنار؟");
        styleButton(skipButton);
        skipButton.addActionListener(e -> frame.setState(JFrame.ICONIFIED));
        bottomPanel.add(skipButton, BorderLayout.WEST);


        JButton lockButton = new JButton("قفل کنم؟");
        styleButton(lockButton);
        lockButton.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("rundll32.exe user32.dll,LockWorkStation");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        bottomPanel.add(lockButton, BorderLayout.EAST);
        JLabel timerLabel = new JLabel("بستن خودکار در 100 ثانیه", SwingConstants.CENTER);
        timerLabel.setForeground(new Color(255, 255, 255)); // سفید مشخص
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 15)); // بزرگتر و bold
        bottomPanel.add(timerLabel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);


        final int[] remaining = {totalSeconds};
        new javax.swing.Timer(600, e -> {
            remaining[0]--;
            timerLabel.setText("بستن خودکار در " + remaining[0] + " ثانیه");
            if (remaining[0] <= 0) {
                frame.dispose();
                ((javax.swing.Timer) e.getSource()).stop();
            }
        }).start();
    }

    private static void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(60, 60, 60));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));


        button.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(80, 80, 80));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(60, 60, 60));
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
            g2.setColor(new Color(255, 207, 56));
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}
