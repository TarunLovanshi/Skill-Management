package com.example.skillmanagementsystem.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee_project")
public class EmployeeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    // ✅ New fields
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    public EmployeeProject() {}

    public EmployeeProject(Employee employee, Long projectId) {
        this.employee = employee;
        this.projectId = projectId;
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

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "EmployeeProject{" +
                "id=" + id +
                ", employeeId=" + (employee != null ? employee.getId() : null) +
                ", projectId=" + projectId +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
