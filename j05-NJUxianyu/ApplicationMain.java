import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screen.Screen;
import screen.SnakeGameScreen;
import screen.StartScreen;
import screen.WinScreen;
import ruler.Win;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

        // ...

        public ApplicationMain(Win win) {
            super();
            // ...

            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("resources\\snake.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImagePanel imagePanel = new ImagePanel(image);
            add(imagePanel);
            pack();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);

            try {
                TimeUnit.SECONDS.sleep(1); // Pause for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            remove(imagePanel);
            terminal = new AsciiPanel(80, 33, AsciiFont.TALRYTH_15_15);
            add(terminal);

            pack();

            screen = new StartScreen(win);

            addKeyListener(this);
            repaint();
        }

        // ...
    

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
    public ApplicationMain changeApplicationMain(Screen screen){
        this.screen=screen;
        return this;
    }
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        Win win = new Win(20);
        
        ApplicationMain app = new ApplicationMain(win);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an ImageIcon and set it as the content of a JLabel
        
        
        app.setVisible(true);
    }

}
