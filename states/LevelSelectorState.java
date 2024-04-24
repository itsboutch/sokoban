package states;
import java.awt.*;
import java.io.*;
import gfx.Assets;
import gfx.Text;
import input.MouseManager;
import main.Window;
import sokoban.Level;
import ui.Button;
import ui.Click;
import java.util.ArrayList;



public class LevelSelectorState extends State {
    private final int DOUBLETILESIZE = 64;
    private Level[] levels = new Level[30];
    private final int xOffset = (Window.WIDTH - DOUBLETILESIZE*6)/2;
    private final int yOffset = (Window.HEIGHT - DOUBLETILESIZE*5)/2;
    private Button back;

    public LevelSelectorState ( Window window){
        super(window);
        for ( int i = 0 ; i < levels.length ; i++){
            levels[i] = loadLevel("levels/"+ i + ".txt");
        }
        back = new Button("Back",Window.WIDTH/2,Window.HEIGHT - 100 , new Click(){
            @Override
            public void onClick(){
                State.currentState = window.getMenuState();
            }
        }, Assets.font45);
    }
    @Override
    public void update(){
        back.update();
    }

    private int getcounter(int x, int y){
        int counter = 1;
        for(int j = 337; j < 662; j = j + 65){
            for(int i = 666; i < 1056; i = (i + 65)){
                if ( (x >= i) && (x <= (i + 65)) && (y <= (j + 65)) && (y >= j) ){
                    return counter;
                }
                counter++;
            }
        }
        return 0;
    }


    @Override
    public void render(Graphics g) {
        back.render(g);
        int counter = 1;
        int counter1 = 1;
        g.setFont(Assets.font35);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                Rectangle bounds = new Rectangle(xOffset + j * DOUBLETILESIZE + 7 , yOffset + i * DOUBLETILESIZE + 7, DOUBLETILESIZE, DOUBLETILESIZE);
                if (bounds.contains(MouseManager.x, MouseManager.y)) {
                    counter1 = getcounter(MouseManager.x, MouseManager.y);
                    if (MouseManager.left && levels[counter - 1].isSolved()){
                        levels[counter1 - 1].start = true;
                        ((GameState)window.getGameState()).setLevel(levels[counter1 - 1]);
                        
                        State.currentState = window.getGameState();
                    }
                    g.drawImage(Assets.outline2_rec, bounds.x, bounds.y, null);
                    if (levels[counter - 1].isSolved()) {
                        Text.drawString(g, counter + "", xOffset + DOUBLETILESIZE / 2 + j * DOUBLETILESIZE, yOffset + DOUBLETILESIZE / 2 + i * DOUBLETILESIZE, true, Color.RED);
                    } else
                        Text.drawString(g, "?", xOffset + DOUBLETILESIZE / 2 + j * DOUBLETILESIZE, yOffset + DOUBLETILESIZE / 2 + i * DOUBLETILESIZE, true, Color.RED);
                } else {
                    g.drawImage(Assets.outline_rec, bounds.x, bounds.y, null);
                    if (levels[counter - 1].isSolved()) {
                        Text.drawString(g, counter + "", xOffset + DOUBLETILESIZE / 2 + j*DOUBLETILESIZE,yOffset + DOUBLETILESIZE/2 + i*DOUBLETILESIZE,true,Color.DARK_GRAY);
                    } else
                        Text.drawString(g, "?",xOffset + DOUBLETILESIZE / 2 + j*DOUBLETILESIZE,yOffset + DOUBLETILESIZE/2 + i*DOUBLETILESIZE,true,Color.LIGHT_GRAY);
                }
                counter++;
            }
        }
        
    }
    /**
     * @param path
     * @return
     */
    private Level loadLevel(String path){
        String file = loadFileAsString(path);
        String[] numbers = file.split("\\s+");
        int cols = parseInt(numbers[0]);
        int rows = parseInt(numbers[1]);
        int player_col = parseInt(numbers[2]);
        int player_row = parseInt(numbers[3]);
        boolean tpm = true;
        int[][] maze = new int[rows][cols];
        for (int row = 0; row < rows ; row++){
            for (int col = 0; col < cols ; col++){
                maze[row][col] = parseInt(numbers[(col + (row*cols))+4]);
            }
        }
        int pos = (rows * cols) + 4;
        if ((numbers[pos].equals("#"))){
            tpm = false;
        }

        ArrayList<int[][]> tab_maze = new ArrayList<>();
        ArrayList<Integer> player_pos = new ArrayList<>();
        while (tpm){
            cols = parseInt(numbers[pos]);
            rows = parseInt(numbers[pos + 1]);

            player_pos.add(parseInt(numbers[pos + 2]));
            player_pos.add(parseInt(numbers[pos + 3]));
            
            int[][] mazes = new int[rows][cols];
            int i = 0;
            for (int row = 0; row < rows ; row++){
                for ( int col = 0; col < cols ; col++){ 
                    i++;
                    mazes[row][col] = parseInt(numbers[(col + (row*cols))+4 + pos]);
                }        
            }
            tab_maze.add(mazes);
            if (!(numbers[pos + (rows*cols)+ 4].equals("#"))){
                pos = pos + (rows*cols)+ 4;
            }
            else{
                tpm = false;
            }
        } 
        return new Level(maze, tab_maze, player_pos, player_row,player_col,this);
    }
    public Level[] getLevels(){
        return levels;
    }
    public static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();
        try{
            InputStream in = LevelSelectorState.class.getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=br.readLine()) != null){
                builder.append(line+"\n");
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }
    public static int parseInt(String num){
        try{
            return Integer.parseInt(num);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
}


