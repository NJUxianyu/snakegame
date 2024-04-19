package screen;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ruler.Win;
import ruler.Win.NumberOfBlocksOfMap;
public class Settings implements Screen{ //设置界面
    private Win win;
    private static final int left=10;//离左边界的距离
    private String length;
    private String enemynumber;
    private String speed;
    private NumberOfBlocksOfMap numberOfBlocks; 
    public Settings(Win win){
        this.win = win;
        length = Integer.toString(win.getVictoryLength());
        enemynumber = Integer.toString(win.getNumberOfEnemys());
        speed = Integer.toString(win.getSpeed());
        numberOfBlocks = win.getmapmode();
        settingline = 1;
    }
    private int settingline;
    public int getsettingline(){
        return settingline;
    }
    
    private static final String LENGTH_LABEL = "length for snake to win : ";
    private static final String ENEMY_NUMBER_LABEL = "number of enemies : ";
    private static final String ENEMY_SPEED_LABEL = "speed of enenmy : ";
    private static final String MAP_MODE_LABEL ="map mode : ";
    private static final int LENGTH_CONSTANT = LENGTH_LABEL.length();
    private static final int ENEMY_NUMBER = ENEMY_NUMBER_LABEL.length();
    private static final int ENEMY_SPEED = ENEMY_SPEED_LABEL.length();
    private static final int MAP_MODE = MAP_MODE_LABEL.length();
    @Override
    public void displayOutput(AsciiPanel terminal){
        terminal.write("Settings",left,1);
        terminal.write(LENGTH_LABEL + length, left, 3);
        terminal.write(ENEMY_NUMBER_LABEL + enemynumber, left, 5);
        terminal.write(ENEMY_SPEED_LABEL+speed,left, 7);
        terminal.write(MAP_MODE_LABEL+'<'+numberOfBlocks.toString()+'>', left,9);
        switch (settingline) {
            case 1:
                terminal.write("_", left+LENGTH_CONSTANT+length.length(), 3);
                break;
            case 2:
                terminal.write("_", left+enemynumber.length()+ENEMY_NUMBER, 5);
                break;
            case 3:
                terminal.write("_",left+ENEMY_SPEED+speed.length(),7);
                break;
            case 4:
                terminal.write(" Change with -> or <-", 40,9);
                break;
            default:
                break;
        }
        terminal.write("press enter to continue after you finished setting.",left,11);
    }
        @Override
        public Screen respondToUserInput(KeyEvent key) {
            int keyCode = key.getKeyCode();
            if(settingline==4) {
                
                    switch (keyCode) {
                        case KeyEvent.VK_LEFT:
                            numberOfBlocks = NumberOfBlocksOfMap.next(numberOfBlocks, false);
                            win.setNumberOfBlocks(numberOfBlocks);
                            break;
                        case KeyEvent.VK_RIGHT:
                            numberOfBlocks = NumberOfBlocksOfMap.next(numberOfBlocks, true);
                            win.setNumberOfBlocks(numberOfBlocks);
                            break;
                        case KeyEvent.VK_UP:
                            return handleUpKey();
                        case KeyEvent.VK_DOWN:
                            return handleDownKey();
                        case KeyEvent.VK_ENTER:
                            return handleEnterKey();
                        default:
                            return this;
                    }
                 }
            else{
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            return handleUpKey();
                        case KeyEvent.VK_DOWN:
                            return handleDownKey();
                        case KeyEvent.VK_ENTER:
                            return handleEnterKey();
                        case KeyEvent.VK_BACK_SPACE:
                            return handleBackspaceKey();
                        default:
                            return handleNumericKey(key);
                    }
            }
            
            return this;
        }

        private Screen handleUpKey() {
            if (settingline != 1) {
                settingline--;
            }
            return this;
        }

        private Screen handleDownKey() {
            if (settingline != 4) {
                settingline++;
            }
            return this;
        }

        private Screen handleEnterKey() {
            if (!(length.equals("") || Integer.parseInt(length) < 1)) {
                int len = Integer.parseInt(length);
                win.setVictoryLength(len);
            }
            if (!(enemynumber.equals("") || Integer.parseInt(enemynumber) < 0)) {
                int number = Integer.parseInt(enemynumber);
                win.setNumberOfEnemys(number);
            }
            if (!(speed.equals("") || Integer.parseInt(speed) <= 0)) {
                int snake_speed = Integer.parseInt(speed);
                win.setSpeed(snake_speed);
            }
            return new SnakeGameScreen(win);
        }

        private Screen handleBackspaceKey() {
            switch (settingline) {
                case 1:
                    if (!length.equals("")) {
                        length = length.substring(0, length.length() - 1);
                    }
                    break;
                case 2:
                    if (!enemynumber.equals("")) {
                        enemynumber = enemynumber.substring(0, enemynumber.length() - 1);
                    }
                    break;
                case 3:
                    if (!speed.equals("")) {
                        speed = speed.substring(0, speed.length() - 1);
                    }
                    break;
                default:
                    break;
            }
            return this;
        }

        private Screen handleNumericKey(KeyEvent key) {
            if (key.getKeyCode() >= KeyEvent.VK_0 && key.getKeyCode() <= KeyEvent.VK_9) {
                switch (settingline) {
                    case 1:
                        length += key.getKeyChar();
                        break;
                    case 2:
                        enemynumber += key.getKeyChar();
                        break;
                    case 3:
                        speed += key.getKeyChar();
                        break;
                    default:
                        break;
                }
            }
            return this;
        }
           
}