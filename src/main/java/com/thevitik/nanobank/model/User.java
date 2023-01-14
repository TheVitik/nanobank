package com.thevitik.nanobank.model;

import com.password4j.Password;

public class User {

    private Integer id = null;
    private String firstName;
    private String lastName;
    private String phone;
    private String password = null;
    private int role = 1;

    public static final int USER = 1;
    public static final int ADMIN = 2;
    public static final int BANNED = 3;

    public User() {
    }

    public User(String firstName, String lastName, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean hasValidPassword(String password) {
        return Password.check(password, this.password).andUpdate().withBcrypt().isVerified();
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setEncryptedPassword(String password) {
        this.password = Password.hash(password).withBcrypt().getResult();
        return this;
    }

    public int getRole() {
        return role;
    }

    public User setRole(int role) {
        this.role = role;
        return this;
    }

    public boolean isBanned() {
        return role == BANNED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!firstName.equals(user.firstName) && !lastName.equals(user.lastName)) return false;
        return phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }
}
