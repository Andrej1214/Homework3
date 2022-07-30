package org.pavlov.model;

public class Account {
    private static final double MAX_BALANCE = 2_000_000_000;
    private int accountId;
    private int userId;
    private double balance;
    private String currency;

    public Account() {
    }

    public Account(int accountId, int userId, double balance, String currency) {
        this.accountId = accountId;
        this.userId = userId;
        if (balance >= 0 && balance < MAX_BALANCE) {
            this.balance = balance;
        } else {
            System.out.println("balance can't be negative or more than 2_000_000_000");
        }
        this.currency = currency;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0 && balance < MAX_BALANCE) {
            this.balance = balance;
        } else {
            System.out.println("balance can't be negative or more than 2_000_000_000");
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != account.accountId) return false;
        if (userId != account.userId) return false;
        if (Double.compare(account.balance, balance) != 0) return false;
        return currency.equals(account.currency);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = accountId;
        result = 31 * result + userId;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("accountId=").append(accountId);
        sb.append(", userId=").append(userId);
        sb.append(", balance=").append(balance);
        sb.append(", currency='").append(currency).append('\'');
        sb.append("} \n");
        return sb.toString();
    }
}
