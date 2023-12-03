package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DbConnection {
    Statement stmt;
    ResultSet st;
    Connection con;
    public DbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazerunnerdb", "root", "zodiac");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st = stmt.executeQuery("SELECT * FROM playertable");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error establishing database connection", e);
        }
    }

    public boolean enterPlayer(String playerName) {
        try {
            boolean exists = false;
            st.beforeFirst();
            while (st.next()) {
                if (st.getString("playerName").equals(playerName)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                st.moveToInsertRow();
                st.updateString("playerName", playerName);
                st.updateInt("score", 0);
                st.insertRow();
                st.moveToCurrentRow();
            }

            return exists;
        } catch (SQLException e) {
            throw new RuntimeException("Error entering player", e);
        }
    }



    public void display() throws SQLException {
        try {
            st.first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        do
        {
            try {
                System.out.println(st.getString(1)+" "+st.getInt(2));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } while(st.next());
    }

    public void setScore(String player) throws SQLException {
        st.first();
        do {
            if (st.getString(1).equals(player)) {
                int curr=st.getInt(2);
                curr++;
                st.updateInt(2,curr);
                st.updateRow();

                break; // No need to continue if the player is found
            }
        } while (st.next());
    }
    public void resetScore(String player) throws SQLException {
        st.first();
        do {
            if (st.getString(1).equals(player)) {
                int curr=0;
                st.updateInt(2,curr);
                st.updateRow();

                break; // No need to continue if the player is found
            }
        } while (st.next());
    }
    public int getScore(String player) throws SQLException {
        st.first();
        do {
            if (st.getString(1).equals(player)) {

                return st.getInt(2);

            }
        } while (st.next());
        return 0;
    }

}

