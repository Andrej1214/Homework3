package org.pavlov.utils;

import java.util.Scanner;

import static java.lang.String.format;

public class CheckInput {
    private static final String IS_NUMBER_REGEX = "\\d+";
    private static final String REGEX_IS_NUMBER_IN_DOUBLE = "(\\d+(,|\\.)\\d{1,3})";
    private static final String REGEX_FOR_NAME = "([A-Z]{1}[a-z]+\\s?[A-Z]{1}[a-z]+)";
    private static final String REGEX_FOR_CURRENCY = "([A-Z]{3})";

    private static final Scanner scanner = new Scanner(System.in);

    public static String checkInputIsNotEmpty(String input,String category){
        while (input.isEmpty()) {
            System.out.println(format("This field cannot be empty. Enter '%s'",category));
            input = scanner.nextLine();
        }
        return input;
    }
    public static String checkInputNameIsValid(String input){
        while (!input.matches(REGEX_FOR_NAME)) {
            System.out.println("You entered a wrong format of user's name.\n" +
                    "allowed format: Ivan Ivanov ;  \n" +
                    "try again.\n");
            input = scanner.nextLine();
        }
        return input;
    }
    public static String checkInputIsNumber(String input){
        while (!input.matches(IS_NUMBER_REGEX)) {
            System.out.println("You entered a wrong number or a number in the wrong format.\n" +
                    "allowed format: 556789 ;  \n" +
                    "try again.\n");
            input = scanner.nextLine();
        }
        return input;
    }
    public static String checkCurrencyInput(String input){
        while (!input.matches(REGEX_FOR_CURRENCY)) {
            System.out.println("You entered a value in the wrong format.\n" +
                    "allowed format: RUB ; EUR \n" +
                    "try again.\n");
            input = scanner.nextLine();
        }
        return input;
    }
    public static String checkInputIsNumberInDouble(String input){
        boolean isValidDouble = input.matches(REGEX_IS_NUMBER_IN_DOUBLE);
        boolean isValidInteger = input.matches(IS_NUMBER_REGEX);
        boolean isValidNumber = isValidDouble ^ isValidInteger;
        if (input.isEmpty()) {
            input = "0";
        } else {
            while (!isValidNumber) {
                System.out.println("You entered a wrong number or a number in the wrong format.\n" +
                        "allowed formats: 556789; 76542,579; 2345.456; 543,34; 76543,1;\n" +
                        "try again.\n");
                input = scanner.nextLine();
                boolean isValidDouble2 = input.matches(REGEX_IS_NUMBER_IN_DOUBLE);
                boolean isValidInteger2 = input.matches(IS_NUMBER_REGEX);
                boolean isValidNumber2 = isValidDouble2 ^ isValidInteger2;
                if (isValidNumber2 == true) {
                    break;
                }
            }
        }
        return input;
    }
    public static void deleteWrongInput(){
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
