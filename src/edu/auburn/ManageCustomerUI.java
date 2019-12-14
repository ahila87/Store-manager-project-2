package edu.auburn;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCustomerUI {
    public JFrame view;
    //  public IDataAccess dataAdapter;
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtCustomerName = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");


    public ManageCustomerUI() {
        view = new JFrame();
        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnLoad);
        buttonPane.add(btnSave);
        pane.add(buttonPane);


        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID:"));
        line.add(txtCustomerID);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Name:"));
        line.add(txtCustomerName);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Phone:"));
        line.add(txtPhone);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Address:"));
        line.add(txtAddress);
        pane.add(line);


        btnLoad.addActionListener(new ManageCustomerUI.LoadButtonListener());
        btnSave.addActionListener(new ManageCustomerUI.SaveButtonListener());


    }
    public void run(){
        view.setVisible(true);


    }

    private  class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            String s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID is INVALID (not a number)!!!");
                return;
            }
            IDataAdapter adapter = StoreManager.getInstance().getDataAdapter();
            customer = adapter.loadCustomer(customer.mCustomerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Customer NOT exists!");
                return;
            }
            txtCustomerName.setText(customer.mName);
            txtPhone.setText(customer.mPhone);
            txtAddress.setText(customer.mAddress);

        }
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            CustomerModel customer = new CustomerModel();
            String s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID is INVALID (not a number)!!!");
                return;
            }

            s = txtCustomerName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Customer Name could not be EMPTY!!!");
                return;
            }
            customer.mName = s;

            s = txtPhone.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Phone could not be EMPTY!!!");
                return;
            }
            customer.mPhone = s;


            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address could not be EMPTY!!!");
                return;
            }
            customer.mAddress = s;



            try {
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_CUSTOMER;
                msg.data = gson.toJson(customer);

//                msg = StoreManager.getInstance().getNetworkAdapter().send(msg, "localhost", 1000);

                if (msg.code == MessageModel.OPERATION_FAILED)
                    JOptionPane.showMessageDialog(null, "Product is NOT saved successfully!");
                else
                    JOptionPane.showMessageDialog(null, "Product is SAVED successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
