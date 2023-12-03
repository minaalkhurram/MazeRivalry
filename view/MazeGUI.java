package view;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.awt.geom.Ellipse2D;
import java.io.File;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;

import controller.DbConnection;
import controller.MazeCreator;
import controller.MultiMazeCreator;
import view.MainClass;


public class MazeGUI extends JPanel implements ActionListener, KeyListener
{
    //Buttons and LAbels
    private JLabel title,levelBoard,scores;
    private MazeWindow mazeDisplay;

    private MazeCreator mazeBuilder;

    private int width, height;
    private JButton flashlightMode, originalMode, increaseSize, decreaseSize,reset, pause, sound;
    private int level;
    private Clip clip;
    // private ImageIcon music;
    private String playerName;

    public MazeGUI() throws SQLException {
        this.MazeGraphics();

    }

    public void MazeGraphics() throws SQLException {
        /*initialize game variables*/
        level=1;
        width=level*5;
        height=level*5;
        mazeBuilder=new MazeCreator(width,height);
        mazeBuilder.createMaze();
        
        /*CREATE AND SETUP WINDOW*/
        JFrame window = new JFrame("Maze Runner Interface");
        window.setBackground(new Color(0,0,0));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Maze Runner Interface");
        window.setSize(655,620);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        /*SIDE BAR PANEL - buttons and level tracker*/
        JPanel sideBar=new JPanel();
        sideBar.setBackground(new Color(0,0,0));
        sideBar.setLayout(new GridLayout(4,1));
        scores=new JLabel("Score: "+Integer.toString( MainClass.dbCON.getScore(MainClass.names[0])), SwingConstants.CENTER);
        scores.setFont(new Font("Times Roman", Font.PLAIN, 24));
        scores.setForeground(Color.CYAN);
        levelBoard=new JLabel("LEVEL: " + level, SwingConstants.CENTER);
        levelBoard.setFont(new Font("Times Roman", Font.PLAIN, 24));
        levelBoard.setForeground(Color.GREEN);
        sideBar.add(levelBoard);
        sideBar.add(scores);
    
        
       // pause=new JButton("Pause");
        //pause.setFocusable(false);
        //pause.addActionListener(this);
        //sideBar.add(pause);
        reset=new JButton("Restart");
        reset.setFocusable(false);
        reset.addActionListener(this);
        sideBar.add(reset);
            sound=new JButton("Sound");
       sound.setFocusable(false);
        sound.addActionListener(this);
        sideBar.add(sound);
        
        /*MAZE DISPLAY - holds MazeWindow*/
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(1,1));
        mazePanel.setBackground(new Color(0,0,0));
        mazeDisplay = new MazeWindow(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getExit());
        mazePanel.add(mazeDisplay);
        
        /*TITLE PANEL*/
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(0,0,0));
        title = new JLabel("MAZE RUNNER");
        title.setFont(new Font("Times Roman", Font.PLAIN, 57));
        title.setForeground(Color.WHITE);
        titleBar.add(title);
        
        /*CREATE MAIN PANEL (borderlayout) and ADD CHILDREN PANELS*/
        JPanel userInterface = new JPanel();
        userInterface.setLayout(new BorderLayout(20,10));
        userInterface.setBackground(new Color(0,0,0));
        userInterface.add(mazeDisplay, BorderLayout.CENTER);
        userInterface.add(titleBar, BorderLayout.NORTH);
        userInterface.add(sideBar, BorderLayout.EAST);
        
        /*FINISH WINDOW*/
        window.add(userInterface);
        window.addKeyListener(this);
        window.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        /*BUTTON LISTENERS*/
        
        if(e.getSource() == reset)
        {
            level=1;
            levelBoard.setText("LEVEL: " + level);
            width=level*5;
            height=level*5;
            mazeBuilder=new MazeCreator(width,height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getExit());
        }
        if(e.getSource()==sound){
            playMusic();
        }
    }
    private void playMusic(){
   if(clip != null && clip.isRunning()){
    clip.stop();
   }
   try{
    //change the path according to your need
      File file= new File("D:\\Documents\\SCD Project\\MazeRivalry\\view\\music.wav");
      AudioInputStream audioin= AudioSystem.getAudioInputStream(file);
      clip= AudioSystem.getClip();
      clip.open(audioin);
      clip.start();

   }catch(Exception e ){
    System.out.println(e);
   }
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {
        /*ARROW CONTROLS*/
        if(e.getExtendedKeyCode()==e.VK_DOWN)
            mazeBuilder.movePlayer(3);
        if(e.getExtendedKeyCode()==e.VK_LEFT)
            mazeBuilder.movePlayer(2);
        if(e.getExtendedKeyCode()==e.VK_RIGHT)
            mazeBuilder.movePlayer(1);
        if(e.getExtendedKeyCode()==e.VK_UP)
            mazeBuilder.movePlayer(4);
        
        /*UPDATE PLAYER DISPLAY*/
        mazeDisplay.setPlayer(mazeBuilder.getPlayerPos());
        
        /*MAZE BUILDER*/
        if(mazeBuilder.win())
        {
            level++;
            try {
                MainClass.dbCON.setScore(MainClass.names[0]);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            levelBoard.setText("LEVEL: " + level);
            try {
                scores.setText("Score: "+Integer.toString( MainClass.dbCON.getScore(MainClass.names[0])));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            width=level*5;
            height=level*5;
            mazeBuilder=new MazeCreator(width,height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getExit());
        }
    }
}