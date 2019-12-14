package edu.auburn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchUI {

    public JFrame view;
    public JTable productTable;
    public UserModel user = null;
    DefaultTableModel tableData = new DefaultTableModel();

    public JButton btnSearch = new JButton("Search");
    public JLabel productName = new JLabel("Product name: ");
    public JTextField txtName = new JTextField(10);
    public JLabel priceMin = new JLabel("Minimum price: ");
    public JTextField txtMin = new JTextField(10);
    public JLabel priceMax = new JLabel("Maximum price: ");
    public JTextField txtMax = new JTextField(10);

    public ProductSearchUI(UserModel user) {
        this.user = user;
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.setTitle("Search Product");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        view.getContentPane().add(productName);
        view.getContentPane().add(txtName);
        view.getContentPane().add(priceMin);
        view.getContentPane().add(txtMin);
        view.getContentPane().add(priceMax);
        view.getContentPane().add(txtMax);
        view.getContentPane().add(btnSearch);
        btnSearch.addActionListener(new ProductSearchUI.AddButtonListerner());
        tableData.addColumn("ProductID");
        tableData.addColumn("Product Name");
        tableData.addColumn("Price");
        tableData.addColumn("Quantity");
        txtMax.setText(String.valueOf(999));
        txtMin.setText(String.valueOf(0));
        productTable = new JTable(tableData);
        JScrollPane scrollableList = new JScrollPane(productTable);
        view.getContentPane().add(scrollableList);
    }

    class AddButtonListerner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            tableData.setRowCount(0);
            String productName = txtName.getText();
            String minPrice = txtMin.getText();
            String maxPrice = txtMax.getText();
            ProductListModel list = StoreManager.getInstance().getDataAdapter().searchProduct(productName, Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
            for (ProductModel p : list.products) {
                Object[] row = new Object[tableData.getColumnCount()];
                row[0] = p.mProductID;
                row[1] = p.mName;
                row[2] = p.mPrice;
                row[3] = p.mQuantity;
                tableData.addRow(row);
            }
            productTable = new JTable(tableData);
            JScrollPane scrollableList = new JScrollPane(productTable);
            view.getContentPane().add(scrollableList);
        }
    }
}