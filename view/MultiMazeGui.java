package view;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.io.File;
import controller.MultiMazeCreator;


public class MultiMazeGui extends JPanel implements ActionListener, KeyListener
{
    //Buttons and Labels
    private JLabel title,levelBoard,player1,player2;
    private MultiMazeWindow mazeDisplay;
    private MultiMazeCreator mazeBuilder;
    private int width, height;
    private JButton flashlightMode, originalMode, increaseSize, decreaseSize,reset, pause, sound;;
    private int level;
    private Clip clip;

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
        window.setSize(680,620);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        /*SIDE BAR PANEL - buttons and level tracker*/
        JPanel sideBar=new JPanel();
        sideBar.setSize(10,600);
        sideBar.setBackground(new Color(0,0,0));
        sideBar.setLayout(new GridLayout(5,1));
        levelBoard=new JLabel("LEVEL: " + level, SwingConstants.CENTER);
        levelBoard.setFont(new Font("Times Roman", Font.PLAIN, 24));
        try {
            player1=new JLabel(MainClass.names[0]+" Score: "+Integer.toString( MainClass.dbCON.getScore(MainClass.names[0])), SwingConstants.CENTER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        player1.setFont(new Font("Times Roman", Font.PLAIN, 20));
        player1.setForeground(Color.CYAN);
        try {
            player2=new JLabel(MainClass.names[1]+" Score: "+Integer.toString( MainClass.dbCON.getScore(MainClass.names[1])), SwingConstants.CENTER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        player2.setFont(new Font("Times Roman", Font.PLAIN, 20));
        player2.setForeground(Color.CYAN);
        levelBoard.setForeground(Color.GREEN);
        sideBar.add(levelBoard);
         sideBar.add(player1);
        sideBar.add(player2);
        sound=new JButton("Sound");
        sound.setFocusable(false);
        sound.addActionListener(this);
        sideBar.add(sound);
        
      /*  pause=new JButton("Pause");
        pause.setFocusable(false);
        pause.addActionListener(this);
        sideBar.add(pause);*/
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
        mazeDisplay.setPreferredSize(new Dimension(1000, 400));
        /*TITLE PANEL*/
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(0,0,0));
        title = new JLabel("MAZE RUNNER");
        title.setFont(new Font("Times Roman", Font.PLAIN, 57));
        title.setForeground(Color.WHITE);
        titleBar.add(title);

        /*CREATE MAIN PANEL (borderlayout) and ADD CHILDREN PANELS*/
        JPanel userInterface = new JPanel();
        userInterface.setLayout(new BorderLayout(10,10));
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

         if(e.getSource()==sound){
           playMusic();
       }
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
    private void playMusic(){
  if(clip != null && clip.isRunning()){
   clip.stop();
  }
  try{
   //change the path according to your need
     File file= new File("/Users/aizakhurram/SCD Project/MazeRivalry/view/music.wav");
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

            try {
                if(mazeBuilder.winPlayer==true){//means player 1 won
                MainClass.dbCON.setScore(MainClass.names[0]);
                    this.WinDialog(MainClass.names[0]);
                }
                else{
                    MainClass.dbCON.setScore(MainClass.names[1]);
                this.WinDialog(MainClass.names[1]);}

                player1.setText(MainClass.names[0]+" Score: "+Integer.toString( MainClass.dbCON.getScore(MainClass.names[0])));
                player2.setText(MainClass.names[1]+" Score: "+Integer.toString( MainClass.dbCON.getScore(MainClass.names[1])));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            level++;
            levelBoard.setText("LEVEL: " + level);
            width=level*5;
            height=level*5;
            mazeBuilder=new MultiMazeCreator(width,height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(),mazeBuilder.getPlayerPos(),mazeBuilder.getPlayerPos(),mazeBuilder.getExit());
        }


    }
    public void WinDialog(String name) {
        JFrame instruction=new JFrame();
        instruction.setTitle("Game Instructions");
        instruction.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        instruction.setSize(300, 150);
        instruction.setLocationRelativeTo(null);

        JLabel label = new JLabel("<html><center> WOHOOOO "+name+" WON !!!</html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        instruction.add(label);
        instruction.setVisible(true);
    }
}
