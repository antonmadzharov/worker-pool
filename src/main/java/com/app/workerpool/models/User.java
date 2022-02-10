package com.app.workerpool.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @Column(nullable = false)
    @Size(min = 1, max = 20, message = "Please enter first name between 1 and 20 characters")
    @Pattern(regexp = "^[A-Za-z]+$",message = "First name must contain only letters")
    private String firstName;

    @Size(min = 1, max = 20, message = "Please enter last name between 1 and 20 characters")
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]+$",message = "Last name must contain only letters")
    private String lastName;

    @Size(min = 2, max = 20, message = "Please enter username between 2 and 20 symbols")
    @Column(unique = true, nullable = false,updatable = false )
    private String username;

    @Email
    private String email;

    @Min(value = 18,message = "Not of age persons are not allowed to enter the site")
    @Max(value = 150,message = "We are happy to see the oldest person on the planet at our doors," +
            " barely believe it is your true age, please enter valid years")
    private int age;

    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 symbols")
    @Column(nullable = false)
    private String password;

    @Size(max = 250)
    private String role;

//    @OneToOne(mappedBy = "user")
//    private Image image;
}
