package screen;

import asciiPanel.AsciiPanel;
import ruler.Win;

public class LoseScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost! Press enter to try again.", 0, 0);
        terminal.write("You can also resassign the Goal(length of snake) here:"+" "+getInputNumber(),0,2);
        terminal.write("Press space for more settings.",0,3);
    }
    public LoseScreen(Win win) {
        this.win = win;
    }

}
