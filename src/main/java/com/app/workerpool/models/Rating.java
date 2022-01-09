package com.app.workerpool.models;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Rating {

    @ManyToOne
    @JoinColumn(name = "worker", nullable = false)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(nullable = false)
    @Range(min = 1, max = 10,message = "Please enter rate in range of 1 to 10")
    private int rating;

}
