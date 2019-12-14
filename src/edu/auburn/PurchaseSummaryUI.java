package edu.auburn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PurchaseSummaryUI {

    public JFrame view;
    //public JList purchases;
    public JTable purchaseTable;

    public UserModel user = null;

    public PurchaseSummaryUI() {

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("View Purchase Summary - Manager View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        PurchaseListModel list = StoreManager.getInstance().getDataAdapter().loadPurchaseSummary();
        DefaultTableModel tableData = new DefaultTableModel();

        tableData.addColumn("Purchase ID");
        tableData.addColumn("Date");
        tableData.addColumn("Customer Name");
        tableData.addColumn("Product Name");
        tableData.addColumn("Product Price");
        tableData.addColumn("Quantity");
        tableData.addColumn("Total Tax");
        tableData.addColumn("Total Cost");

        for (PurchaseModel purchase : list.purchases) {
            Object[] row = new Object[tableData.getColumnCount()];
            row[0] = purchase.mPurchaseID;
            row[1] = purchase.mDate;
            ProductModel product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
            System.out.println(purchase.mCustomerID);
            CustomerModel customerModel = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);
            if(customerModel!=null) {row[2] = customerModel.mName;}
            row[3] = product.mName;
            row[4] = product.mPrice;
            row[5] = purchase.mQuantity;
            row[6] = purchase.mTax;
            row[7] = purchase.mCost;
            tableData.addRow(row);
        }

        purchaseTable = new JTable(tableData);

        JScrollPane scrollableList = new JScrollPane(purchaseTable);

        view.getContentPane().add(scrollableList);


    }
}