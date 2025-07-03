package com.example.skillmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "skills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"skills", "manager", "projects", "learningRequests", "createdDate", "updatedDate", "password"})
    private Set<Employee> employees = new HashSet<>();

    // Constructors
    public Skill() {}

    public Skill(String name) {
        this.name = name;
    }

    // Lifecycle hooks
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

    // Getters
    public Long getId() { return id; }

    public String getName() { return name; }

    public Date getCreatedDate() { return createdDate; }

    public Date getUpdatedDate() { return updatedDate; }

    public Set<Employee> getEmployees() { return employees; }

    // Setters
    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public void setUpdatedDate(Date updatedDate) { this.updatedDate = updatedDate; }

    public void setEmployees(Set<Employee> employees) {
        this.employees = (employees != null) ? employees : new HashSet<>();
    }

    // equals & hashCode based on ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString for logging
    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
