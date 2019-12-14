package edu.auburn;

public class PurchaseModel {
    public int mPurchaseID, mProductID, mCustomerID;
    public double mPrice, mQuantity, mCost, mTax, mTotalCost;
    public String mDate;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(mPurchaseID).append(",");
        sb.append(mCustomerID).append(",");
        sb.append(mProductID).append(",");

        sb.append(mQuantity).append(",");
        sb.append(mPrice).append(",");
       // sb.append(mCost).append(",");
        sb.append(mTax).append(",");
        sb.append(mTotalCost).append(",");
        sb.append("\"").append(mDate).append("\"").append(")");
        return sb.toString();
    }

}


