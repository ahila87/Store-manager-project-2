package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI {
    public JFrame view;

    public JButton btnAddUser = new JButton("Add New User");
    public JButton btnUpdateUser = new JButton("Update User");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnLogout = new JButton("Logout");

    public AdminUI(UserModel user) {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Admin View");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        JPanel panelUser = new JPanel(new FlowLayout());
        panelUser.add(new JLabel("Fullname: " + user.mFullname));
        panelUser.add(new JLabel("Username: " + user.mUsername));

        view.getContentPane().add(panelUser);

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddUser);
        panelButtons.add(btnUpdateUser);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnLogout);
        view.getContentPane().add(panelButtons);

        btnUpdateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateUserUI ui = new UpdateUserUI();
                ui.run();
            }
        });

        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddUserUI ui = new AddUserUI();
                ui.run();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateInfoUI ui = new UpdateInfoUI(user);
                ui.run();
            }
        } );

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.setVisible(false);
                LoginUI ui = new LoginUI();
                ui.view.setVisible(true);
            }
        });


    }
}
