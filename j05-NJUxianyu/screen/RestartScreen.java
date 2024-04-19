package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import ruler.Win;
public abstract class RestartScreen implements Screen {
    protected Win win;
    public RestartScreen(Win win) {
        this.win = win;
    }
    public RestartScreen(){
        this.win = new Win();
    }
    @Override
    public abstract void displayOutput(AsciiPanel terminal);
    private String inputNumber = "";
    public String getInputNumber(){
        return inputNumber;
    }
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (!inputNumber.equals("")) {
                    int number = Integer.parseInt(inputNumber);
                    win.setVictoryLength(number);
                    return new SnakeGameScreen(win);
                } else {
                    return new SnakeGameScreen(win);
                }
            case KeyEvent.VK_BACK_SPACE:
                if (!inputNumber.equals("")) {
                    // 删除字符串末尾的字符
                    inputNumber = inputNumber.substring(0, inputNumber.length() - 1);
                }
                return this;
            case KeyEvent.VK_SPACE:
                return new Settings(win);
            default:
                if (key.getKeyCode() >= KeyEvent.VK_0 && key.getKeyCode() <= KeyEvent.VK_9) {
                    // 将数字追加到 inputNumber
                    inputNumber += key.getKeyChar();
                }
                return this;
        }
    }
}