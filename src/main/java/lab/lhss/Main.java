package lab.lhss;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/store?useTimezone=true&serverTimezone=UTC";

        getProductsFromDatabase(url);
    }

    private static void createProductsOnDatabase(String url) throws SQLException {
        String[] products = { "Cheese", "Beans", "Salt", "Sugar" };

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

    private static void getProductsFromDatabase(String url) throws SQLException {

        try (Connection conn = DriverManager.getConnection(url, "root", "as@12as")) {
            System.out.println("Successfully connected to Database.");

            String sql = "SELECT ID, NAME FROM PRODUCT";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        System.out.println("ID: " + id + ", NAME: " + name);
                    }
                }
            }
        }

    }


}
