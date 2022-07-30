package org.pavlov.query;

import org.pavlov.model.Account;
import org.pavlov.model.Transaction;
import org.pavlov.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.*;
import static org.pavlov.service.AccountInput.getNewAccount;
import static org.pavlov.service.TransactionInput.getTransaction;

public class RequestStorage {
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM Users";
    private static final String SQL_FIND_ALL_TRANSACTIONS = "SELECT * FROM Transactions";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM Accounts";

    public static void printAllUsers(Connection connection) throws SQLException {
        List<User> usersList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("userId"));
            user.setName(resultSet.getString("name"));
            String userAddress = resultSet.getString("address");
            if (!(userAddress.isEmpty() || (userAddress == null))) {
                user.setAddress(resultSet.getString("address"));
            }
            usersList.add(user);
        }
        System.out.println(usersList);
        resultSet.close();
        statement.close();
    }

    public static void printAllTransactions(Connection connection) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_TRANSACTIONS);
        while (resultSet.next()) {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(resultSet.getInt("transactionId"));
            transaction.setAccountId(resultSet.getInt("accountId"));
            transaction.setAmount(resultSet.getLong("amount"));
            transactionList.add(transaction);
        }
        System.out.println(transactionList);
        resultSet.close();
        statement.close();
    }

    public static void printAllAccounts(Connection connection) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ACCOUNTS);
        while (resultSet.next()) {
            Account account = new Account();
            account.setAccountId(resultSet.getInt("accountId"));
            account.setUserId(resultSet.getInt("userId"));
            account.setBalance(resultSet.getDouble("balance"));
            account.setCurrency(resultSet.getString("currency"));
            accountList.add(account);
        }
        System.out.println(accountList);
        resultSet.close();
        statement.close();
    }

    public static void registeringNewUser(Connection connection, User user) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format("INSERT INTO Users(name,address) VALUES ('%s','%s');",
                user.getName(), user.getAddress()));
        statement.close();
    }

    public static void addAccountForNewUser(Connection connection, Account account) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userId FROM Users");
        List<Integer> listOfUserId = new ArrayList<>();
        while (resultSet.next()) {
            int number = resultSet.getInt("userId");
            listOfUserId.add(number);
        }
        if (listOfUserId.contains(account.getUserId())) {
            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Accounts");
            Map<Integer, String> map = new HashMap<>();
            while (resultSet1.next()) {
                Integer number = resultSet1.getInt("userId");
                String currency = resultSet1.getString("currency");
                map.put(number, currency);
            }
            if (map.containsKey(account.getUserId()) && map.containsValue(account.getCurrency())) {
                System.out.println("One user can have multiple accounts \nonly in different currencies. "
                        + "Try again:");
                Account newAccount = getNewAccount();
                addAccountForNewUser(connection, newAccount);
            } else {
                statement.executeUpdate(format("INSERT INTO Accounts(userId,balance,currency) VALUES ('%s','%s','%s');",
                        account.getUserId(), account.getBalance(), account.getCurrency()));
                statement.close();
            }
        } else {
            System.out.println("User with this id does not exist. Enter correct number");
            Account newAccount = getNewAccount();
            addAccountForNewUser(connection, newAccount);
        }
    }

    public static void replenishmentOfExistingAccount(Connection connection, Transaction transaction)
            throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT accountId FROM Accounts");
        List<Integer> listOfAccountId = new ArrayList<>();
        while (resultSet.next()) {
            int number = resultSet.getInt("accountId");
            listOfAccountId.add(number);
        }
        resultSet.close();
        if (listOfAccountId.stream().anyMatch(x -> x == transaction.getAccountId())) {
            statement.executeUpdate(format("INSERT INTO Transactions(accountId,amount) VALUES ('%s','%s');",
                    transaction.getAccountId(), transaction.getAmount()));
            ResultSet resultSet2 = statement.executeQuery(format("SELECT * FROM Accounts WHERE accountId = '%s';",
                    transaction.getAccountId()));
            double value = resultSet2.getDouble("balance");
            double rez = value + transaction.getAmount();
            if (rez > 2_000_000_000) {
                System.out.println("Balance can't be more than 2_000_000_000. Try again:\n");
                Transaction addTransaction = getTransaction();
                replenishmentOfExistingAccount(connection, addTransaction);
            }
            statement.executeUpdate(format("UPDATE Accounts SET balance ='%s' WHERE accountId = '%s';",
                    rez, transaction.getAccountId()));
            statement.close();
        } else {
            System.out.println("Account with this id does not exist. Enter correct number");
            System.out.println("Existed accounts: " + listOfAccountId);
            Transaction addTransaction = getTransaction();
            replenishmentOfExistingAccount(connection, addTransaction);
        }
    }

    public static void withdrawalOfFundsFromExistingAccount(Connection connection, Transaction transaction)
            throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT accountId FROM Accounts");
        List<Integer> listOfAccountId = new ArrayList<>();
        while (resultSet.next()) {
            int number = resultSet.getInt("accountId");
            listOfAccountId.add(number);
        }
        resultSet.close();
        if (listOfAccountId.stream().anyMatch(x -> x == transaction.getAccountId())) {
            statement.executeUpdate(format("INSERT INTO Transactions(accountId,amount) VALUES ('%s','-%s');",
                    transaction.getAccountId(), transaction.getAmount()));
            ResultSet resultSet2 = statement.executeQuery(format("SELECT * FROM Accounts WHERE accountId = '%s';",
                    transaction.getAccountId()));
            double value = resultSet2.getInt("balance");
            double rez = value - transaction.getAmount();
            System.out.println("balance = " + value);
            System.out.println("rez = " + rez);
            if (rez < 0) {
                System.out.println("Balance less than zero. Unsupported Operation. Try again: \n");
                Transaction withdrawalTransaction = getTransaction();
                withdrawalOfFundsFromExistingAccount(connection, withdrawalTransaction);
            }
            statement.executeUpdate(format("UPDATE Accounts SET balance ='%s' WHERE accountId = '%s';",
                    rez, transaction.getAccountId()));
            statement.close();
        } else {
            System.out.println("Account with this id does not exist. Enter correct number");
            System.out.println("Existed accounts: " + listOfAccountId);
            Transaction withdrawalTransaction = getTransaction();
            withdrawalOfFundsFromExistingAccount(connection, withdrawalTransaction);
        }
    }
}
