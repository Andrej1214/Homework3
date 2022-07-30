package org.pavlov.service;

import org.pavlov.model.User;

import java.util.Scanner;
import static org.pavlov.utils.CheckInput.*;

public class UserInput {
    private static final String FOR_USER = "user's name";

    public static User addNewUser() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        boolean valid = true;
        do {
            try {
                System.out.println("enter user's name");
                String userName = scanner.nextLine();
                checkInputIsNotEmpty(userName,FOR_USER);
                userName = checkInputNameIsValid(userName);
                user.setName(userName);
                System.out.println("enter user's address or press enter");
                String address = scanner.nextLine();
                user.setAddress(address);
            } catch (Exception e) {
                System.out.println("Invalid data, try again");
                deleteWrongInput();
            }
        } while (!valid);
        return user;
    }
}
