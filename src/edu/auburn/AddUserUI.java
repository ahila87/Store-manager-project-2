package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AddUserUI {
    public JFrame view;

    public Map<String,Integer> rolesHashMap = new HashMap<String,Integer>();
    public JTextField txtUsername = new JTextField(20);
    public JTextField txtFullname = new JTextField(20);
    JComboBox<String> roleList = new JComboBox<>();


    public JPasswordField txtPassword = new JPasswordField(20);
    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public AddUserUI() {
        rolesHashMap.put("Customer", UserModel.CUSTOMER);
        rolesHashMap.put("Cashier", UserModel.CASHIER);
        rolesHashMap.put("Manager", UserModel.MANAGER);

        for (Map.Entry<String,Integer> entry : rolesHashMap.entrySet()) {
            roleList.addItem(entry.getKey());
        }

        view = new JFrame();
        view.setTitle("Add User");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username:"));
        line1.add(txtUsername);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Password:"));
        line2.add(txtPassword);
        pane.add(line2);

        JPanel line0 = new JPanel(new FlowLayout());
        line0.add(new JLabel("Full Name:"));
        line0.add(txtFullname);
        pane.add(line0);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Role:"));
        line3.add(roleList);
        pane.add(line3);


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
            UserModel userModel = new UserModel();

            String s = txtUsername.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Username could not be EMPTY!!!");
                return;
            }
            userModel.mUsername = s;

            s = String.valueOf(txtPassword.getPassword());
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Password could not be EMPTY!!!");
                return;
            }

            userModel.mPassword = s;

            s = txtFullname.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Name could not be EMPTY!!!");
                return;
            }

            userModel.mFullname = s;

            s = roleList.getSelectedItem().toString();
            userModel.mUserType = rolesHashMap.get(s);
            userModel.mCustomerID = -1;
            int res = 0;
            res = StoreManager.getInstance().getDataAdapter().saveUser(userModel);
            if (res==SQLiteDataAdapter.USER_SAVE_FAILED)
                JOptionPane.showMessageDialog(null,
                        "User save failed!");
            else {
                JOptionPane.showMessageDialog(null,
                        "User is saved successfully!");
            }
        }
    }
}