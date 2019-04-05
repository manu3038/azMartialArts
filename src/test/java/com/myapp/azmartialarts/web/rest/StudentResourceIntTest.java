package com.myapp.azmartialarts.web.rest;

import com.myapp.azmartialarts.AzmartialartsApp;

import com.myapp.azmartialarts.domain.Student;
import com.myapp.azmartialarts.repository.StudentRepository;
import com.myapp.azmartialarts.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.myapp.azmartialarts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentResource REST controller.
 *
 * @see StudentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AzmartialartsApp.class)
public class StudentResourceIntTest {

    private static final String DEFAULT_STUDENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENTMOBILE_NUMBER = "82";
    private static final String UPDATED_STUDENTMOBILE_NUMBER = "40";

    private static final String DEFAULT_DATE_OF_JOINING = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_JOINING = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_CARD_NUMBER = "6";
    private static final String UPDATED_AADHAR_CARD_NUMBER = "22";

    private static final String DEFAULT_PARENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENTMOBILE_NUMBER = "31";
    private static final String UPDATED_PARENTMOBILE_NUMBER = "72";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_FEES = 1D;
    private static final Double UPDATED_TOTAL_FEES = 2D;

    private static final Double DEFAULT_PAID_FEES = 1D;
    private static final Double UPDATED_PAID_FEES = 2D;

    private static final Double DEFAULT_DUE_FEES = 1D;
    private static final Double UPDATED_DUE_FEES = 2D;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_STUDENTEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_STUDENTEMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PARENTEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PARENTEMAIL = "BBBBBBBBBB";

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentMockMvc;

    private Student student;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentRepository);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .studentName(DEFAULT_STUDENT_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .studentmobileNumber(DEFAULT_STUDENTMOBILE_NUMBER)
            .dateOfJoining(DEFAULT_DATE_OF_JOINING)
            .aadharCardNumber(DEFAULT_AADHAR_CARD_NUMBER)
            .parentName(DEFAULT_PARENT_NAME)
            .parentmobileNumber(DEFAULT_PARENTMOBILE_NUMBER)
            .password(DEFAULT_PASSWORD)
            .totalFees(DEFAULT_TOTAL_FEES)
            .paidFees(DEFAULT_PAID_FEES)
            .dueFees(DEFAULT_DUE_FEES)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .studentemail(DEFAULT_STUDENTEMAIL)
            .parentemail(DEFAULT_PARENTEMAIL);
        return student;
    }

    @Before
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentName()).isEqualTo(DEFAULT_STUDENT_NAME);
        assertThat(testStudent.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testStudent.getStudentmobileNumber()).isEqualTo(DEFAULT_STUDENTMOBILE_NUMBER);
        assertThat(testStudent.getDateOfJoining()).isEqualTo(DEFAULT_DATE_OF_JOINING);
        assertThat(testStudent.getAadharCardNumber()).isEqualTo(DEFAULT_AADHAR_CARD_NUMBER);
        assertThat(testStudent.getParentName()).isEqualTo(DEFAULT_PARENT_NAME);
        assertThat(testStudent.getParentmobileNumber()).isEqualTo(DEFAULT_PARENTMOBILE_NUMBER);
        assertThat(testStudent.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testStudent.getTotalFees()).isEqualTo(DEFAULT_TOTAL_FEES);
        assertThat(testStudent.getPaidFees()).isEqualTo(DEFAULT_PAID_FEES);
        assertThat(testStudent.getDueFees()).isEqualTo(DEFAULT_DUE_FEES);
        assertThat(testStudent.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testStudent.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testStudent.getStudentemail()).isEqualTo(DEFAULT_STUDENTEMAIL);
        assertThat(testStudent.getParentemail()).isEqualTo(DEFAULT_PARENTEMAIL);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStudentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setStudentName(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setDateOfBirth(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfJoiningIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setDateOfJoining(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAadharCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setAadharCardNumber(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setParentName(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParentmobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setParentmobileNumber(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentName").value(hasItem(DEFAULT_STUDENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].studentmobileNumber").value(hasItem(DEFAULT_STUDENTMOBILE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfJoining").value(hasItem(DEFAULT_DATE_OF_JOINING.toString())))
            .andExpect(jsonPath("$.[*].aadharCardNumber").value(hasItem(DEFAULT_AADHAR_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].parentName").value(hasItem(DEFAULT_PARENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentmobileNumber").value(hasItem(DEFAULT_PARENTMOBILE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].totalFees").value(hasItem(DEFAULT_TOTAL_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].paidFees").value(hasItem(DEFAULT_PAID_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].dueFees").value(hasItem(DEFAULT_DUE_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].studentemail").value(hasItem(DEFAULT_STUDENTEMAIL.toString())))
            .andExpect(jsonPath("$.[*].parentemail").value(hasItem(DEFAULT_PARENTEMAIL.toString())));
    }
    

    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentName").value(DEFAULT_STUDENT_NAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.studentmobileNumber").value(DEFAULT_STUDENTMOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfJoining").value(DEFAULT_DATE_OF_JOINING.toString()))
            .andExpect(jsonPath("$.aadharCardNumber").value(DEFAULT_AADHAR_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.parentName").value(DEFAULT_PARENT_NAME.toString()))
            .andExpect(jsonPath("$.parentmobileNumber").value(DEFAULT_PARENTMOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.totalFees").value(DEFAULT_TOTAL_FEES.doubleValue()))
            .andExpect(jsonPath("$.paidFees").value(DEFAULT_PAID_FEES.doubleValue()))
            .andExpect(jsonPath("$.dueFees").value(DEFAULT_DUE_FEES.doubleValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.studentemail").value(DEFAULT_STUDENTEMAIL.toString()))
            .andExpect(jsonPath("$.parentemail").value(DEFAULT_PARENTEMAIL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .studentName(UPDATED_STUDENT_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .studentmobileNumber(UPDATED_STUDENTMOBILE_NUMBER)
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .aadharCardNumber(UPDATED_AADHAR_CARD_NUMBER)
            .parentName(UPDATED_PARENT_NAME)
            .parentmobileNumber(UPDATED_PARENTMOBILE_NUMBER)
            .password(UPDATED_PASSWORD)
            .totalFees(UPDATED_TOTAL_FEES)
            .paidFees(UPDATED_PAID_FEES)
            .dueFees(UPDATED_DUE_FEES)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .studentemail(UPDATED_STUDENTEMAIL)
            .parentemail(UPDATED_PARENTEMAIL);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentName()).isEqualTo(UPDATED_STUDENT_NAME);
        assertThat(testStudent.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testStudent.getStudentmobileNumber()).isEqualTo(UPDATED_STUDENTMOBILE_NUMBER);
        assertThat(testStudent.getDateOfJoining()).isEqualTo(UPDATED_DATE_OF_JOINING);
        assertThat(testStudent.getAadharCardNumber()).isEqualTo(UPDATED_AADHAR_CARD_NUMBER);
        assertThat(testStudent.getParentName()).isEqualTo(UPDATED_PARENT_NAME);
        assertThat(testStudent.getParentmobileNumber()).isEqualTo(UPDATED_PARENTMOBILE_NUMBER);
        assertThat(testStudent.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testStudent.getTotalFees()).isEqualTo(UPDATED_TOTAL_FEES);
        assertThat(testStudent.getPaidFees()).isEqualTo(UPDATED_PAID_FEES);
        assertThat(testStudent.getDueFees()).isEqualTo(UPDATED_DUE_FEES);
        assertThat(testStudent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testStudent.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testStudent.getStudentemail()).isEqualTo(UPDATED_STUDENTEMAIL);
        assertThat(testStudent.getParentemail()).isEqualTo(UPDATED_PARENTEMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Get the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }
}
