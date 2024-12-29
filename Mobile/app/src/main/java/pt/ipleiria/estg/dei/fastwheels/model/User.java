package pt.ipleiria.estg.dei.fastwheels.model;

import java.util.Date;

public class User {
    private String token, name, email, phone, balance, iban;
    private int id;

    public User() {

    }

    public User(String token, int id, String name, String email, String phone, String balance, String iban) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.iban = iban;
    }

    public String getToken() {
        return token;
    }
    public String setToken(String token) {
        return this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
