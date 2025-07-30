import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

public class GuidePage extends JFrame {

    public GuidePage() {
        setTitle("راهنما و نحوه استفاده");
        setSize(850, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // پنجره بدون حاشیه و با گوشه‌های گرد
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        Color backgroundColor = new Color(12, 170, 218);
        Font font = new Font("Vazir", Font.PLAIN, 20);

        JPanel panel = new JPanel(null);
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextArea textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(backgroundColor);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textArea.setBounds(20, 50, 810, 440);

        textArea.setText(
                "سلام دوست عزیز! 😊\n\n" +
                        "این برنامه برای محافظت از چشم‌هایت طراحی شده تا هنگام کار طولانی پای کامپیوتر، چشم‌هات کمتر خسته بشن.\n\n" +
                        "هر 20 دقیقه، یه استراحت کوتاه 20 ثانیه‌ای بده و به یه جسم دور نگاه کن.\n\n" +
                        "برای شروع، دکمه «شروع» رو بزن.\n\n" +
                        "اگه سوال داشتی یا کمک خواستی، دکمه «تماس با ما» آماده‌ی پاسخ‌گویی به توئه.\n\n" +
                        "امیدوارم این برنامه کمکت کنه چشم‌هات همیشه سلامت بمونن!\n\n" +
                        "موفق باشی 🌟"
        );
        panel.add(textArea);

        // گیف متحرک پایین متن
        ImageIcon downGif = new ImageIcon(getClass().getResource("/down.gif"));

        JLabel downGifLabel = new JLabel(downGif);

        int gifWidth = downGif.getIconWidth();
        int gifHeight = downGif.getIconHeight();
        int gifX = (850 - gifWidth) / 2;
        int gifY = 500;

        downGifLabel.setBounds(gifX, gifY, gifWidth, gifHeight);
        panel.add(downGifLabel);

        // دکمه بازگشت به خانه - پایین سمت چپ
        JButton backButton = new JButton("بازگشت");
        backButton.setFont(font);
        backButton.setBackground(new Color(8, 140, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setBounds(30, 700, 180, 40);

        backButton.addActionListener((ActionEvent e) -> {
            dispose(); // فقط این صفحه بسته شود
            Main.returnToMain(); // صفحه اصلی نمایش داده شود
        });

        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuidePage::new);
    }
}