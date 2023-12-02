
//changed for p2 except last function
package view;
import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.util.*;

import javax.swing.border.Border;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;


    /*MAZE GRAPHIC CLASS DRAWN ON A JPANEL*/
    public class MultiMazeWindow extends JPanel
    {
        /*VARIABLES*/
        private static final boolean WALL=false;
        private static final boolean CELL=true;
        boolean[][] maze;
        boolean flashlightMode;
        Point player,player2,exit;
        int height, width;



           // Load images for the wall
    private Image wallImage;
    private Image playerImage;
    private Image exitImage;
    private Image player2Image;
        public MultiMazeWindow(boolean[][] maze, Point player,Point player2, Point exit)
        {
            height=maze.length;
            width=maze[0].length;
            this.maze=maze;
            this.exit=exit;
            this.player=player;
            this.player2=player2;
            flashlightMode=false;

              try {
            // Load the wall image (replace "wall_image.png" with the actual file name)
                  wallImage = ImageIO.read(new File("D:\\Documents\\SCDPROJFINAL\\SCD Project\\MazeRivalry\\view\\grey_wall.png"));
                 // System.out.println("Wall image loaded successfully");

                  // Load the player image
                  playerImage = ImageIO.read(new File("D:\\Documents\\SCDPROJFINAL\\SCD Project\\MazeRivalry\\view\\darkknight.gif"));
                 // System.out.println("Player image loaded successfully");

                  // Load the player2 image
                  player2Image = ImageIO.read(new File("D:\\Documents\\SCDPROJFINAL\\SCD Project\\MazeRivalry\\view\\player2.png"));
                //  System.out.println("Player2 image loaded successfully");

                  // Load the exit image
                  exitImage = ImageIO.read(new File("D:\\Documents\\SCDPROJFINAL\\SCD Project\\MazeRivalry\\view\\Checkpoint.png"));
                 // System.out.println("Exit image loaded successfully");
            
        } catch (IOException e) {
                  System.out.println("error with image");
            e.printStackTrace();
        }
        }
      
        public void setPoints(boolean[][] maze, Point player,Point player2, Point exit)
        {
            height=maze.length;
            width=maze[0].length;
            this.maze = maze;
            this.exit=exit;
            this.player=player;
            this.player2=player2;
            repaint();
        }

        /*REPAINT METHOD*/
        public void paintComponent (Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK); //cover old frame with black rectangle
            g2.fill(new Rectangle2D.Double(0, 0, 700, 700));
            paintMaze(g2); //print new maze graphic
        }

        /*PAINT MAZE*/
        public void paintMaze(Graphics2D g2) {
            /*LOOP THROUGH MAZE 2D ARRAY*/
            for (int y = 0; y < maze.length; y++) {
                for (int x = 0; x < maze[0].length; x++) {
                    /*DETERMINE COLOR*/
                    if (player.getY() == y && player.getX() == x) // player
                        g2.drawImage(playerImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                                (int) (500.0 / width), (int) (500.0 / height), null);
                    else if (exit.getY() == y && exit.getX() == x) //exit
                    //g2.setPaint(Color.BLACK);
                        g2.drawImage(exitImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                                (int) (500.0 / width), (int) (500.0 / height), null);
                    else if (player2.getY() == y && player2.getX() == x)
                        g2.drawImage(player2Image, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                                (int) (500.0 / width), (int) (500.0 / height), null);

                    else if (maze[x][y] == WALL) // wall
                        g2.drawImage(wallImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                                (int) (500.0 / width), (int) (500.0 / height), null);
                    else if (flashlightMode && (x > player.getX() + 3 || y > player.getY() + 3 || x < player.getX() - 3
                            || y < player.getY() - 3)) { // darkness
                        g2.setPaint(Color.black);
                        // Fill the grid cell for darkness
                        g2.fill(new Rectangle2D.Double(x * (500.0 / width), y * (500.0 / height),
                                (500.0 / width), (500.0 / height)));
                    } else { // cell
                        g2.setPaint(Color.black);
                        // Fill the grid cell
                        g2.fill(new Rectangle2D.Double(x * (500.0 / width), y * (500.0 / height),
                                (500.0 / width), (500.0 / height)));
                    }
                }
            }
        }





        public void setOriginalMode()
        {
            flashlightMode=false;
            repaint();
        }

        // public void setFlashlightMode()
        // {
        //     flashlightMode=true;
        //     repaint();
        // }

        public void setPlayer(Point player)
        {
            this.player=player;
            repaint();
        }
        public void setPlayer2(Point player2)
        {
            this.player2=player2;
            repaint();
        }
      /*  public static void main(String[] args)
        {
           new MultiMazeGui();
        }*/
    }




