package org.pavlov.demo;

import org.pavlov.model.Account;
import org.pavlov.model.Transaction;
import org.pavlov.model.User;
import org.pavlov.property_reader.PropertiesReader;

import java.sql.*;
import java.util.Scanner;

import static org.pavlov.query.RequestStorage.*;
import static org.pavlov.service.AccountInput.getNewAccount;
import static org.pavlov.service.TransactionInput.getTransaction;
import static org.pavlov.service.UserInput.addNewUser;

public class Application {
    private static final String JDBC_DRIVER_PATH = "org.sqlite.JDBC";
    private static final String URL_KEY = "dbURL";
    private static String URL;

    static{
        URL = PropertiesReader.getValueFromProperty(URL_KEY);
    }

    public static void main(String[] args) throws SQLException {
        if (isDriverExists()) {
            Connection connection = DriverManager.getConnection(URL);
            String actionCode;
            do {
                printMenu();
                actionCode = new Scanner(System.in).nextLine();
                switch (actionCode.trim()) {
                    case "1":
                        User user = addNewUser();
                        registeringNewUser(connection, user);
                        break;
                    case "2":
                        Account account = getNewAccount();
                        addAccountForNewUser(connection, account);
                        break;
                    case "3":
                        Transaction addTransaction = getTransaction();
                        replenishmentOfExistingAccount(connection, addTransaction);
                        break;
                    case "4":
                        Transaction withdrawalTransaction = getTransaction();
                        withdrawalOfFundsFromExistingAccount(connection, withdrawalTransaction);
                        break;
                    case "5":
                        printAllUsers(connection);
                        break;
                    case "6":
                        printAllAccounts(connection);
                        break;
                    case "7":
                        printAllTransactions(connection);
                        break;
                    case "8":
                        break;
                    default:
                        System.out.println("Unknown option. Please, try again");
                }
            } while (!"8".equals(actionCode));
            connection.close();
        }
    }

    private static boolean isDriverExists() {
        try {
            Class.forName(JDBC_DRIVER_PATH);
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not fount");
            return false;
        }
    }

    private static void printMenu() {
        System.out.println("\nPlease, select an action");
        System.out.println("1 - Registering a new user");
        System.out.println("2 - Adding an account to a new user");
        System.out.println("3 - Replenishment of an existing account");
        System.out.println("4 - Withdrawal of funds from an existing account");
        System.out.println("5 - show users");
        System.out.println("6 - show accounts");
        System.out.println("7 - show transactions");
        System.out.println("8 - quit\n");
    }
}
