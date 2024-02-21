package sokoban;

import gfx.Assets;
import input.KeyBoard;
import states.LevelSelectorState;
import states.State;
import ui.Button;
import ui.Click;
import main.Window;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.*;

public class Level {
    public static final int TILESIZE = 96;
    private ArrayList<Integer> player_pos;
    private ArrayList<Integer> copy_player_pos;
    private ArrayList<int[][]> tab_maze;
    private ArrayList<int[][]> copy_tab_maze;
    private int[][] maze;
    private int[][] copy;
    private Stack<int[][]> ctrlZ;
    private Stack<Integer> myLevel;
    private Stack<Integer> ctrlZPlayer; 
    private int player_row,player_col;
    private Image texture;
    private int xOffset,yOffset;
    private long time, lastTime;
    private final int DELAY = 150;
    private Button restart,back;
    private boolean solved;
    private  int plaStartRow,plaStartCol;
    private LevelSelectorState levelSelectorState;
    private int isInrecur;
    public static int ID =0;
    private int id;
    private Executor thread;
    public boolean start = true;
    public Level(int[][] maze, ArrayList<int[][]> tab_maze, ArrayList<Integer> player_pos, int player_row , int player_col,LevelSelectorState levelSelectorState){
        thread = Executors.newSingleThreadExecutor();
        ctrlZ = new Stack<>();
        ctrlZPlayer = new Stack<>();
        myLevel = new Stack<>();
        this.player_pos = player_pos;
        this.copy_player_pos = new ArrayList<>(player_pos);
        this.tab_maze = tab_maze;
        this.copy_tab_maze = new ArrayList<>();
        for(int i = 0; i < tab_maze.size(); i++){ 
            int[][] tpm = copyMatrix(tab_maze.get(i));  
            copy_tab_maze.add(tpm);
        }

        this.levelSelectorState = levelSelectorState;
        ctrlZ.push(maze);
        ctrlZPlayer.push(player_row);
        ctrlZPlayer.push(player_col);
        this.maze = maze;
        ID++;
        id = ID;
        copy = new int[maze.length][maze[0].length];
        for ( int row = 0 ; row< maze.length; row ++ ) {
            for ( int col = 0 ; col<maze[row].length; col++){
                copy[row][col] = maze[row][col];
            }
            plaStartRow = player_row;
            plaStartCol = player_col;
            this.player_row = player_row;
            this.player_col = player_col;
            if (ID == 1 )
                solved  = true ;
            else
                solved = false;
            xOffset = (Window.WIDTH - maze[0].length/TILESIZE)/2;
            yOffset = (Window.HEIGHT - maze.length*TILESIZE)/2;
            texture = Assets.playerFront;
            restart = new Button("Restart", 100, Window.HEIGHT / 2, new Click() {
                @Override
                public void onClick() {
                    reset();
                }
            },Assets.font35);
            back = new Button("Back", Window.WIDTH - 150, Window.HEIGHT / 2, new Click() {
                @Override
                public void onClick() {
                    State.currentState = levelSelectorState;
                }
            },Assets.font35);
            time = 0;
            lastTime = System.currentTimeMillis();
        }
    }

    public void afficherMatrice(int[][] mazes){
		for (int i = 0; i < mazes.length; i++) { 
            for (int j = 0; j < mazes[i].length; j++) { 
                System.out.print(mazes[i][j]); 
            } 
            System.out.println(); 
        }
	}

    public void changerspawn(){
		for (int i = 0; i < this.maze.length; i++) { 
            for (int j = 0; j < this.maze[i].length; j++) { 
                if (this.maze[i][j] == 5){
                    player_row = i;
                    player_col = j;  
                }
            }  
        }
	}

    public static int[][] copyMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] newMatrix = new int[rows][cols];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    private void reset(){
        for(int row = 0; row < maze.length;row++){
            for (int col = 0; col < maze[row].length;col ++)
                maze[row][col] = copy[row][col];
        }

        tab_maze.clear();
        for(int i = 0; i < copy_tab_maze.size(); i++){ 
            int[][] tpm = copyMatrix(copy_tab_maze.get(i));  
            tab_maze.add(tpm);
        }


        player_pos.clear();
        player_pos.addAll(copy_player_pos);

        ctrlZ.clear();
        ctrlZPlayer.clear();

        player_row = plaStartRow;
        player_col = plaStartCol;
        texture = Assets.playerFront;
    }
    public void update(){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        if (start){
            if (solved){
                reset();
            }
            start = false;
        }


        if (KeyBoard.UP && time > DELAY && KeyBoard.COOLDOWN){
            KeyBoard.COOLDOWN = false;
            move(-1,0);
            texture = Assets.playerBack;
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sokoban/footsteps.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
        if (KeyBoard.LEFT && time > DELAY && KeyBoard.COOLDOWN){
            KeyBoard.COOLDOWN = false;
            move(0,-1);
            texture = Assets.playerLeft;
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sokoban/footsteps.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
        if (KeyBoard.DOWN && time > DELAY && KeyBoard.COOLDOWN){
            KeyBoard.COOLDOWN = false;
            move(1,0);
            texture = Assets.playerFront;
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sokoban/footsteps.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
        if (KeyBoard.RIGHT && time > DELAY && KeyBoard.COOLDOWN){
            KeyBoard.COOLDOWN = false;
            move(0,1);
            texture = Assets.playerRight;
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sokoban/footsteps.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
        if (KeyBoard.CTRL && KeyBoard.Z && time > DELAY && KeyBoard.COOLDOWN){
            KeyBoard.COOLDOWN = false;
            if (!(ctrlZ.empty()) && !(ctrlZPlayer.empty())){
                int[][] tpm = ctrlZ.pop();
                this.maze = tpm;
                this.player_col = ctrlZPlayer.pop();
                this.player_row = ctrlZPlayer.pop();
                try {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sokoban/footsteps.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                   
                }
            }      
        }

        restart.update();
        back.update();
        for(int row = 0; row < maze.length;row++){
            for (int col = 0; col < maze[row].length;col ++){
                if (maze[row][col] == 2){
                    return;
                }
            }
        }
        for(int i = 0; i < tab_maze.size(); i++){
            for(int row = 0; row < tab_maze.get(i).length; row++){
                for(int col = 0; col < tab_maze.get(i)[row].length; col++){
                    if(tab_maze.get(i)[row][col] == 2){
                        return;
                    }
                }
            }
        }
        levelSelectorState.getLevels()[id].setSolved(true);
        State.currentState = levelSelectorState;

    }


    private void move(int row,int col) {
        if (maze[player_row + row][player_col + col] != 1) {
            int[][] tpm = copyMatrix(this.maze);
            ctrlZ.push(tpm);
            ctrlZPlayer.push(player_row);
            ctrlZPlayer.push(player_col);
            Boolean tpm1 = true;
            
            if (maze[player_row + row][player_col + col] == 2 || maze[player_row + row][player_col + col] == 4 || maze[player_row + row][player_col + col] > 4) {
                
                if ( maze[player_row + row][player_col + col] < 5 && (maze[player_row + row * 2][player_col + col * 2] == 1 || maze[player_row + row * 2][player_col + col * 2] == 2 || maze[player_row + row * 2][player_col + col * 2] == 4)){
                    return;
                }
                    
                if (maze[player_row + row][player_col + col] == 4) {
                    maze[player_row + row][player_col + col] = 3;
                    if (maze[player_row + row * 2][player_col + col * 2] == 3)
                        maze[player_row + row * 2][player_col + col * 2] = 4;
                    else
                        maze[player_row + row * 2][player_col + col * 2] = 2;
                } 
                if (maze[player_row + row][player_col + col] == 5){
                    //tpm1 = false;
            
                    Runnable task = () -> {
                        boolean tpm2 = true;
                        int i = 0;
                        while (i <= 1400 && tpm2){
                            try {
                                Thread.sleep(100);
                              } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            if(maze[player_row][player_col] != 5){
                                tpm2 = false;
                            }
                            i = i + 100;
                        }
                        

                        if (maze[player_row][player_col] == 5 && tpm2){
                            tab_maze.set(myLevel.pop() - 6, copyMatrix(this.maze));
                            this.maze = copyMatrix(tab_maze.get(tab_maze.size() - 1));
                            tab_maze.remove(tab_maze.size() - 1);
                        
                            player_col = player_pos.get(player_pos.size() - 1);
                            player_row = player_pos.get(player_pos.size() - 2);

                            player_pos.remove(player_pos.size() - 1);
                            player_pos.remove(player_pos.size() - 1);
                        }
                    };
                    player_row += row;
                    player_col += col;
                    thread.execute(task);
                    return;

                }
                if (maze[player_row + row][player_col + col] > 5){
                    isInrecur = maze[player_row + row][player_col + col];
                    myLevel.push(isInrecur);
                    tpm1 = false;
                    tab_maze.add(copyMatrix(this.maze));
                    player_pos.add(player_row);
                    player_pos.add(player_col);
                    this.maze = tab_maze.get(maze[player_row + row][player_col + col] - 6);
                    player_row = player_pos.get(isInrecur - 6);
                    player_col = player_pos.get((isInrecur - 6) + 1);
                    this.maze[player_row][player_col] = 5;
                }
                if (maze[player_row + row][player_col + col] == 2){
                    maze[player_row + row][player_col + col] = 0;
                    if (maze[player_row + row * 2][player_col + col * 2] == 3)
                        maze[player_row + row * 2][player_col + col * 2] = 4;
                    else
                        maze[player_row + row * 2][player_col + col * 2] = 2;
                }
            }
            if (tpm1){
                player_row += row;
                player_col += col;
            }

        }
        time = 0;
    }
    public void render(Graphics g){
        restart.render(g);
        back.render(g);
        for(int row = 0; row < maze.length;row++){
            for (int col = 0; col < maze[row].length;col ++){
                if( maze[row][col] != 6){
                    g.drawImage(Assets.floor,xOffset + col*TILESIZE - 350 ,yOffset + row*TILESIZE,null);
                }
                if(maze[row][col] == 1)
                    g.drawImage(Assets.wall,xOffset + col*TILESIZE - 350 ,yOffset + row*TILESIZE,null);
                if(maze[row][col] == 2)
                    g.drawImage(Assets.boxOff,xOffset + col*TILESIZE - 350,yOffset + row*TILESIZE,null);
                if(maze[row][col] == 3)
                    g.drawImage(Assets.spot,xOffset + col*TILESIZE - 350 ,yOffset + row*TILESIZE,null);
                if(maze[row][col] == 4)
                    g.drawImage(Assets.boxOn,xOffset + col*TILESIZE - 350,yOffset + row*TILESIZE,null);
                if(maze[row][col] == 5)
                    g.drawImage(Assets.box_return,xOffset + col*TILESIZE - 350,yOffset + row*TILESIZE,null);   
                 if(maze[row][col] > 5){
                    int[][] matrix = copyMatrix(tab_maze.get(maze[row][col]-6));
                    int row1,col1;
                    int off =0;
                    int off2 =0;
                    for( row1 = 0; row1 < matrix.length;row1++){
                        for (col1 = 0; col1 < matrix[row].length;col1++){
                                g.drawImage(Assets.floor_rec,xOffset + col*TILESIZE - 350 + off ,yOffset + row*TILESIZE + off2,null);
                            
                            if(matrix[row1][col1] == 1)
                                g.drawImage(Assets.wall_rec,xOffset + col*TILESIZE - 350 + off ,yOffset + row*TILESIZE+ off2,null);
                            if(matrix[row1][col1] == 2)
                                g.drawImage(Assets.boxOff_rec,xOffset + col*TILESIZE - 350 + off,yOffset + row*TILESIZE+ off2,null);
                            if(matrix[row1][col1] == 3)
                                g.drawImage(Assets.spot_rec,xOffset + col*TILESIZE - 350 + off ,yOffset + row*TILESIZE+ off2,null);
                            if(matrix[row1][col1] == 4)
                                g.drawImage(Assets.boxOn_rec,xOffset + col*TILESIZE - 350 + off,yOffset + row*TILESIZE+ off2,null);
                            if(matrix[row1][col1] == 5)
                                g.drawImage(Assets.box_return_rec,xOffset + col*TILESIZE - 350 + off,yOffset + row*TILESIZE+ off2,null);
                            off = off + 12;
    

                        }
                        off2 = off2 + 12;
                        off = 0;


                }
                g.drawImage(Assets.outline3,xOffset + col*TILESIZE - 350,yOffset + row*TILESIZE,null);
                    

            }
        

            }
            g.drawImage(texture,xOffset - 350 + player_col*TILESIZE,yOffset + player_row*TILESIZE,null);
       
    }
}

    public boolean isSolved() {
        return solved;
    }
    public void setSolved(boolean bool){
        solved = bool;
    }
}





