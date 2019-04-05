package com.myapp.azmartialarts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Performance entity.
 * @author Manohar
 */
@ApiModel(description = "The Performance entity. @author Manohar")
@Entity
@Table(name = "performance")
public class Performance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 2)
    @Pattern(regexp = "[0-9]+")
    @Column(name = "rating", length = 2, nullable = false)
    private String rating;

    @OneToMany(mappedBy = "performance")
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public Performance rating(String rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Performance students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Performance addStudent(Student student) {
        this.students.add(student);
        student.setPerformance(this);
        return this;
    }

    public Performance removeStudent(Student student) {
        this.students.remove(student);
        student.setPerformance(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Performance performance = (Performance) o;
        if (performance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), performance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Performance{" +
            "id=" + getId() +
            ", rating='" + getRating() + "'" +
            "}";
    }
}
