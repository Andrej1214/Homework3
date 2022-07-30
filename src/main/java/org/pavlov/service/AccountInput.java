package org.pavlov.service;

import org.pavlov.model.Account;

import static org.pavlov.utils.CheckInput.*;
import static org.pavlov.utils.CommonStringUtils.*;

import java.util.Scanner;

public class AccountInput {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FOR_USER_ID = "user's id";
    private static final String FOR_CURRENCY = "the name of the currency";

    public static Account getNewAccount() {
        Account account = new Account();
        boolean valid = true;
        do {
            try {
                System.out.println("enter user's id");
                String inputId = scanner.nextLine();
                inputId = checkInputIsNotEmpty(inputId, FOR_USER_ID);
                inputId = checkInputIsNumber(inputId);
                account.setUserId(Integer.parseInt(inputId.trim()));
                System.out.println("enter user's balance");
                String input = scanner.nextLine();
                input = checkInputIsNumberInDouble(input);
                input = replaceCommaOnDot(input);
                double inputBalanceInDouble = Double.parseDouble(input);
                account.setBalance(inputBalanceInDouble);
                System.out.println("enter currency");
                String inputCurrency = scanner.nextLine();
                inputCurrency = checkInputIsNotEmpty(inputCurrency,FOR_CURRENCY);
                inputCurrency = checkCurrencyInput(inputCurrency);
                account.setCurrency(inputCurrency);
            } catch (Exception e) {
                System.out.println("Invalid data, try again");
                deleteWrongInput();
            }
        } while (!valid);
        return account;
    }
}
