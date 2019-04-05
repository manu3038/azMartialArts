package com.myapp.azmartialarts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.myapp.azmartialarts.domain.BeltLevel;
import com.myapp.azmartialarts.repository.BeltLevelRepository;
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
 * REST controller for managing BeltLevel.
 */
@RestController
@RequestMapping("/api")
public class BeltLevelResource {

    private final Logger log = LoggerFactory.getLogger(BeltLevelResource.class);

    private static final String ENTITY_NAME = "beltLevel";

    private final BeltLevelRepository beltLevelRepository;

    public BeltLevelResource(BeltLevelRepository beltLevelRepository) {
        this.beltLevelRepository = beltLevelRepository;
    }

    /**
     * POST  /belt-levels : Create a new beltLevel.
     *
     * @param beltLevel the beltLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beltLevel, or with status 400 (Bad Request) if the beltLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/belt-levels")
    @Timed
    public ResponseEntity<BeltLevel> createBeltLevel(@Valid @RequestBody BeltLevel beltLevel) throws URISyntaxException {
        log.debug("REST request to save BeltLevel : {}", beltLevel);
        if (beltLevel.getId() != null) {
            throw new BadRequestAlertException("A new beltLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeltLevel result = beltLevelRepository.save(beltLevel);
        return ResponseEntity.created(new URI("/api/belt-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /belt-levels : Updates an existing beltLevel.
     *
     * @param beltLevel the beltLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beltLevel,
     * or with status 400 (Bad Request) if the beltLevel is not valid,
     * or with status 500 (Internal Server Error) if the beltLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/belt-levels")
    @Timed
    public ResponseEntity<BeltLevel> updateBeltLevel(@Valid @RequestBody BeltLevel beltLevel) throws URISyntaxException {
        log.debug("REST request to update BeltLevel : {}", beltLevel);
        if (beltLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeltLevel result = beltLevelRepository.save(beltLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beltLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /belt-levels : get all the beltLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beltLevels in body
     */
    @GetMapping("/belt-levels")
    @Timed
    public List<BeltLevel> getAllBeltLevels() {
        log.debug("REST request to get all BeltLevels");
        return beltLevelRepository.findAll();
    }

    /**
     * GET  /belt-levels/:id : get the "id" beltLevel.
     *
     * @param id the id of the beltLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beltLevel, or with status 404 (Not Found)
     */
    @GetMapping("/belt-levels/{id}")
    @Timed
    public ResponseEntity<BeltLevel> getBeltLevel(@PathVariable Long id) {
        log.debug("REST request to get BeltLevel : {}", id);
        Optional<BeltLevel> beltLevel = beltLevelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beltLevel);
    }

    /**
     * DELETE  /belt-levels/:id : delete the "id" beltLevel.
     *
     * @param id the id of the beltLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/belt-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeltLevel(@PathVariable Long id) {
        log.debug("REST request to delete BeltLevel : {}", id);

        beltLevelRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
