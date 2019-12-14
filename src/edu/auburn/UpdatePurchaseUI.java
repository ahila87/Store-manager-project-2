package edu.auburn;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class UpdatePurchaseUI {
    public JFrame view;

    public JButton btnFind = new JButton("Find");
    public JButton btnAdd = new JButton("update");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);
    public JTextField txtPrice = new JTextField(10);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerId = new JLabel("Customer Id: ");
    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");
    public JLabel labProductId = new JLabel("Product Id: ");
    public JLabel loadPurchaseName = new JLabel("Purchase Name: ");

    public JLabel loadDate = new JLabel("Date ");

    public JLabel labCost = new JLabel("Cost: $0.00 ");
    public JLabel labTax = new JLabel("Tax: $0.00");
    public JLabel labTotalCost = new JLabel("Total Cost: $0.00");

    ProductModel product;
    PurchaseModel purchase = new PurchaseModel();
    CustomerModel customer;


    public UpdatePurchaseUI() {

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("PurchaseID "));
        line.add(txtPurchaseID);
        line.add(btnFind);
        view.getContentPane().add(line);

        btnFind.addActionListener(new FindButtonListener());

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID "));
        line.add(txtCustomerID);
        txtCustomerID.setEnabled(false);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID "));
        line.add(txtProductID);
        txtProductID.setEnabled(false);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity "));
        line.add(txtQuantity);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Price "));
        line.add(txtPrice);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labTax);
        line.add(labTotalCost);
        view.getContentPane().add(line);

        txtQuantity.setText(String.valueOf(0));
        txtPrice.setText(String.valueOf(0));
        txtQuantity.getDocument().addDocumentListener(new QuantityChangeListener());
        txtPrice.getDocument().addDocumentListener(new PriceChangeListener());

        line = new JPanel(new FlowLayout());
        line.add(btnAdd);
        line.add(btnCancel);
        view.getContentPane().add(line);

        btnAdd.addActionListener(new AddButtonListerner());
    }

    public void run() {
        purchase.mDate = Calendar.getInstance().getTime().toString();
        labDate.setText("Date of purchase: " + purchase.mDate);
        view.setVisible(true);
    }

    private class FindButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            process();
        }

        private void process() {

            purchase = new PurchaseModel();

            String s = txtPurchaseID.getText();
            if (s.length() == 0) {
                loadPurchaseName.setText("Purchase Name: [not specified!]");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid PurchaseId", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            purchase = StoreManager.getInstance().getDataAdapter().loadPurchase(purchase.mPurchaseID);

            if (purchase == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No purchase with id = " + purchase.mPurchaseID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ADD VALUES TO VIEW
            txtCustomerID.setText(String.valueOf(purchase.mCustomerID));
            txtProductID.setText(String.valueOf(purchase.mProductID));
            txtQuantity.setText(String.valueOf(purchase.mQuantity));
            txtPrice.setText(String.valueOf(purchase.mPrice));

        }
    }

    private class ProductIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }

            System.out.println("ProductID = " + s);

            try {
                purchase.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labProductName.setText("Product Name: ");

                return;
            }

            labProductName.setText("Product Name: " + product.mName);
            purchase.mPrice = product.mPrice;
            labPrice.setText("Product Price: " + product.mPrice);

        }

    }

    private class CustomerIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {

            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                purchase.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labCustomerName.setText("Customer Name: ");

                return;
            }

            labCustomerName.setText("Customer Name: " + customer.mName);

        }

    }

    private class QuantityChangeListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            process();
        }

        public void removeUpdate(DocumentEvent e) {
            process();
        }

        public void insertUpdate(DocumentEvent e) {
            process();
        }

        private void process() {
            String s = txtQuantity.getText();

            if (s.length() == 0) {
                //labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            try {
                purchase.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an invalid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an invalid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            purchase.mCost = purchase.mQuantity * Double.parseDouble(txtPrice.getText());
            purchase.mTax = purchase.mCost * 0.09;
            purchase.mTotalCost = purchase.mCost + purchase.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", purchase.mCost).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", purchase.mTax).trim());
            labTotalCost.setText("Total: $" + String.format("%8.2f", purchase.mTotalCost).trim());

        }
    }

    private class PriceChangeListener implements DocumentListener {

        public void changedUpdate(DocumentEvent e) {
            process();
        }

        public void removeUpdate(DocumentEvent e) {
            process();
        }

        public void insertUpdate(DocumentEvent e) {
            process();
        }

        private void process() {
            String s = txtPrice.getText();

            if (s.length() == 0) {
                //labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            try {
                purchase.mPrice = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter valid price", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter valid price", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            purchase.mCost = Double.parseDouble(txtQuantity.getText()) * purchase.mPrice;
            purchase.mTax = purchase.mCost * 0.09;
            purchase.mTotalCost = purchase.mCost + purchase.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", purchase.mCost).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", purchase.mTax).trim());
            labTotalCost.setText("Total: $" + String.format("%8.2f", purchase.mTotalCost).trim());

        }
    }

    class AddButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            purchase.mDate = Calendar.getInstance().getTime().toString();
            int res = StoreManager.getInstance().getDataAdapter().updatePurchase(purchase);
            if (res == SQLiteDataAdapter.PURCHASE_SAVE_FAILED)
                JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate product ID!");
            else
                JOptionPane.showMessageDialog(null, "Purchase updated successfully!" + purchase);
        }
    }

}
