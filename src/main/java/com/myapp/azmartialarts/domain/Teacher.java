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
 * The Teacher entity.
 * @author Manohar
 */
@ApiModel(description = "The Teacher entity. @author Manohar")
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+")
    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @NotNull
    @Size(max = 12)
    @Pattern(regexp = "[0-9]+")
    @Column(name = "mobile_number", length = 12, nullable = false)
    private String mobileNumber;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToMany(mappedBy = "teacher")
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public Teacher teacherName(String teacherName) {
        this.teacherName = teacherName;
        return this;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Teacher mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Teacher dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public Teacher email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Teacher password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesc() {
        return desc;
    }

    public Teacher desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Teacher students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Teacher addStudent(Student student) {
        this.students.add(student);
        student.setTeacher(this);
        return this;
    }

    public Teacher removeStudent(Student student) {
        this.students.remove(student);
        student.setTeacher(null);
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
        Teacher teacher = (Teacher) o;
        if (teacher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", teacherName='" + getTeacherName() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
