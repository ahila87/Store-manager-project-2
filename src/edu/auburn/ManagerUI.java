package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerUI {
    public JFrame view;

    public JButton btnAddProduct = new JButton("Add New Product");
    public JButton btnUpdateProduct = new JButton("Update Product");
    public JButton viewSummary = new JButton("View Summary Report");
    public JButton btnUpdate = new JButton("Update Information");
    public JButton btnLogout = new JButton("Logout");

    public ManagerUI(UserModel user) {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Manager View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelUser = new JPanel(new FlowLayout());
        panelUser.add(new JLabel("Fullname: " + user.mFullname));
        panelUser.add(new JLabel("Username: " + user.mUsername));

        view.getContentPane().add(panelUser);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddProduct);
        panelButtons.add(btnUpdateProduct);
        panelButtons.add(viewSummary);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnLogout);
        view.getContentPane().add(panelButtons);

        btnUpdateProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateProductUI ui = new UpdateProductUI();
                ui.run();
            }
        });

        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddProductUI ui = new AddProductUI();
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

        viewSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchaseSummaryUI ui = new PurchaseSummaryUI();
                ui.view.setVisible(true);
            }
        });


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