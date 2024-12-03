package window.menubar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * profilkép megjelenitése
 */
public class ProfilePicture extends JPanel {

    @Override
    public void paint(Graphics g) {
        BufferedImage image;
        try {
             image = ImageIO.read(new File("src/main/data/profil.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, null);
    }
}