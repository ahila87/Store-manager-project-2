package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddProductUI {
    public JFrame view;
    public JTextField txtProductId = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
  //  public JTextField txtVendor = new JTextField(20);
    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");


    public AddProductUI() {
        view = new JFrame();
        view.setTitle("Add Product");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line0 = new JPanel(new FlowLayout());
        line0.add(new JLabel("Product Id:"));
        line0.add(txtProductId);
        pane.add(line0);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Product Name:"));
        line1.add(txtName);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Price:"));
        line2.add(txtPrice);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Quantity:"));
        line3.add(txtQuantity);
        pane.add(line3);

    //    JPanel line4 = new JPanel(new FlowLayout());
      //  line4.add(new JLabel("Vendor:"));
       // line4.add(txtVendor);
       // pane.add(line4);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnAdd);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        btnAdd.addActionListener(new AddButtonListener());
//        btnSave.addActionListener(new ManageCustomerUI.SaveButtonListener());


    }

    public void run() {
        view.setVisible(true);
    }
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();

            String s = txtProductId.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "ProductID could not be EMPTY!!!");
                return;
            }
            try {
                product.mProductID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "ProductID is INVALID (not a number)!!!");
                return;
            }

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Product Name could not be EMPTY!!!");
                return;
            }
            product.mName = s;

            s = txtPrice.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Price could not be EMPTY!!!");
                return;
            }

            try {
                product.mPrice = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Price is INVALID (not a number)!!!");
                return;
            }

            s = txtQuantity.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Quantity could not be EMPTY!!!");
                return;
            }

            try {
                product.mQuantity = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Quantity is INVALID (not a number)!!!");
                return;
            }

         //   s = txtVendor.getText();
          //  if (s.length() == 0) {
           //     JOptionPane.showMessageDialog(null,
            //            "Vendor could not be EMPTY!!!");
             //   return;
            //}
           // product.mVendor = s;

            int res = StoreManager.getInstance().getDataAdapter().saveProduct(product);
            if (res == IDataAdapter.PRODUCT_SAVE_OK)
                JOptionPane.showMessageDialog(null,
                        "Product is saved successfully!");
            else {
                JOptionPane.showMessageDialog(null,
                        "Product saved failed!");
            }

        }
    }
}
