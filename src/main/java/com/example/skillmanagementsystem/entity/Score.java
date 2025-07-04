package com.example.skillmanagementsystem.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "learning_request_id", nullable = false)
    private Long learningRequestId;

    @Column(name = "rating_by")
    private String ratingBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    public Score() {}

    public Score(Long employeeId, Long learningRequestId, String ratingBy) {
        this.employeeId = employeeId;
        this.learningRequestId = learningRequestId;
        this.ratingBy = ratingBy;
    }

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getLearningRequestId() {
        return learningRequestId;
    }

    public String getRatingBy() {
        return ratingBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setLearningRequestId(Long learningRequestId) {
        this.learningRequestId = learningRequestId;
    }

    public void setRatingBy(String ratingBy) {
        this.ratingBy = ratingBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
