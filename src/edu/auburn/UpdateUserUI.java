package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserUI {
    public JFrame view;
    public UserModel userModel = new UserModel();

    public Map<String,Integer> rolesHashMap = new HashMap<String,Integer>();
    public JTextField txtUsername = new JTextField(20);
    public JTextField txtShowUsername = new JTextField(20);
    public JTextField txtFullname = new JTextField(20);
    JComboBox<String> roleList = new JComboBox<>();

    public JPasswordField txtPassword = new JPasswordField(20);
    public JButton btnUpdate = new JButton("Update");
    public JButton btnFind = new JButton("Find");
    public JButton btnCancel = new JButton("Cancel");

    public UpdateUserUI() {
        rolesHashMap.put("Customer", UserModel.CUSTOMER);
        rolesHashMap.put("Cashier", UserModel.CASHIER);
        rolesHashMap.put("Manager", UserModel.MANAGER);

        for (Map.Entry<String,Integer> entry : rolesHashMap.entrySet()) {
            roleList.addItem(entry.getKey());
        }

        view = new JFrame();
        view.setTitle("Update User");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));


        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Username:"));
        line4.add(txtUsername);
        line4.add(btnFind);
        pane.add(line4);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username:"));
        line1.add(txtShowUsername);
        txtShowUsername.setEnabled(false);
        pane.add(line1);

        JPanel line0 = new JPanel(new FlowLayout());
        line0.add(new JLabel("Full Name:"));
        line0.add(txtFullname);
        txtFullname.setEnabled(false);
        pane.add(line0);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Role:"));
        line3.add(roleList);
        pane.add(line3);


        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnUpdate);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        btnFind.addActionListener(new FindUserListener());
        btnUpdate.addActionListener(new AddButtonListener());
//        btnSave.addActionListener(new ManageCustomerUI.SaveButtonListener());
    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String role = roleList.getSelectedItem().toString();
            userModel.mUserType = rolesHashMap.get(role);
            userModel.mCustomerID = -1;
            int res = 0;

            res = StoreManager.getInstance().getDataAdapter().updateUser(userModel);
            if (res==SQLiteDataAdapter.USER_SAVE_FAILED)
                JOptionPane.showMessageDialog(null,
                        "User update failed!");
            else {
                JOptionPane.showMessageDialog(null,
                        "User is updated successfully!");
            }
        }
    }

    class FindUserListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            userModel = new UserModel();
            String s = txtUsername.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Username could not be EMPTY!!!");
                return;
            }
            userModel.mUsername = s;

            userModel = StoreManager.getInstance().getDataAdapter().loadUser(userModel.mUsername);
            if (userModel == null)
                JOptionPane.showMessageDialog(null,
                        "Customer not available!");
            else {
                txtShowUsername.setText(userModel.mUsername);
                txtFullname.setText(userModel.mFullname);
                String role = "";
                for (Map.Entry<String,Integer> entry : rolesHashMap.entrySet()) {
                    if(entry.getValue() == userModel.mUserType) {
                        role = entry.getKey();
                    }
                }
                roleList.setSelectedItem(role);
            }
        }
    }

}