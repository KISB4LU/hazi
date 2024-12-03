package window.menubar;

import window.login.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * profil, kiirja a felhasználó nevét és az aktuális egyenlegét
 */
public class Profile extends JPanel {
    private User user;
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private ProfilePicture image;
    public void setUser(User user) {
        this.user = user;
        nameLabel = new JLabel(user.getName());
        double balance = Math.round(user.getBalance()*100.0)/100.0;
        balanceLabel = new JLabel(String.valueOf(balance));
        image = new ProfilePicture();
        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
        stats.add(nameLabel);
        stats.add(balanceLabel);
        add(image);
        add(stats);
    }
    public void update(){
        double balance = Math.round(user.getBalance()*100)/100.0;
        balanceLabel.setText(String.valueOf(balance));
    }
}
