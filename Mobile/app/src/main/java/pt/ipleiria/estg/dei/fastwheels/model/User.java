package pt.ipleiria.estg.dei.fastwheels.model;

import androidx.annotation.NonNull;

import java.util.Date;

import pt.ipleiria.estg.dei.fastwheels.utils.generateBase64;

public class User {
    private String token, name, email, phone, balance, iban;
    private int id;
    private String password;
    private generateBase64 base64token;

    public User() {

    }

    public User(String token, int id, String name, String email, String phone, String balance, String iban) {
        this.id = id;
        this.token = token;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public generateBase64 getBase64token() {
        return base64token;
    }

    public void setBase64token(generateBase64 base64token) {
        this.base64token = base64token;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", balance='" + balance + '\'' +
                ", iban='" + iban + '\'' +
                ", id=" + id +
                '}';
    }
}
