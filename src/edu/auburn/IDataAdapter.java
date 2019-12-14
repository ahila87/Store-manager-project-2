package edu.auburn;

import java.util.List;

public interface IDataAdapter {

    public static final int CONNECTION_OPEN_OK = 100;
    public static final int CONNECTION_OPEN_FAILED = 101;

    public static final int PRODUCT_SAVE_OK = 101;
    public static final int PRODUCT_SAVE_FAILED = 102;
    public static final int PRODUCT_SAVE_DUPLICATE = 103;

    public static final int PRODUCT_LOAD_OK = 104;
    public static final int PRODUCT_LOAD_FAILED = 105;
    public static final int PRODUCT_LOAD_ID_NOT_FOUND = 106;

    public static final int CUSTOMER_LOAD_OK = 107;
    public static final int CUSTOMER_LOAD_FAILED = 108;
    public static final int CUSTOMER_LOAD_ID_NOT_FOUND = 109;

    public static final int CUSTOMER_SAVE_OK = 110;
    public static final int CUSTOMER_SAVE_FAILED = 111;
    public static final int CUSTOMER_SAVE_DUPLICATE = 112;

    public static final int CONNECTION_CLOSE_OK = 200;
    public static final int CONNECTION_CLOSE_FAILED = 201;



    public static final int PURCHASE_SAVE_OK = 500;
    public static final int PURCHASE_SAVE_FAILED = 501;


    public static final int USER_SAVE_OK = 700 ;
    public static final int USER_SAVE_FAILED = 701 ;


    public static final int PURCHASE_LOAD_OK = 113;
    public static final int PURCHASE_LOAD_FAILED = 114;
    public static final int PURCHASE_LOAD_ID_NOT_FOUND = 115;


    public boolean connect(String dbfile);
    public boolean disconnect();
    public int getErrorCode();
    public String getErrorMessage();

    public ProductModel loadProduct(int id);
    public int saveProduct(ProductModel model);

    public CustomerModel loadCustomer(int d);
    public int saveCustomer(CustomerModel customer);

    public PurchaseModel loadPurchase(int purchaseId);
    public int savePurchase(PurchaseModel purchase);

    public int updatePurchase(PurchaseModel purchase);
    PurchaseListModel loadPurchaseSummary();

    int updateCustomer(CustomerModel customerModel);

    int updateUserInfo(UserModel userModel);

    int updateUser(UserModel userModel);

    public PurchaseListModel loadPurchaseHistory(int customerID);

    public ProductListModel searchProduct(String name, double minPrice, double maxPrice);

    int saveUser(UserModel userModel);

    public UserModel loadUser(String username);
    //public int saveUser(UserModel user);
}
