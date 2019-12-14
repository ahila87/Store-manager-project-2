package edu.auburn;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Calendar;

public class AddCustomerUI {
    public JFrame view;
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");


    public AddCustomerUI(Object object) {
        view = new JFrame();
        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));



        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID:"));
        line1.add(txtCustomerID);
        pane.add(line1);

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
            CustomerModel customerModel = new CustomerModel();

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

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Customer Name could not be EMPTY!!!");
                return;
            }
            customerModel.mName = s;

            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address could not be EMPTY!!!");
                return;
            }

            customerModel.mAddress = s;

            s = txtPhone.getText();
            if (s.length() != 10) {
                JOptionPane.showMessageDialog(null,
                        "Invalid Phone Number (Format: 3344444444) !!!");
                return;
            }
            customerModel.mPhone = s;


            int  res = StoreManager.getInstance().getDataAdapter().saveCustomer(customerModel);

            if (res==SQLiteDataAdapter.CUSTOMER_SAVE_OK)
                JOptionPane.showMessageDialog(null,
                        "Customer is saved successfully!");
            else {
                JOptionPane.showMessageDialog(null,
                        "Customer save failed!");
            }

        }
    }
}