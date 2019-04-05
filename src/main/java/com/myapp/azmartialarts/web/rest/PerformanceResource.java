package com.myapp.azmartialarts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.myapp.azmartialarts.domain.Performance;
import com.myapp.azmartialarts.repository.PerformanceRepository;
import com.myapp.azmartialarts.web.rest.errors.BadRequestAlertException;
import com.myapp.azmartialarts.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Performance.
 */
@RestController
@RequestMapping("/api")
public class PerformanceResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceResource.class);

    private static final String ENTITY_NAME = "performance";

    private final PerformanceRepository performanceRepository;

    public PerformanceResource(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    /**
     * POST  /performances : Create a new performance.
     *
     * @param performance the performance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new performance, or with status 400 (Bad Request) if the performance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/performances")
    @Timed
    public ResponseEntity<Performance> createPerformance(@Valid @RequestBody Performance performance) throws URISyntaxException {
        log.debug("REST request to save Performance : {}", performance);
        if (performance.getId() != null) {
            throw new BadRequestAlertException("A new performance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Performance result = performanceRepository.save(performance);
        return ResponseEntity.created(new URI("/api/performances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /performances : Updates an existing performance.
     *
     * @param performance the performance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated performance,
     * or with status 400 (Bad Request) if the performance is not valid,
     * or with status 500 (Internal Server Error) if the performance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/performances")
    @Timed
    public ResponseEntity<Performance> updatePerformance(@Valid @RequestBody Performance performance) throws URISyntaxException {
        log.debug("REST request to update Performance : {}", performance);
        if (performance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Performance result = performanceRepository.save(performance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, performance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /performances : get all the performances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of performances in body
     */
    @GetMapping("/performances")
    @Timed
    public List<Performance> getAllPerformances() {
        log.debug("REST request to get all Performances");
        return performanceRepository.findAll();
    }

    /**
     * GET  /performances/:id : get the "id" performance.
     *
     * @param id the id of the performance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the performance, or with status 404 (Not Found)
     */
    @GetMapping("/performances/{id}")
    @Timed
    public ResponseEntity<Performance> getPerformance(@PathVariable Long id) {
        log.debug("REST request to get Performance : {}", id);
        Optional<Performance> performance = performanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(performance);
    }

    /**
     * DELETE  /performances/:id : delete the "id" performance.
     *
     * @param id the id of the performance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/performances/{id}")
    @Timed
    public ResponseEntity<Void> deletePerformance(@PathVariable Long id) {
        log.debug("REST request to delete Performance : {}", id);

        performanceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
