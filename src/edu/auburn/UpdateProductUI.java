package edu.auburn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProductUI {
    public JFrame view;
    public JTextField txtProductID = new JTextField(20);
    public JTextField showTxtProductID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
  //  public JTextField txtVendor = new JTextField(20);

    public JButton btnFind = new JButton("Find");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnCancel = new JButton("Cancel");

    ProductModel productModel = new ProductModel();

    public UpdateProductUI() {
        view = new JFrame();
        view.setTitle("Update Product");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Product ID:"));
        line1.add(txtProductID);
        line1.add(btnFind);
        pane.add(line1);

        JPanel lineNew = new JPanel(new FlowLayout());
        lineNew.add(new JLabel("ProductID:"));
        lineNew.add(showTxtProductID);
        showTxtProductID.setEnabled(false);
        pane.add(lineNew);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Price:"));
        line3.add(txtPrice);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity:"));
        line4.add(txtQuantity);
        pane.add(line4);

    //    JPanel line5 = new JPanel(new FlowLayout());
      //  line5.add(new JLabel("Vendor:"));
      //  line5.add(txtVendor);
      //  pane.add(line5);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnUpdate);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        btnUpdate.addActionListener(new UpdateProductListener());
        btnFind.addActionListener(new FindProductListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class FindProductListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            productModel = new ProductModel();

            String s = txtProductID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "ProductId could not be EMPTY!!!");
                return;
            }
            try {
                productModel.mProductID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "ProductId is INVALID (not a number)!!!");
                return;
            }

            productModel = StoreManager.getInstance().getDataAdapter().loadProduct(productModel.mProductID);
            if (productModel == null)
                JOptionPane.showMessageDialog(null,
                        "Product not available!");
            else {
                showTxtProductID.setText(String.valueOf(productModel.mProductID));
                txtName.setText(productModel.mName);
                txtPrice.setText(String.valueOf(productModel.mPrice));
                txtQuantity.setText(String.valueOf(productModel.mQuantity));
        //        txtVendor.setText(String.valueOf(productModel.mVendor));
            }

        }
    }

    class UpdateProductListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            productModel.mName = txtName.getText();
            productModel.mPrice = Double.parseDouble(txtPrice.getText());
            productModel.mQuantity = Double.parseDouble(txtQuantity.getText());
          //  productModel.mVendor = txtVendor.getText();

            int res = 0;
            res = StoreManager.getInstance().getDataAdapter().saveProduct(productModel);
            if (res == SQLiteDataAdapter.PRODUCT_SAVE_FAILED)
                JOptionPane.showMessageDialog(null,
                        "Product update failed!");
            else {
                JOptionPane.showMessageDialog(null,
                        "Product is updated successfully!");
            }
        }
    }
}