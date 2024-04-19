package screen;

import asciiPanel.AsciiPanel;
import ruler.Win;
public class WinScreen extends RestartScreen {
    public WinScreen(Win win) {
        this.win = win;
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        if(getInputNumber().equals("")){
        terminal.write("You won! Press enter to go again.", 0, 0);
        terminal.write("You can also resassign the Goal(length of snake) here:"+" ",0,2);
        terminal.write("Press space for more settings.",0,3);}
        else {
        terminal.write("You won! Press enter to go again.", 0, 0);
        terminal.write("You can also resassign the Goal(length of snake) here:"+" "+getInputNumber(),0,2);
        terminal.write("Press space for more settings.",0,3);
        }
    }  
}

