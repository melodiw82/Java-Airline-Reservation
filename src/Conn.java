import java.sql.*;
import java.util.Scanner;

public class Conn {

    Connection connection = null;
    Statement statement = null;

    public static void main(String[] args) {
        Conn conn = new Conn();
    }

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flightData", "root", "zari7294");
            System.out.println("> Database connection established");
            System.out.println();
            statement = connection.createStatement();


            // insert into table
//            Scanner sc = new Scanner(System.in);
//            String flight = sc.next();
//            String origin = sc.next();
//            String destination = sc.next();
//            String date = sc.next();
//            String time = sc.next();
//            System.out.println("> int");
//            int price = sc.nextInt();
//            int seat = sc.nextInt();
//
//            String query = "INSERT INTO flights VALUES('" + flight + "', '" + origin + "', '" + destination + "', '" + date + "', '" + time + "', '" + price + "', '" + seat + "')";
//            statement.execute(query);
//            statement.close();


            // insert into prepared statement
//            Scanner sc = new Scanner(System.in);
//            String flight = sc.next();
//            int price = sc.nextInt();
//
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO flights VALUES (?, ?, ?, ?, ?, ?, ?)");
//            statement.setString(1, flight);
//            statement.setInt(6, price);
//            // 7 statements
//            statement.execute();
//            System.out.println("> Insertion completed");
//            statement.close();


            // delete
//            String query = "DELETE FROM flights WHERE flight_id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, "ds");
//            statement.executeUpdate();
//            System.out.println("> Executed");
//            statement.close();


            // update
//            PreparedStatement statement = connection.prepareStatement("UPDATE flights SET price = ? WHERE flight_id = ?");
//            statement.setInt(1,  4000000);
//            statement.setString(2, "WX-12");
//            statement.executeUpdate();
//            System.out.println("> Executed");
//            statement.close();

            // search
//            String query = "SELECT * FROM flights WHERE flight_id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, "WX-12");
//            ResultSet rs = statement.executeQuery();
//            if (rs.next()) {
//                String flightId = rs.getNString("flight_id");
//                String origin = rs.getString("origin");
//                String destination = rs.getString("destination");
//                Date date = rs.getDate("date");
//                Time time = rs.getTime("time");
//                int price = rs.getInt("price");
//                int seat = rs.getInt("seat");
//                System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flightId, origin, destination, date, time, price, seat);
//            }
//            statement.close();


            // flight schedule
//            ResultSet rs = statement.executeQuery("SELECT * FROM flights ORDER BY date");
//            while (rs.next()) {
//                String flightId = rs.getNString("flight_id");
//                String origin = rs.getString("origin");
//                String destination = rs.getString("destination");
//                Date date = rs.getDate("date");
//                Time time = rs.getTime("time");
//                int price = rs.getInt("price");
//                int seat = rs.getInt("seat");
//                System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flightId, origin, destination, date, time, price, seat);
//            }
//            statement.close();

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}