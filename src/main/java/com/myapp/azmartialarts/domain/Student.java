package com.myapp.azmartialarts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Student entity.
 * @author Manohar
 */
@ApiModel(description = "The Student entity. @author Manohar")
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+")
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Size(max = 12)
    @Pattern(regexp = "[0-9]+")
    @Column(name = "studentmobile_number", length = 12)
    private String studentmobileNumber;

    @NotNull
    @Column(name = "date_of_joining", nullable = false)
    private String dateOfJoining;

    @NotNull
    @Size(max = 12)
    @Pattern(regexp = "[0-9]+")
    @Column(name = "aadhar_card_number", length = 12, nullable = false)
    private String aadharCardNumber;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+")
    @Column(name = "parent_name", nullable = false)
    private String parentName;

    @NotNull
    @Size(max = 12)
    @Pattern(regexp = "[0-9]+")
    @Column(name = "parentmobile_number", length = 12, nullable = false)
    private String parentmobileNumber;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "total_fees")
    private Double totalFees;

    @Column(name = "paid_fees")
    private Double paidFees;

    @Column(name = "due_fees")
    private Double dueFees;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "studentemail")
    private String studentemail;

    @Column(name = "parentemail")
    private String parentemail;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private BeltLevel beltLevel;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private Performance performance;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public Student studentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Student dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStudentmobileNumber() {
        return studentmobileNumber;
    }

    public Student studentmobileNumber(String studentmobileNumber) {
        this.studentmobileNumber = studentmobileNumber;
        return this;
    }

    public void setStudentmobileNumber(String studentmobileNumber) {
        this.studentmobileNumber = studentmobileNumber;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public Student dateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
        return this;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getAadharCardNumber() {
        return aadharCardNumber;
    }

    public Student aadharCardNumber(String aadharCardNumber) {
        this.aadharCardNumber = aadharCardNumber;
        return this;
    }

    public void setAadharCardNumber(String aadharCardNumber) {
        this.aadharCardNumber = aadharCardNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public Student parentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentmobileNumber() {
        return parentmobileNumber;
    }

    public Student parentmobileNumber(String parentmobileNumber) {
        this.parentmobileNumber = parentmobileNumber;
        return this;
    }

    public void setParentmobileNumber(String parentmobileNumber) {
        this.parentmobileNumber = parentmobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public Student password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getTotalFees() {
        return totalFees;
    }

    public Student totalFees(Double totalFees) {
        this.totalFees = totalFees;
        return this;
    }

    public void setTotalFees(Double totalFees) {
        this.totalFees = totalFees;
    }

    public Double getPaidFees() {
        return paidFees;
    }

    public Student paidFees(Double paidFees) {
        this.paidFees = paidFees;
        return this;
    }

    public void setPaidFees(Double paidFees) {
        this.paidFees = paidFees;
    }

    public Double getDueFees() {
        return dueFees;
    }

    public Student dueFees(Double dueFees) {
        this.dueFees = dueFees;
        return this;
    }

    public void setDueFees(Double dueFees) {
        this.dueFees = dueFees;
    }

    public byte[] getImage() {
        return image;
    }

    public Student image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Student imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getStudentemail() {
        return studentemail;
    }

    public Student studentemail(String studentemail) {
        this.studentemail = studentemail;
        return this;
    }

    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public String getParentemail() {
        return parentemail;
    }

    public Student parentemail(String parentemail) {
        this.parentemail = parentemail;
        return this;
    }

    public void setParentemail(String parentemail) {
        this.parentemail = parentemail;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Student teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public BeltLevel getBeltLevel() {
        return beltLevel;
    }

    public Student beltLevel(BeltLevel beltLevel) {
        this.beltLevel = beltLevel;
        return this;
    }

    public void setBeltLevel(BeltLevel beltLevel) {
        this.beltLevel = beltLevel;
    }

    public Performance getPerformance() {
        return performance;
    }

    public Student performance(Performance performance) {
        this.performance = performance;
        return this;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Location getLocation() {
        return location;
    }

    public Student location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentName='" + getStudentName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", studentmobileNumber='" + getStudentmobileNumber() + "'" +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", aadharCardNumber='" + getAadharCardNumber() + "'" +
            ", parentName='" + getParentName() + "'" +
            ", parentmobileNumber='" + getParentmobileNumber() + "'" +
            ", password='" + getPassword() + "'" +
            ", totalFees=" + getTotalFees() +
            ", paidFees=" + getPaidFees() +
            ", dueFees=" + getDueFees() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", studentemail='" + getStudentemail() + "'" +
            ", parentemail='" + getParentemail() + "'" +
            "}";
    }
}
