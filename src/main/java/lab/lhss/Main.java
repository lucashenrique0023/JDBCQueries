package lab.lhss;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/store?useTimezone=true&serverTimezone=UTC";

        insertProductsAndItens(url);
    }

    private static void insertProductsAndItens(String url) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, "root", "as@12as")) {
            System.out.println("Successfully connected to Database.");

            String sqlProduct = "INSERT INTO PRODUCT (ID, NAME) VALUES (?, ?)";
            String sqlItem = "INSERT INTO ITEM (PRODUCT_ID, PRICE) VALUES (?, ?)";

            try (PreparedStatement stmtProduct = conn.prepareStatement(sqlProduct);
                 PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {

                conn.setAutoCommit(false);

                try {
                    stmtProduct.setInt(1,1);
                    stmtProduct.setString(2,"Rice");
                    stmtProduct.executeUpdate();

                    stmtItem.setInt(1, 1);
                    stmtItem.setDouble(2, 3.50);
                    stmtItem.executeUpdate();

                    Object o = null;
                    o.toString(); // Throws exception

                    stmtItem.setInt(1, 1);
                    stmtItem.setDouble(2, 4.70);
                    stmtItem.executeUpdate();

                    conn.commit();

                } catch (Exception e) {
                    conn.rollback();
                }

            }
        }
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

    private static void insertManyProducts(String url) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, "root", "as@12as")) {
            System.out.println("Successfully connected to Database.");

            String sql = "INSERT INTO PRODUCT (NAME) VALUES (?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 1; i <= 1000; i++) {
                    stmt.setString(1, "Product "+ i);
                    stmt.addBatch();
                }
                // Execute only 1 request
                stmt.executeBatch();
            }
        }
    }


}
