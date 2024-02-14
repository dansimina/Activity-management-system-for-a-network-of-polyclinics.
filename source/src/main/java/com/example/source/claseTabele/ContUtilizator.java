package com.example.source.claseTabele;

public class ContUtilizator {
    private Integer id;


    private String username;
    private String password;

    public ContUtilizator(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ContUtilizator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
