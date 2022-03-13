package com.app.workerpool.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @ManyToOne
    @JoinColumn(name = "worker", nullable = false)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(nullable = false)
    @Range(min = 1, max = 10,message = "Please enter rate in range of 1 to 10")
    private int speed;

    @Column(nullable = false)
    @Range(min = 1, max = 10,message = "Please enter rate in range of 1 to 10")
    private int quality;

    @Column(nullable = false)
    @Range(min = 1, max = 10,message = "Please enter rate in range of 1 to 10")
    private int communication;

    @Column(nullable = false)
    @Range(min = 1, max = 10,message = "Please enter rate in range of 1 to 10")
    private int price;

    @Column(nullable = false)
    private double rating;

    public Rating(Worker worker, User user, Integer speed, Integer quality, Integer communication, Integer price, double rating) {
        this.worker = worker;
        this.user = user;
        this.speed = speed;
        this.quality = quality;
        this.communication = communication;
        this.price = price;
        this.rating = rating;
    }
}
