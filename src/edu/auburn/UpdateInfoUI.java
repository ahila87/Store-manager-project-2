package edu.auburn;

import javax.naming.InsufficientResourcesException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateInfoUI {
    public JFrame view;

    public JTextField txtUserName = new JTextField(20);
    public JTextField txtFullName = new JTextField(20);
    public JPasswordField txtPassword = new JPasswordField(20);

    public JButton btnUpdate = new JButton("Update");
    public JButton btnCancel = new JButton("Cancel");

    UserModel user = new UserModel();

    public UpdateInfoUI(Object object) {

        user = (UserModel) object;
        view = new JFrame();
        view.setTitle("Change username or password");
        view.setSize(1000, 600);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("User name:"));
        txtUserName.setText(user.mUsername);
        txtUserName.setEnabled(false);
        line1.add(txtUserName);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtFullName);
        txtFullName.setText(user.mFullname);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Enter a new Password:"));
        line3.add(txtPassword);
        pane.add(line3);


        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnUpdate);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        btnUpdate.addActionListener(new AddButtonListener());
//        btnSave.addActionListener(new ManageCustomerUI.SaveButtonListener());


    }

    public void run() {
        view.setVisible(true);
    }
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            user.mUsername = txtUserName.getText();
            String s;
            s = txtFullName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "User FullName could not be EMPTY!!!");
                return;
            }
            user.mFullname = s;

            s = String.valueOf(txtPassword.getPassword());
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Password could not be EMPTY!!!");
                return;
            }
            user.mPassword = s;
            int res = 0;
            res = StoreManager.getInstance().getDataAdapter().updateUserInfo(user);

            if (res==SQLiteDataAdapter.USER_SAVE_FAILED)
                JOptionPane.showMessageDialog(null,
                        "Information update failed!");
            else {
                JOptionPane.showMessageDialog(null,
                        "Information is updated successfully!");
            }

        }
    }
}