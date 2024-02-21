package main;

import gfx.Assets;
import input.KeyBoard;
import input.MouseManager;
import sokoban.Level;
import states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable,ActionListener{
    public static final int WIDTH = 1700 , HEIGHT = 980;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private BufferStrategy bs;
    private Graphics g;
    private final int FPS = 60;
    private double TARGETTIME = 1000000000/FPS;
    private double delta = 0;

    private GameState gameState;
    private LevelSelectorState levelSelectorState;
    private MenuState menuState;
    private LoadingState loadingState;
    private KeyBoard keyBoard;
    private MouseManager mouseManager;

    public Window(){

        setTitle("Waveriders Sokoban");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JMenuBar jbar = new JMenuBar();
        JMenu jmfile = new JMenu("File");
        jmfile.setMnemonic('F');
        JMenuItem jmiSave = new JMenuItem("Save");
        jmiSave.setMnemonic('S');
        jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        jmiSave.setIcon(new ImageIcon("Sokoban12/src/gfx/res/blocks/icons8-sauvegarder-30.png"));
        jmfile.add(jmiSave);
        jmfile.addSeparator();
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmiExit.setMnemonic('E');
        jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_DOWN_MASK));
        jmiExit.setIcon(new ImageIcon("Sokoban12/src/gfx/res/blocks/icons8-fermer-la-fenêtre-30.png"));
        jmfile.add(jmiExit);
        jmiExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int confirm = JOptionPane.showOptionDialog(Window.this,"Voulez-vous vraiment quitter le jeu ?", "MESSAGE",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if(confirm == JOptionPane.YES_OPTION){
                    Window.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                } 
                else{
                    Window.this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
            });
        jbar.add(jmfile);
        
        

        JMenu jmHelp = new JMenu("Help");
        jmHelp.setMnemonic( 'H' );
        JMenuItem jmiAbout = new JMenuItem("About");
        jmiAbout.setMnemonic( 'A' );
        jmiAbout.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
        jmiAbout.setIcon(new ImageIcon("Sokoban12/src/gfx/res/blocks/icons8-info-24.png"));
        jmHelp.add(jmiAbout);
        jmiAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame newframe = new JFrame("About");
                newframe.setSize(800,400);
                Assets.txtabout.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(Assets.txtabout);
                newframe.getContentPane().add(scrollPane,BorderLayout.CENTER);
                newframe.setVisible(true);
                newframe.setLocationRelativeTo(null);
            }
        });
        
        jbar.add(jmHelp);
        jmiSave.addActionListener(this);
        jmiExit.addActionListener(this);
        jmiAbout.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                int confirm = JOptionPane.showOptionDialog(Window.this,"Voulez-vous vraiment quitter le jeu ?", "MESSAGE",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if(confirm == JOptionPane.YES_OPTION){
                    Window.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                } 
                else{
                    Window.this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });



        canvas = new Canvas();
        keyBoard = new KeyBoard();
        mouseManager = new MouseManager();

        canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH,HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        canvas.setFocusable(true);

        add(canvas);
        addMouseMotionListener(mouseManager);
        addMouseListener(mouseManager);
        canvas.addMouseMotionListener(mouseManager);
        canvas.addMouseListener(mouseManager);
        canvas.addKeyListener(keyBoard);
        setJMenuBar(jbar);
        setVisible(true);
    }
    public static void main(String[] args){
        
        new Window().start();
    }
    private void update(){
        if(State.currentState instanceof GameState)
            keyBoard.update();
        if ( State.currentState != null)
            State.currentState.update();
        }
    private void draw(){
        bs = canvas.getBufferStrategy();
        if (bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);
        for ( int i = 0 ; i < Window.WIDTH/ Level.TILESIZE+1;i++)
            for ( int j = 0 ; j < Window.HEIGHT/Level.TILESIZE + i; j++)
                g.drawImage(Assets.floor2,i*Level.TILESIZE,j*Level.TILESIZE, null);
            if(State.currentState != null)
            State.currentState.render(g);
        g.dispose();
        bs.show();
    }
    private void init(){
        Assets.init();
        menuState = new MenuState(this);
        gameState = new GameState(this);
        loadingState = new LoadingState(this);
        levelSelectorState = new LevelSelectorState(this);
        State.currentState = loadingState;
    }
    @Override
    public void run(){
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        init();

        while(running)
        {
            now = System.nanoTime();
            delta += ( now - lastTime)/TARGETTIME;
            time += ( now - lastTime);
            lastTime = now;
            if(delta >= 1){
                    update();
                    draw();
                    delta --;
                    frames ++;
            }
            if ( time >= 1000000000 )
            {
                frames = 0;
                time = 0;
            }
        }
        stop();
    }
    private void start(){
            thread = new Thread(this);
            thread.start();
            running = true;

    }
    private void stop(){
            try {
                    thread.join();
                    running = false;
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
    }
    public State getGameState(){
                return gameState;
    }
    public State getLevelSelectorState(){
                return levelSelectorState;
    }
    public State getMenuState(){
                return menuState;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String comstr = e.getActionCommand();
        System.out.println(comstr + "Selected");
    }

    
}