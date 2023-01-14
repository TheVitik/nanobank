package com.thevitik.nanobank.service.validation;

import com.thevitik.nanobank.model.User;

import javax.servlet.http.HttpServletRequest;

public abstract class Validator<T> {

    final String PHONE_PATTERN = "^0+[1-9]{1}+[0-9]{8}$";
    final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    protected String str;
    protected String name;

    /**
     * Validate data and return object
     */
    public abstract T validated(HttpServletRequest request) throws IllegalArgumentException;

    /**
     * Start a validation process
     */
    public Validator<T> validate(String str, String name) {
        this.str = str;
        this.name = name;
        return this;
    }

    /**
     * The request has required parameters
     */
    public void checkRequiredParameters(HttpServletRequest request, String[] params) {
        for (String p : params) {
            String value = request.getParameter(p);
            if (value == null || value.equals("")) {
                String msg = String.format("%s is empty", p.substring(0, 1).toUpperCase() + p.substring(1));
                throw new IllegalArgumentException(msg);
            }
        }
    }

    /**
     * Has a length lower than number
     */
    public Validator<T> lengthLower(int number) {
        if (str.length() >= number) {
            throw new IllegalArgumentException(String.format("The length of %s must be lower than %s", name, number));
        }
        return this;
    }

    /**
     * Has a length greater than number
     */
    public Validator<T> lengthGreater(int number) {
        if (str.length() <= number) {
            throw new IllegalArgumentException(String.format("The length of %s must be greater than %s", name, number));
        }
        return this;
    }

    /**
     * Has a length between numbers
     */
    public Validator<T> lengthBetween(int min, int max) {
        if (str.length() < min || str.length() > max) {
            throw new IllegalArgumentException(String.format("The length of %s must be between %s and %s", name, min, max));
        }
        return this;
    }

    /**
     * Has a length between numbers
     */
    public Validator<T> intBetween(int min, int max) {
        if (Integer.parseInt(str) < min || Integer.parseInt(str) > max) {
            throw new IllegalArgumentException(String.format("The length of %s must be between %s and %s", name, min, max));
        }
        return this;
    }

    /**
     * Has a phone format
     */
    public Validator<T> phone() {
        if (!str.matches(PHONE_PATTERN)) {
            throw new IllegalArgumentException("Incorrect phone format");
        }
        return this;
    }

    /**
     * Has a secure password
     * At least one special char, uppercase letter and number
     */
    public Validator<T> password() {
        if (!str.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("Password is not secure");
        }
        return this;
    }

    /**
     * Has a credit card number
     * Without real validation
     */
    public Validator<T> creditCard() {
        try {
            long result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid card number");
        }
        if (str.length() != 16) {
            throw new IllegalArgumentException("Invalid card number");
        }

        return this;
    }

    /**
     * Check if string is integer
     */
    public Validator<T> integer() {
        try {
            int result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("It is not a number");
        }
        return this;
    }

    public Validator<T> unique(String table, String column) {
        return this;
    }

    public Validator<T> exists(String table, String column) {

        return this;
    }
}
