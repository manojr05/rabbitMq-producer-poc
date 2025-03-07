package com.rabbitmq.model;

import com.rabbitmq.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private double salary;
}
