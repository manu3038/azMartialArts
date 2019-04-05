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
 * The Location entity.
 * @author Manohar
 */
@ApiModel(description = "The Location entity. @author Manohar")
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "location_address")
    private String locationAddress;

    @OneToMany(mappedBy = "location")
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public Location locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public Location locationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
        return this;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Location students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Location addStudent(Student student) {
        this.students.add(student);
        student.setLocation(this);
        return this;
    }

    public Location removeStudent(Student student) {
        this.students.remove(student);
        student.setLocation(null);
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", locationAddress='" + getLocationAddress() + "'" +
            "}";
    }
}
