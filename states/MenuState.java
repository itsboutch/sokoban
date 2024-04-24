package states;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import gfx.Assets;
import gfx.Text;
import main.Window;
import ui.Button;
import ui.Click;

public class MenuState extends State{
    private ArrayList<Button> buttons = new ArrayList<Button>();
    JFrame fenerror;
    public MenuState(Window window){
        super(window);
        buttons.add(new Button("Play",Window.WIDTH/2 ,Window.HEIGHT/2 ,new Click(){
            @Override
            public void onClick(){
                State.currentState = window.getLevelSelectorState();
            }
        },Assets.font25));
        

        Button bexit = new Button("Exit", Window.WIDTH / 2 , Window.HEIGHT / 2 +100 ,  new Click() {
            @Override
            public void onClick() {
                int confirm = JOptionPane.showConfirmDialog(fenerror,"Voulez-vous vraiment quitter le jeu ?", "MESSAGE",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                System.exit(1);
                }
            }},Assets.font25);
        

        fenerror = new JFrame("MESSAGE");
        buttons.add(bexit);

        Button bAbout = new Button("About", Window.WIDTH / 2 , Window.HEIGHT / 2 +50,  new Click() {
            @Override
            public void onClick() {
                Frame newframe = new JFrame("About");
                newframe.setSize(800,400);
                Assets.txtabout.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(Assets.txtabout);
                ((JFrame) newframe).getContentPane().add(scrollPane,BorderLayout.CENTER);
                newframe.setVisible(true);
                newframe.setLocationRelativeTo(null);
                
                
            }},Assets.font25);

        buttons.add(bAbout);
    }
        @Override
        public void update(){
        for ( int i = 0; i < buttons.size(); i++){
            buttons.get(i).update();
        }
    }
        @Override
        public void render(Graphics g){
        g.setFont(Assets.font45);
        Text.drawString(g,"WAVERIDERS SOKOBAN",Window.WIDTH/2,Window.HEIGHT/2-200, true,Color.white);
        for( int i = 0 ; i < buttons.size(); i++){
            buttons.get(i).render(g);
        }
    }

}
