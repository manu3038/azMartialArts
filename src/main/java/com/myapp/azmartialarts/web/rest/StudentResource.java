package com.myapp.azmartialarts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.myapp.azmartialarts.domain.BeltLevel;
import com.myapp.azmartialarts.domain.Location;
import com.myapp.azmartialarts.domain.Student;
import com.myapp.azmartialarts.domain.Teacher;
import com.myapp.azmartialarts.repository.StudentRepository;
import com.myapp.azmartialarts.web.rest.errors.BadRequestAlertException;
import com.myapp.azmartialarts.web.rest.util.HeaderUtil;
import com.myapp.azmartialarts.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Student.
 */
@RestController
@RequestMapping("/api")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    private static final String ENTITY_NAME = "student";

    private final StudentRepository studentRepository;

    public StudentResource(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * POST  /students : Create a new student.
     *
     * @param student the student to create
     * @return the ResponseEntity with status 201 (Created) and with body the new student, or with status 400 (Bad Request) if the student has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/students")
    @Timed
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) throws URISyntaxException {
        log.debug("REST request to save Student : {}", student);
        if (student.getId() != null) {
            throw new BadRequestAlertException("A new student cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Student result = studentRepository.save(student);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /students : Updates an existing student.
     *
     * @param student the student to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated student,
     * or with status 400 (Bad Request) if the student is not valid,
     * or with status 500 (Internal Server Error) if the student couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/students")
    @Timed
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student) throws URISyntaxException {
        log.debug("REST request to update Student : {}", student);
        if (student.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Student result = studentRepository.save(student);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, student.getId().toString()))
            .body(result);
    }

    /**
     * GET  /students : get all the students.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of students in body
     */
    @GetMapping("/students")
    @Timed
    public ResponseEntity<List<Student>> getAllStudents(Pageable pageable) {
        log.debug("REST request to get a page of Students");
        Page<Student> page = studentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/students");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /students/:id : get the "id" student.
     *
     * @param id the id of the student to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the student, or with status 404 (Not Found)
     */
    @GetMapping("/students/{id}")
    @Timed
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        Optional<Student> student = studentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(student);
    }

    /**
     * DELETE  /students/:id : delete the "id" student.
     *
     * @param id the id of the student to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/students/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);

        studentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/searchStudents")
    @Timed
    public ResponseEntity<List<Student>> searchStudents(@RequestBody LinkedHashMap<String,Long> params){
    	if(params.get("beltSearch") == null && params.get("locationSearch")!= null) {
    		List<Student> locationResult = studentRepository.getByLocation(params.get("locationSearch"));
    		log.debug("returned result for location "+locationResult.toString());
    		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationResult));
    	}
    	else if(params.get("beltSearch") != null && params.get("locationSearch") == null) {
    	List<Student> beltResult = studentRepository.getbyBelt(params.get("beltSearch"));
    	log.debug("returned result for belt "+beltResult.toString());
    	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(beltResult));
    	}
    	else if(params.get("locationSearch") != null && params.get("beltSearch")!= null) {
    		List<Student> locationAndBeltResult = studentRepository.getbyLocationAndBelt(params.get("locationSearch"), params.get("beltSearch"));
    		log.debug("returned result for belt & location "+locationAndBeltResult.toString());
    		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationAndBeltResult));
    	}
		return ResponseEntity.noContent().build();    	
    }
    
    
    @GetMapping("/teacherDelete/{id}")
    @Timed
    public ResponseEntity<?> searchStudents(@PathVariable Teacher id){
		log.debug("In the get api call id = "+id);
		List<Student> teacherDelete = studentRepository.findByTeacher(id);
		log.debug("result for teacher serach" + teacherDelete);
    	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teacherDelete));
    }
}
    
