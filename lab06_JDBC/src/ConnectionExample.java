import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionExample {

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver failed to load.");
            return;
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql:///inventory", "stock", "check");
            System.out.println("Connected");
            while (true) {
                String commandString = getCommand();
                if (commandString.equals("0")) {
                    System.out.println("User Quit.");
                    break;
                } else if (commandString.equals("1")) {
                    searchSupplier(con);
                } else if (commandString.equals("2")) {
                    insertSupplier(con);
                } else if (commandString.equals("3")) {
                    System.out.println(deleteSupplier(con)
                            + " rows have been deleted.");
                } else if (commandString.equals("4")) {
                    updateSupplier(con);
                    System.out.println("update successed");
                } else {
                    System.out.println("Invalid Command.");
                }
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void updateSupplier(Connection con) throws IOException,
            SQLException {
        // TODO Auto-generated method stub
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("UPDATE `inventory`.`Suppliers` SET ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter Supplier ID: ");
        String supplierID = br.readLine();
        List<String> paras = new ArrayList<>();
        do {
            System.out.print("Enter Field Name: ");
            String field = br.readLine();
            System.out.print("Enter Value: ");
            String value = br.readLine();
            paras.add(value);
            sBuilder.append(field + "= ?, ");
            System.out.print("Continue?(Y or N): ");
        } while (!br.readLine().equalsIgnoreCase("N"));
        sBuilder.deleteCharAt(sBuilder.length() - 2);
        sBuilder.append("WHERE SupplierID = " + supplierID);
        PreparedStatement statement = con.prepareStatement(sBuilder.toString());
        for (int i = 0; i < paras.size(); i++) {
            statement.setString(i + 1, paras.get(i));
        }
        statement.executeUpdate();
    }

    private static int deleteSupplier(Connection con) throws SQLException,
            IOException {
        // TODO Auto-generated method stub
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("DELETE FROM `inventory`.`Suppliers` WHERE SupplierID = ?");
        String sqlString = sBuilder.toString();
        PreparedStatement statement = con.prepareStatement(sqlString);
        System.out.print("Enter Supplier ID: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String supplierID = br.readLine();
        int ID = Integer.parseInt(supplierID);
        statement.setInt(1, ID);
        return statement.executeUpdate();
    }

    private static void insertSupplier(Connection con) throws SQLException,
            IOException {
        // TODO Auto-generated method stub
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("INSERT INTO `inventory`.`Suppliers`");
        sBuilder.append("(`SupplierName`,`ContactName`,`ContactTitle`,`Address`) ");
        sBuilder.append("VALUES(?,?,?,?)");
        String sqlString = sBuilder.toString();
        PreparedStatement statement = con.prepareStatement(sqlString);
        System.out.print("Enter Supplier Name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String supplierName = br.readLine();
        statement.setString(1, supplierName);
        System.out.print("Enter Contact Name: ");
        String contactName = br.readLine();
        statement.setString(2, contactName);
        System.out.print("Enter Contact Title: ");
        String contactTitle = br.readLine();
        statement.setString(3, contactTitle);
        System.out.print("Enter Address: ");
        String address = br.readLine();
        statement.setString(4, address);
        int result = statement.executeUpdate();
        System.out.println(result + " rows get affected.");
        System.out.println();
    }

    private static void searchSupplier(Connection con) throws SQLException,
            IOException {
        String sqlString = "SELECT * FROM inventory.Suppliers where SupplierName like ?";
        PreparedStatement statement = con.prepareStatement(sqlString);
        System.out.print("Enter Supplier Name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String supplierName = br.readLine();
        String para = "%" + supplierName + "%";
        statement.setString(1, para);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.print("SupplierID: " + rs.getInt(1) + "\t");
            System.out.print("SupplierName: " + rs.getString(2) + "\t");
            System.out.print("ContactName: " + rs.getString(3) + "\t");
            System.out.print("ContactTitle: " + rs.getString(4) + "\t");
            System.out.print("Address: " + rs.getString(5) + "\n");
        }
        System.out.println();
    }

    private static String getCommand() {
        System.out.print("1: Search.  ");
        System.out.print("2: Insert.  ");
        System.out.print("3: Delete.  ");
        System.out.print("4: Update.  ");
        System.out.println("0: Quit.");
        System.out.print("Enter command: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        try {
            s = br.readLine();
            return s;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0";
        }

    }
}
