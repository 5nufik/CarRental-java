package carRental;

import java.sql.*;

public class DBHandler {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kurs?serverTimezone=Europe/Moscow&useSSL=false", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete(String db, String idName, String id) throws SQLException {
        PreparedStatement prst = getConnection().prepareStatement("delete from " + db + " where " + idName + " = ?");

        prst.setString(1, id);

        prst.executeUpdate();
    }
}
