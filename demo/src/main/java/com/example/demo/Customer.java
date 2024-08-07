package com.example.CustomerMicroservices;

import org.hibernate.annotations.DialectOverride.GeneratedColumn;
import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationPlan;
import org.springframework.aot.generate.GenerationContext;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;

    public Customer(int id, String name, String password, String email){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    public Customer() {};

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPassword(){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setEmail(){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}
