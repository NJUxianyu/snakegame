import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Testframe extends JFrame {
    public Testframe() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources\\snake.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (image == null) {
            throw new RuntimeException("Failed to load image");
        }
        
        ImagePanel imagePanel = new ImagePanel(image);
        add(imagePanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Testframe();
    }
}
