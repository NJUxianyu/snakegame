package screen;

import asciiPanel.AsciiPanel;
import ruler.Win;
public class StartScreen extends RestartScreen {
    public StartScreen(Win win) {
        this.win = win;
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("This is the start screen.", 0, 0);
        terminal.write("Press ENTER to continue...", 0, 1);
        terminal.write("You can also resassign the Goal(length of snake) here:"+" "+getInputNumber(),0,2);
        terminal.write("Press space for more settings.",0,3);
    }

}
