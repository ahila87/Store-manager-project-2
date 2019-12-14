package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateCustomerUI {
    public JFrame view;
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtCustomerIDDisplay = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);

    public JButton btnFind = new JButton("Find");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnCancel = new JButton("Cancel");

    CustomerModel customerModel = new CustomerModel();

    public UpdateCustomerUI(Object object) {
        view = new JFrame();
        view.setTitle("Update Customer");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID:"));
        line1.add(txtCustomerID);
        line1.add(btnFind);
        pane.add(line1);

        JPanel lineNew = new JPanel(new FlowLayout());
        lineNew.add(new JLabel("CustomerID:"));
        lineNew.add(txtCustomerIDDisplay);
        txtCustomerIDDisplay.setEnabled(false);
        pane.add(lineNew);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address:"));
        line3.add(txtAddress);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone:"));
        line4.add(txtPhone);
        pane.add(line4);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnUpdate);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        btnUpdate.addActionListener(new UpdateCustomerListener());
        btnFind.addActionListener(new FindCustomerListener());


    }

    public void run() {
        view.setVisible(true);
    }

    class FindCustomerListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            customerModel = new CustomerModel();

            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerId could not be EMPTY!!!");
                return;
            }
            try {
                customerModel.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerId is INVALID (not a number)!!!");
                return;
            }

            customerModel = StoreManager.getInstance().getDataAdapter().loadCustomer(customerModel.mCustomerID);
            if (customerModel == null)
                JOptionPane.showMessageDialog(null,
                        "Customer not available!");
            else {
                txtCustomerIDDisplay.setText(String.valueOf(customerModel.mCustomerID));
                txtName.setText(customerModel.mName);
                txtAddress.setText(customerModel.mAddress);
                txtPhone.setText(customerModel.mPhone);
            }

        }
    }

    class UpdateCustomerListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            customerModel.mName = txtName.getText();
            customerModel.mAddress = txtAddress.getText();
            customerModel.mPhone = txtPhone.getText();
            int res = 0;
            res = StoreManager.getInstance().getDataAdapter().updateCustomer(customerModel);
            if (res == SQLiteDataAdapter.CUSTOMER_SAVE_FAILED)
                JOptionPane.showMessageDialog(null,
                        "Customer update failed!");
            else {
                JOptionPane.showMessageDialog(null,
                        "Customer is updated successfully!");
            }
        }
    }
}
