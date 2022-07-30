package org.pavlov.model;

public class Transaction {
    private static final int MAX_AMOUNT = 100_000_000;
    private static final int MIN_AMOUNT = - 100_000_000;

    private int transactionId;
    private int accountId;
    private double amount;

    public Transaction() {
    }

    public Transaction(int transactionId, int accountId, double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        if (amount > MIN_AMOUNT && amount < MAX_AMOUNT) {
            this.amount = amount;
        } else {
            System.out.println("amount can't be more than 100_000_000");
        }
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount > MIN_AMOUNT && amount < MAX_AMOUNT) {
            this.amount = amount;
        } else {
            System.out.println("amount can't be more than 100_000_000");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (transactionId != that.transactionId) return false;
        if (accountId != that.accountId) return false;
        return Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = transactionId;
        result = 31 * result + accountId;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transactionId=").append(transactionId);
        sb.append(", accountId=").append(accountId);
        sb.append(", amount=").append(amount);
        sb.append("} \n");
        return sb.toString();
    }
}
