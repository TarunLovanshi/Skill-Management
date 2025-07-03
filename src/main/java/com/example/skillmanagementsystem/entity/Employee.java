package com.example.skillmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.*;

@Entity
@Table(name = "employee")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String gmail;

    private String department;

    private String designation;

    private int score = 0;

    // 👔 Self or assigned manager
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // 🎯 Skills
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_skill",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnoreProperties("employees")
    private Set<Skill> skills = new HashSet<>();

    // 📁 Projects
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("employee")
    private Set<EmployeeProject> projects = new HashSet<>();

    // 📚 Learning requests
    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("requester")
    private List<LearningRequest> learningRequests = new ArrayList<>();

    // 🕒 Timestamp fields
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    // 🔧 Constructors
    public Employee() {}

    public Employee(String department, String designation, String name, String password, String gmail, Employee manager) {
        this.department = department;
        this.designation = designation;
        this.name = name;
        this.password = password;
        this.gmail = gmail;
        this.manager = manager;
    }

    // 🔁 Lifecycle hooks
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

    // 🔍 Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getGmail() { return gmail; }
    public String getDepartment() { return department; }
    public String getDesignation() { return designation; }
    public int getScore() { return score; }
    public Employee getManager() { return manager; }
    public Set<Skill> getSkills() { return skills; }
    public Set<EmployeeProject> getProjects() { return projects; }
    public List<LearningRequest> getLearningRequests() { return learningRequests; }
    public Date getCreatedDate() { return createdDate; }
    public Date getUpdatedDate() { return updatedDate; }

    // ✏️ Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setGmail(String gmail) { this.gmail = gmail; }
    public void setDepartment(String department) { this.department = department; }
    public void setDesignation(String designation) { this.designation = designation; }
    public void setScore(int score) { this.score = score; }
    public void setManager(Employee manager) { this.manager = manager; }
    public void setSkills(Set<Skill> skills) { this.skills = skills != null ? skills : new HashSet<>(); }
    public void setProjects(Set<EmployeeProject> projects) { this.projects = projects != null ? projects : new HashSet<>(); }
    public void setLearningRequests(List<LearningRequest> learningRequests) {
        this.learningRequests = learningRequests != null ? learningRequests : new ArrayList<>();
    }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public void setUpdatedDate(Date updatedDate) { this.updatedDate = updatedDate; }

    // 🔁 equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // 🧾 toString
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gmail='" + gmail + '\'' +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
