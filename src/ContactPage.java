import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class ContactPage extends JFrame {

    public ContactPage() {
        setTitle("تماس با من");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(12, 170, 218));

        Font titleFont = new Font("Vazir", Font.BOLD, 40);
        Font labelFont = new Font("Vazir", Font.BOLD, 24);
        Font valueFont = new Font("Vazir", Font.PLAIN, 24);

        // بارگذاری گیف از resources
        URL gifUrl = getClass().getResource("/left.gif");
        JLabel gifLabel;

        if (gifUrl != null) {
            ImageIcon gifIcon = new ImageIcon(gifUrl);
            gifLabel = new JLabel(gifIcon);
            gifLabel.setBounds(20, 40, 400, 400);
        } else {
            System.err.println("⚠️ فایل left.gif پیدا نشد!");
            gifLabel = new JLabel("GIF not found!");
            gifLabel.setForeground(Color.RED);
            gifLabel.setBounds(20, 40, 200, 40);
        }

        panel.add(gifLabel);

        // نام
        JLabel nameLabel = new JLabel("امیررضا رشیدی");
        nameLabel.setFont(titleFont);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setBounds(300, 50, 500, 40);
        panel.add(nameLabel);

        // رشته
        JLabel fieldLabel = new JLabel("دانشجوی مهندسی کامپیوتر");
        fieldLabel.setFont(labelFont);
        fieldLabel.setForeground(Color.WHITE);
        fieldLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fieldLabel.setBounds(300, 110, 500, 30);
        panel.add(fieldLabel);

        // خط جداکننده زیر رشته
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(300, 360, 500, 2);
        separator1.setForeground(Color.WHITE);
        panel.add(separator1);

        // تلگرام
        JLabel telLabel = new JLabel("تلگرام:");
        telLabel.setFont(labelFont);
        telLabel.setForeground(Color.WHITE);
        telLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        telLabel.setBounds(720, 380, 80, 30);
        panel.add(telLabel);

        JLabel telValueLabel = new JLabel("@weareunity5831");
        telValueLabel.setFont(valueFont);
        telValueLabel.setForeground(Color.WHITE);
        telValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        telValueLabel.setBounds(300, 380, 400, 30);
        panel.add(telValueLabel);

        // خط جداکننده زیر تلگرام
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(300, 420, 500, 2);
        separator2.setForeground(Color.WHITE);
        panel.add(separator2);

        // گیت‌هاب
        JLabel githubLabel = new JLabel("گیت‌هاب:");
        githubLabel.setFont(labelFont);
        githubLabel.setForeground(Color.WHITE);
        githubLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        githubLabel.setBounds(720, 440, 80, 30);
        panel.add(githubLabel);

        JLabel githubValueLabel = new JLabel("github.com/amirreza-rashidi");
        githubValueLabel.setFont(valueFont);
        githubValueLabel.setForeground(Color.WHITE);
        githubValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        githubValueLabel.setBounds(300, 440, 400, 30);
        panel.add(githubValueLabel);

        // خط جداکننده زیر گیت‌هاب
        JSeparator separator3 = new JSeparator();
        separator3.setBounds(300, 480, 500, 2);
        separator3.setForeground(Color.WHITE);
        panel.add(separator3);

        // ایمیل
        JLabel emailLabel = new JLabel("ایمیل:");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailLabel.setBounds(720, 500, 80, 30);
        panel.add(emailLabel);

        JLabel emailValueLabel = new JLabel("amirrezarashidi5831ar@gmail.com");
        emailValueLabel.setFont(valueFont);
        emailValueLabel.setForeground(Color.WHITE);
        emailValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailValueLabel.setBounds(300, 500, 400, 30);
        panel.add(emailValueLabel);

        // خط جداکننده زیر ایمیل
        JSeparator separator4 = new JSeparator();
        separator4.setBounds(300, 540, 500, 2);
        separator4.setForeground(Color.WHITE);
        panel.add(separator4);

        // جمله همکاری
        JLabel collabLabel = new JLabel("برای همکاری در پروژه‌ها آماده‌ام !");
        collabLabel.setFont(labelFont);
        collabLabel.setForeground(Color.WHITE);
        collabLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        collabLabel.setBounds(300, 560, 500, 40);
        panel.add(collabLabel);

        // دکمه بازگشت به خانه - پایین سمت چپ
        JButton backButton = new JButton("بازگشت");
        backButton.setFont(valueFont);
        backButton.setBackground(new Color(8, 140, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setBounds(30, 580, 180, 40);

        backButton.addActionListener((ActionEvent e) -> {
            dispose(); // فقط این صفحه بسته شود
            Main.returnToMain(); // صفحه اصلی نمایش داده شود
        });

        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactPage::new);
    }
}