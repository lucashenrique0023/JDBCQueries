package lab.lhss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) throws Exception {

        String[] products = { "Cheese", "Beans", "Salt", "Sugar" };

        String url = "jdbc:mysql://localhost:3306/store?useTimezone=true&serverTimezone=UTC";

        try (Connection conn = DriverManager.getConnection(url, "root", "as@12as")) {
            System.out.println("Successfully connected to Database.");

            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO PRODUCT (NAME) VALUES (?)")) {
                for (String product : products) {
                    stmt.setString(1, product);
                    stmt.executeUpdate();
                }
            }
        }

    }
}
