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
        frame.setLocationRelativeTo(null);

        // Creating the background image
        ImageIcon bg = new ImageIcon("D:\\Documents\\SCDPROJFINAL\\SCD Project\\MazeRivalry\\view\\img3.png");
        try {
            Image img = bg.getImage();
            Image temp_imp = img.getScaledInstance(700, 650, Image.SCALE_SMOOTH);
            bg = new ImageIcon(temp_imp);
        } catch (Exception e) {
            System.out.println("Not loaded ");
        }

        JLabel back = new JLabel(bg);
        back.setBounds(0, 0, 700, 650);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        // Adding title label
        JPanel labelPanel = new JPanel();
        labelPanel.setBounds(130, 50, 450, 150);
        labelPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("MAZE RUNNER");
        titleLabel.setBounds(0, 0, 450, 50);
        titleLabel.setFont(new Font("Times Roman", Font.PLAIN, 57));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        labelPanel.add(titleLabel);

        // Adding buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(125, 250, 445, 310);
        buttonPanel.setOpaque(false);
        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.setBackground(Color.WHITE);
        singlePlayerButton.setForeground(Color.black);
        singlePlayerButton.setFont(new Font("Times Roman", Font.PLAIN, 19));
        singlePlayerButton.setPreferredSize(new Dimension(200, 50));
        singlePlayerButton.addActionListener(e -> {
            try {
                showNameDialog("Single Player");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setPreferredSize(new Dimension(200, 50));
        multiplayerButton.setBackground(Color.WHITE);
        multiplayerButton.setForeground(Color.black);
        multiplayerButton.setFont(new Font("Times Roman", Font.PLAIN, 19));
        multiplayerButton.addActionListener(e -> {
            try {
                showNameDialog("Multiplayer");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiplayerButton);

        // Add components to layered pane
        layeredPane.add(back, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(labelPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        frame.setContentPane(layeredPane);

        // Making the frame visible
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

           boolean exists,multi;
           exists=dbCON.enterPlayer(names[0]);
            System.out.println(names[0]);
            dbCON.display();
            if (mode.equals("Single Player")) {
                multi=false;
                if(exists==true)
                this.NewGameBox(multi);
                new MazeGUI();

            } else {
                multi=true;
                dbCON.enterPlayer(names[1]);
                this.NewGameBox(multi);
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

    public void NewGameBox(Boolean multiP) throws SQLException {
        JFrame frame = new JFrame("Game Dialog");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(300, 200);

        // Show a dialog box to the user
        int choice = JOptionPane.showOptionDialog(
                frame,
                "Do you want to start a new game or continue the old one?",
                "Game Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Start New Game", "Continue Old Game"},
                "Start New Game");
         //  frame.setVisible(true);
        if (choice == JOptionPane.YES_OPTION) {
            System.out.println("Starting a new game...");
            dbCON.resetScore(names[0]);
            if(multiP==true)
                dbCON.resetScore(names[1]);
            JOptionPane.showMessageDialog(frame, "Game Reset Enjoy!");
        } else if (choice == JOptionPane.NO_OPTION) {
            frame.setVisible(false);
        }

    }}


