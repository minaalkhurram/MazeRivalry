package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DbConnection {
Statement stmt;
    ResultSet st;
    Connection con;
   public DbConnection()
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con= DriverManager.getConnection("jdbc:mysql://localhost:3306/mazerunnerdb","root","zodiac");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st=stmt.executeQuery("select * from playertable");



        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int enterPlayer(String player) {
        try {
            boolean exists = false;
            st.first();
            do {
                if (st.getString(1).equals(player)) {
                    exists = true;
                    break; // No need to continue if the player is found
                }
            } while (st.next());

            if (!exists) {
                st.moveToInsertRow();
                st.updateString(1, player);
                st.updateString(2, "0");
                st.insertRow();
                st.moveToCurrentRow();
            }

            return st.getRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
  /*public static void main(String args[]) throws SQLException {
      DbConnection nn=new DbConnection();
  nn.enterPlayer("minaal");
  nn.display();
  }*/

}
