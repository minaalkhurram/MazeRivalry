package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import java.awt.geom.Ellipse2D;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import controller.MultiMazeCreator;



public class MultiMazeGui extends JPanel implements ActionListener, KeyListener
{
    //Buttons and LAbels
    private JLabel title,levelBoard;
    private MultiMazeWindow mazeDisplay;
    private MultiMazeCreator mazeBuilder;
    private int width, height;
    private JButton flashlightMode, originalMode, increaseSize, decreaseSize,reset;
    private int level;

    public static void main(String[] args)
    {
        new MultiMazeGui();
    }

    public MultiMazeGui()
    {
        /*initialize game variables*/
        level=1;
        width=level*5;
        height=level*5;
        mazeBuilder=new MultiMazeCreator(width,height);
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
        levelBoard=new JLabel("LEVEL: " + level, SwingConstants.CENTER);
        levelBoard.setFont(new Font("Times Roman", Font.PLAIN, 24));
        levelBoard.setForeground(Color.GREEN);
        sideBar.add(levelBoard);
        flashlightMode=new JButton("Flashlight Mode");
        flashlightMode.setFocusable(false);
        flashlightMode.addActionListener(this);
        sideBar.add(flashlightMode);
        originalMode=new JButton("Original Mode");
        originalMode.setFocusable(false);
        originalMode.addActionListener(this);
        sideBar.add(originalMode);
        reset=new JButton("Restart");
        reset.setFocusable(false);
        reset.addActionListener(this);
        sideBar.add(reset);

        /*MAZE DISPLAY - holds MazeWindow*/
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(1,1));
        mazePanel.setBackground(new Color(0,0,0));
        mazeDisplay = new MultiMazeWindow(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getPlayer2Pos(),mazeBuilder.getExit());
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
        if(e.getSource() == originalMode)
            mazeDisplay.setOriginalMode();

        if(e.getSource() == flashlightMode)
            mazeDisplay.setFlashlightMode();

        if(e.getSource() == reset)
        {
            level=1;
            levelBoard.setText("LEVEL: " + level);
            width=level*5;
            height=level*5;
            mazeBuilder=new MultiMazeCreator(width,height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getPlayer2Pos(),mazeBuilder.getExit());
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
        if(e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
            mazeBuilder.movePlayer2(3); // Move down for player2
        }
        if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
            mazeBuilder.movePlayer2(2); // Move left for player2
        }
        if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
            mazeBuilder.movePlayer2(1); // Move right for player2
        }
        if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
            mazeBuilder.movePlayer2(4); // Move up for player2
        }

        /*UPDATE PLAYER DISPLAY*/
        mazeDisplay.setPlayer(mazeBuilder.getPlayerPos());
        mazeDisplay.setPlayer2(mazeBuilder.getPlayer2Pos());

       
        /*MAZE BUILDER*/
        if(mazeBuilder.win())
        {
            level++;
            levelBoard.setText("LEVEL: " + level);
            width=level*5;
            height=level*5;
            mazeBuilder=new MultiMazeCreator(width,height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getPlayerPos(),mazeBuilder.getExit());
        }
    }
}
