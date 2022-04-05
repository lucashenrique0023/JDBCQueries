package lab.lhss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/store?useTimezone=true&serverTimezone=UTC";

        try (Connection conn = DriverManager.getConnection(url, "root", "as@12as")) {
            System.out.println("Successfully connected to Database.");

            String sql = "INSERT INTO PRODUCT (NAME) VALUES ('Rice')";

            try (Statement stmt = conn.createStatement()) {
                int lines = stmt.executeUpdate(sql);
                System.out.println(lines + " Product(s) created.");
            }
        }
    }
}
