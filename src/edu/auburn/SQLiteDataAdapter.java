package edu.auburn;

import java.sql.*;

public class SQLiteDataAdapter implements IDataAdapter {

    String url = null;
    int errorCode = 0;

    public SQLiteDataAdapter(String path) {
        url = "jdbc:sqlite:" + path;
    }

    public SQLiteDataAdapter() {
        String path = "C:\\Users\\ahila\\IdeaProjects\\StoreManager\\data\\store.db";
        url = "jdbc:sqlite:" + path;
    }

    public boolean connect(String path) {
        try {
             return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
            case CUSTOMER_LOAD_FAILED: return "Cannot load the customer!";
            case PRODUCT_SAVE_FAILED: return "Cannot save the product!";
            case CUSTOMER_SAVE_FAILED: return "Cannot save the customer!";
            case PURCHASE_SAVE_FAILED:return "Cannot save the purchase!";
        }
        ;
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products WHERE ProductId = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = PRODUCT_LOAD_FAILED;
        }
        return product;
    }
    public int saveProduct(ProductModel product) {
        try {

            ProductModel model = loadProduct(product.mProductID);
            Connection conn = DriverManager.getConnection(url);
            String sql;
            Statement stmt = conn.createStatement();
            if (model != null)  // delete this record
                stmt.execute("DELETE FROM Products WHERE ProductId = " + product.mProductID);
            stmt.execute("INSERT INTO Products VALUES " + product);
            stmt.close();
            conn.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return PRODUCT_SAVE_FAILED;
        }
        return PRODUCT_SAVE_OK;
    }

    @Override
    public PurchaseModel loadPurchase(int purchaseId) {
        PurchaseModel purchase = new PurchaseModel();


        try {
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Orders WHERE OrderID = " + purchaseId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            purchase.mPurchaseID = rs.getInt("OrderID");
            purchase.mCustomerID = rs.getInt("CustomerID");
            purchase.mProductID = rs.getInt("ProductID");
            purchase.mQuantity = rs.getInt("Quantity");
            purchase.mPrice = rs.getDouble("Price");
            purchase.mTax = rs.getDouble("TotalTax");
            purchase.mTotalCost = rs.getDouble("TotalCost");
            conn.close();



        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return purchase;
    }
    @Override
    public int savePurchase(PurchaseModel purchase) {
        try {
            Connection conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Orders(OrderID,CustomerID,ProductID,Quantity,Price,TotalTax,TotalCost) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, purchase.mPurchaseID);
            pstmt.setInt(2, purchase.mCustomerID);
            pstmt.setInt(3, purchase.mProductID);
            pstmt.setDouble(4, purchase.mQuantity);
            pstmt.setDouble(5, purchase.mPrice);
            pstmt.setDouble(6, purchase.mTax);
            pstmt.setDouble(7, purchase.mTotalCost);
            pstmt.executeUpdate();
            conn.close();
            return PURCHASE_SAVE_OK;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = PURCHASE_SAVE_FAILED;


        }

        return PURCHASE_SAVE_FAILED;

    }

    public CustomerModel loadCustomer(int id) {
        try {
            CustomerModel c = new CustomerModel();
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customers WHERE CustomerID = " + id);
            c.mCustomerID = rs.getInt("CustomerID");
            c.mName = rs.getString("Name");
            c.mPhone = rs.getString("Phone");
            c.mAddress = rs.getString("Address");
            conn.close();
            return c;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
    }
    public int saveCustomer(CustomerModel customer) {
        try{
            Connection conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Customers(CustomerID,Name,Phone,Address) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customer.mCustomerID);
            pstmt.setString(2, customer.mName);
            pstmt.setString(3,customer.mPhone);
            pstmt.setString(4,customer.mAddress);
            pstmt.executeUpdate();
            conn.close();
            return CUSTOMER_SAVE_OK;


        }catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_SAVE_FAILED;

        }

        return CUSTOMER_SAVE_FAILED;

    }

    @Override
    public int updatePurchase(PurchaseModel purchase) {

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            PurchaseModel p = loadPurchase(purchase.mPurchaseID); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Orders WHERE OrderId = " + purchase.mPurchaseID);
            }
            String sql = "INSERT INTO Orders VALUES " + purchase;
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.close();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            return PURCHASE_SAVE_FAILED;
        }
        return PURCHASE_SAVE_OK;
    }

    @Override
    public int updateCustomer(CustomerModel customerModel) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            CustomerModel p = loadCustomer(customerModel.mCustomerID); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Customers WHERE CustomerId = " + customerModel.mCustomerID);
            }
            String sql = "INSERT INTO Customers VALUES " + customerModel;
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.close();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            return CUSTOMER_SAVE_FAILED;
        }
        return CUSTOMER_SAVE_OK;
    }

    @Override
    public int updateUserInfo(UserModel userModel) {
        int response = USER_SAVE_FAILED;
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = "UPDATE users SET Fullname='"+ userModel.mFullname+"', Password='"+ userModel.mPassword +"' WHERE Username = '"+ userModel.mUsername +"'";
            stmt.executeUpdate(sql);
            response = USER_SAVE_OK;
            conn.close();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            response = USER_SAVE_FAILED;
        }
        return response;
    }

    @Override
    public int updateUser(UserModel userModel) {
        int response = USER_SAVE_FAILED;
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            UserModel p = loadUser(userModel.mUsername); // check if this product exists
            if (p != null) {
               stmt.executeUpdate("DELETE FROM Users WHERE Username = '"+p.mUsername+"'");
            }
            ResultSet rs;
            if (userModel.mUserType == UserModel.CUSTOMER) {
                do {
                    int randomUserId = (int) (Math.random()*(9999 - 1)) + 1;
                    System.out.println(randomUserId);
                    String checkUserId = "SELECT CustomerId from Users where CustomerId = '"+ randomUserId +"'";
                    rs = stmt.executeQuery(checkUserId);
                    userModel.mCustomerID = randomUserId;
                } while(rs.next());
            }

          String sql = "INSERT INTO Users(Username,Password,Fullname,Usertype) VALUES(?,?,?,?)";
            //Statement stmt1 = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userModel.mUsername);
            pstmt.setString(2, userModel.mPassword);
            pstmt.setString(3,userModel.mFullname);
            pstmt.setInt(4,userModel.mUserType);
            // userModel.mCustomerID = 0;

            pstmt.executeUpdate();
            response = USER_SAVE_OK;
            conn.close();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            response = USER_SAVE_FAILED;
        }
        return response;
    }

    @Override
    public PurchaseListModel loadPurchaseHistory(int id) {
        PurchaseListModel res = new PurchaseListModel();
        try {
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Orders WHERE CustomerId = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mCustomerID = id;
                purchase.mPurchaseID = rs.getInt("OrderID");
                purchase.mProductID = rs.getInt("ProductID");
             //   purchase.mPrice = rs.getDouble("Price");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mCost = rs.getDouble("Price");
                purchase.mTax = rs.getDouble("TotalTax");
                purchase.mTotalCost = rs.getDouble("TotalCost");
                purchase.mDate = rs.getString("Date");
                res.purchases.add(purchase);
                System.out.println(purchase);


            }

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;

    }

    @Override
    public ProductListModel searchProduct(String name, double minPrice, double maxPrice) {
        ProductListModel res = new ProductListModel();
        try {
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Products WHERE Name LIKE \'%" + name + "%\' "
                    + "AND Price >= " + minPrice + " AND Price <= " + maxPrice;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Bfwhile");
            while (rs.next()) {
                System.out.println("while");
                ProductModel product = new ProductModel();
                product.mProductID = rs.getInt("ProductID");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
                res.products.add(product);
                System.out.println("bef");
                System.out.println(res);

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("aftr");
        System.out.println(res);
        return res;
    }

    @Override
    public int saveUser(UserModel userModel) {
        try {
           // connect(path);
            Connection conn = DriverManager.getConnection(url);
            ResultSet rs;
            if (userModel.mUserType == UserModel.CUSTOMER) {
                do {
                    int randomUserId = (int) (Math.random()*(9999 - 1)) + 1;
                    String checkUserId = "SELECT CustomerId from Users where CustomerId = '"+ randomUserId +"'";
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(checkUserId);
                    userModel.mCustomerID = randomUserId;
                } while(rs.next());
            }
            String sql = "INSERT INTO Users(Username,Password,Fullname,Usertype) VALUES(?,?,?,?)";
            //Statement stmt1 = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userModel.mUsername);
            pstmt.setString(2, userModel.mPassword);
            pstmt.setString(3,userModel.mFullname);
            pstmt.setInt(4,userModel.mUserType);
           // userModel.mCustomerID = 0;

            pstmt.executeUpdate();
            conn.close();
            return USER_SAVE_OK;
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            return USER_SAVE_FAILED;
        }

        //return USER_SAVE_OK;
    }


    @Override
    public PurchaseListModel loadPurchaseSummary() {
        PurchaseListModel res = new PurchaseListModel();
        try {
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Orders";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mPurchaseID = rs.getInt("OrderId");
                purchase.mCustomerID = rs.getInt("CustomerId");
                purchase.mProductID = rs.getInt("ProductId");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mPrice = rs.getDouble("Price");
                purchase.mTax = rs.getDouble("TotalTax");
                purchase.mCost = rs.getDouble("TotalCost");

                purchase.mDate = rs.getString("Date");
                res.purchases.add(purchase);

            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }



    public UserModel loadUser(String username) {
        UserModel user = null;

        try {
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Users WHERE Username = \"" + username + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("Password");
                user.mFullname = rs.getString("Fullname");
                user.mUserType = rs.getInt("Usertype");
                if (user.mUserType == UserModel.CUSTOMER)
                    user.mCustomerID = rs.getInt("CustomerID");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

}
