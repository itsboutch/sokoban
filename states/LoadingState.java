package states;

import gfx.Text;
import gfx.Assets;

import java.awt.*;
import main.Window;

public class LoadingState extends State {
    private final String NAME = "DEVELOPED BY WAVERIDERS ";
    private String text ="";
    private int index = 0;
    private long time, lastTime;
    public LoadingState(Window window){
        super(window);
        time = 0;
        lastTime = System.currentTimeMillis();
    }
    @Override
    public void update(){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(time > 75){
            text = NAME.substring(0,index);
            if ( index < NAME.length()){
                index ++;
            }
            else{
                try {
                    Thread.sleep(1500);
                }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    State.currentState = window.getMenuState();
            }
            time = 0;
        }
    }
    @Override
    public void render(Graphics g){
        g.setFont(Assets.font25);
    Text.drawString(g,text,Window.WIDTH/2,Window.HEIGHT/2,true,Color.WHITE);
    }
}
