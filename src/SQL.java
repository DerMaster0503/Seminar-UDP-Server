import java.sql.*;
import java.util.ArrayList;

public class SQL {

    Connection con;
    Statement statement;

    ArrayList<room> rooms;


    public SQL(String username, String password) throws SQLException, ClassNotFoundException {
        System.out.println("SQL Connecting.... ");
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://192.168.50.2:5432/sensors", username, password);
        System.out.println("SQL Connected");
        rooms = new ArrayList<>();
        this.init_rooms();
    }

    public void init_rooms() throws SQLException {
        System.out.println("Initialising rooms");
        statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM room");
        while (resultSet.next()){
            rooms.add(new room(resultSet.getInt("id"), resultSet.getString("name")));
        }
        System.out.println("Initialising completed");
        statement.close();
    }

    public void addReadings(int id, String type, int value) throws SQLException {
        statement = con.createStatement();
        statement.execute("INSERT INTO sensor_readings (room_id, sensor_type, value, time) VALUES ("+id+", "+type+", "+value+", "+System.currentTimeMillis()+")");
        statement.close();
    }

    public void close() throws SQLException {
        System.out.println("Stopping SQL");
        statement.close();
        con.close();
        System.out.println("SQL stopped");
    }
}

record room(int id, String name){}