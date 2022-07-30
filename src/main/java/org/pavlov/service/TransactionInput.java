package org.pavlov.service;

import org.pavlov.model.Transaction;
import static org.pavlov.utils.CheckInput.*;
import static org.pavlov.utils.CommonStringUtils.*;
import java.util.Scanner;

public class TransactionInput {
    private static Scanner scanner = new Scanner(System.in);
    private static final String FOR_ACCOUNT_ID = "accountId";

    public static Transaction getTransaction() {
        Transaction transaction = new Transaction();
        boolean valid = true;
        do {
            try {
                System.out.println("enter accountId");
                String accountIdInput = scanner.nextLine().trim();
                accountIdInput = checkInputIsNotEmpty(accountIdInput,FOR_ACCOUNT_ID);
                accountIdInput = checkInputIsNumber(accountIdInput);
                int afterValidate = Integer.parseInt(accountIdInput);
                transaction.setAccountId(afterValidate);
                System.out.println("enter value amount");
                String amountInput = scanner.nextLine();
                amountInput = checkInputIsNumberInDouble(amountInput);
                String inputAmount = replaceCommaOnDot(amountInput);
                double inputAmountInDouble = Double.parseDouble(inputAmount);
                transaction.setAmount(inputAmountInDouble);
            } catch (Exception e) {
                System.out.println("Invalid data, try again");
                deleteWrongInput();
            }
        } while (!valid);
        return transaction;
    }
}
