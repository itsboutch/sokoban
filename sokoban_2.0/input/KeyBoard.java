package input;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyBoard implements KeyListener {
    private boolean[] keys;
    public static boolean UP,LEFT,RIGHT,DOWN,COOLDOWN,CTRL,Z;

    public KeyBoard(){
        keys = new boolean[256];
        UP = false;
        DOWN = false;
        RIGHT = false;
        LEFT = false;
        CTRL = false; 
        Z = false;
        COOLDOWN = true;
    }
    public void update(){
        UP=keys[KeyEvent.VK_UP];
        LEFT=keys[KeyEvent.VK_LEFT];
        RIGHT=keys[KeyEvent.VK_RIGHT];
        DOWN=keys[KeyEvent.VK_DOWN];
        CTRL = keys[KeyEvent.VK_CONTROL];
        Z = keys[KeyEvent.VK_Z];
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        COOLDOWN = true;
    }
}