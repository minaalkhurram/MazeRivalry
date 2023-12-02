package view;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

import controller.DbConnection;

public class MainClass {
    static DbConnection dbCON;

    public static String[] names;
    public static void main(String[] args)
    {

        dbCON=new DbConnection();
       MainClass nn=new MainClass();
       nn.UserFrame();


    }
    public void UserFrame() {
        JFrame frame = new JFrame("Maze Runner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 650);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);


        JLabel titleLabel = new JLabel("MAZE RUNNER");
        titleLabel.setBounds(90, 30, 450, 50);
        titleLabel.setFont(new Font("Times Roman", Font.PLAIN, 57));
        titleLabel.setForeground(Color.white);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.setBounds(80, 200, 180, 60);
        singlePlayerButton.addActionListener(e -> {
            try {
                showNameDialog("Single Player");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setBounds(310, 200, 180, 60);
        multiplayerButton.addActionListener(e -> {
            try {
                showNameDialog("Multiplayer");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
//adding to frame
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setForeground(Color.BLACK);
        frame.add(titleLabel);
        frame.add(singlePlayerButton);
        frame.add(multiplayerButton);

        frame.setVisible(true);


    }

    private  void showNameDialog(String mode) throws SQLException {
        JFrame dialogFrame = new JFrame();
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        if (mode.equals("Single Player")) {
            names = new String[1];
            names[0] = JOptionPane.showInputDialog(dialogFrame, "Enter your name for Single Player:");

        } else {
            names = new String[2];
            names[0] = JOptionPane.showInputDialog(dialogFrame, "Enter Player 1's name:");
            names[1] = JOptionPane.showInputDialog(dialogFrame, "Enter Player 2's name:");
        }

        if (names[0] != null && !names[0].isEmpty() && (mode.equals("Single Player") || (names[1] != null && !names[1].isEmpty()))) {

            dbCON.enterPlayer(names[0]);
            System.out.println(names[0]);
            dbCON.display();
            if (mode.equals("Single Player")) {
              //  playerName=names[0];
                new MazeGUI();

            } else {
                dbCON.enterPlayer(names[1]);
             //   this.InstructionsDialog();
               // JOptionPane.showMessageDialog(dialogFrame, "<html>Instructions ! <br> Arrow keys up, down, left, right for <html>" + names[0] + " <html><br> Keys W,A,S,D for <html> " + names[1] );
                new MultiMazeGui();
                this.InstructionsDialog();
            }

        } else {
            JOptionPane.showMessageDialog(dialogFrame, "Please enter valid names!");
        }
    }
    public void InstructionsDialog() {
        JFrame instruction=new JFrame();
        instruction.setTitle("Game Instructions");
        instruction.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        instruction.setSize(300, 150);
        instruction.setLocationRelativeTo(null);

        JLabel label = new JLabel("<html><center> INSTRUCTIONS !!! <br>"+names[0]+" : Arrow keys (Up, Down, Left, Right)<br>"+names[1]+": Keys (W, A, S, D)</center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        instruction.add(label);
        instruction.setVisible(true);
    }
}
