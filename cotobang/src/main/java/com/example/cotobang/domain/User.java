package com.example.cotobang.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@Where(clause = "deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private boolean deleted = false;

    @Builder
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void change(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void destory() {
        this.deleted = true;
    }
    
    public boolean authenticate(String password) {
        return !deleted && password.equals(this.password);
    }

    public boolean compare(User user) {
        return id == user.getId();
    }

    @Override
    public String toString() {
        return String.format("User{id=%s, email=%s, name=%s, password=%s}",
                id, email, name, password);
    }
}
