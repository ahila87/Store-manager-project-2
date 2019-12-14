package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI {

    public UserModel user = null;

    public JFrame view;

    public JButton btnMakePurchase = new JButton("Add New Purchase");
    public JButton btnViewPurchases = new JButton("View Purchase History");
    public JButton btnSeachProduct = new JButton("Search Product Info");
    public JButton btnUpdate = new JButton("Update Information");
    public JButton btnLogout = new JButton("Logout");

    public CustomerUI(UserModel user) {

        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Customer View");
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
        panelButtons.add(btnMakePurchase);
        panelButtons.add(btnViewPurchases);
        panelButtons.add(btnSeachProduct);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnLogout);

        view.getContentPane().add(panelButtons);


        btnViewPurchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PurchaseHistoryUI ui = new PurchaseHistoryUI(user);
                ui.view.setVisible(true);
            }
        });

        btnMakePurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI ui = new AddPurchaseUI(user);
                ui.run();
            }
        });

        btnSeachProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProductSearchUI ui = new ProductSearchUI(user);
                ui.view.setVisible(true);
            }
        } );

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