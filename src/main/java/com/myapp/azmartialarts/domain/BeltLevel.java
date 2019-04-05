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
 * The BeltLevel entity.
 * @author Manohar
 */
@ApiModel(description = "The BeltLevel entity. @author Manohar")
@Entity
@Table(name = "belt_level")
public class BeltLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+")
    @Column(name = "jhi_level", nullable = false)
    private String level;

    @OneToMany(mappedBy = "beltLevel")
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public BeltLevel level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public BeltLevel students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public BeltLevel addStudent(Student student) {
        this.students.add(student);
        student.setBeltLevel(this);
        return this;
    }

    public BeltLevel removeStudent(Student student) {
        this.students.remove(student);
        student.setBeltLevel(null);
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
        BeltLevel beltLevel = (BeltLevel) o;
        if (beltLevel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beltLevel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BeltLevel{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
