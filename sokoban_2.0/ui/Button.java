package ui;

import java.awt.*;

import gfx.Text;
import input.MouseManager;

public class Button {
    private String text;
    private int x,y;
    private FontMetrics fm;
    private Rectangle bounds;
    private Click click;
    private Font font;
    private boolean hovering;
    public Button(String text, int x , int y, Click click,Font font){
        this.text=text;
        this.x=x;
        this.y=y;
        this.click=click;
        hovering = false;
        this.font = font;
    }

    public void update(){
        if(bounds != null && bounds.contains(MouseManager.x, MouseManager.y)){
            hovering = true;
            if(MouseManager.left)
                click.onClick();
        }
        else{
            hovering = false;
        }
    }
    public void render(Graphics g){
        g.setFont(font);
        fm = g.getFontMetrics();
        if(hovering)
            Text.drawString(g,text,x,y,true,Color.LIGHT_GRAY);
            else
                Text.drawString(g,text,x,y,true,Color.WHITE);

            bounds = new Rectangle(x-fm.stringWidth(text)/2,y- fm.getHeight()/2 , fm.stringWidth(text), fm.getHeight());


    }
    

}
