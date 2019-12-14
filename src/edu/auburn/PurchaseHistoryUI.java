package edu.auburn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PurchaseHistoryUI {

    public JFrame view;
    //public JList purchases;
    public JTable purchaseTable;

    public UserModel user = null;

    public PurchaseHistoryUI(UserModel user) {
        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("View Purchase History - Customer View");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Purchase history for " + user.mFullname);

        title.setFont (title.getFont().deriveFont (24.0f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        view.getContentPane().add(title);

        PurchaseListModel list = StoreManager.getInstance().getDataAdapter().loadPurchaseHistory(user.mCustomerID);

        DefaultTableModel tableData = new DefaultTableModel();

        System.out.println(list);

        tableData.addColumn("PurchaseID");
        tableData.addColumn("ProductID");
        tableData.addColumn("Product Name");
        tableData.addColumn("Total");
        System.out.println("before");
        for (PurchaseModel purchase : list.purchases) {
            System.out.println("inside");
            Object[] row = new Object[tableData.getColumnCount()];
            System.out.println(purchase);
            row[0] = purchase.mPurchaseID;
            row[1] = purchase.mProductID;
            ProductModel product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
            row[2] = product.mName;
            row[3] = purchase.mTotalCost;
            tableData.addRow(row);
        }

        purchaseTable = new JTable(tableData);
        JScrollPane scrollableList = new JScrollPane(purchaseTable);
        view.getContentPane().add(scrollableList);

    }
}