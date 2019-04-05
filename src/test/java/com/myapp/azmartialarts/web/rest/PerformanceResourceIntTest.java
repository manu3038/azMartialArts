package com.myapp.azmartialarts.web.rest;

import com.myapp.azmartialarts.AzmartialartsApp;

import com.myapp.azmartialarts.domain.Performance;
import com.myapp.azmartialarts.repository.PerformanceRepository;
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

import javax.persistence.EntityManager;
import java.util.List;


import static com.myapp.azmartialarts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PerformanceResource REST controller.
 *
 * @see PerformanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AzmartialartsApp.class)
public class PerformanceResourceIntTest {

    private static final String DEFAULT_RATING = "56";
    private static final String UPDATED_RATING = "6";

    @Autowired
    private PerformanceRepository performanceRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerformanceMockMvc;

    private Performance performance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerformanceResource performanceResource = new PerformanceResource(performanceRepository);
        this.restPerformanceMockMvc = MockMvcBuilders.standaloneSetup(performanceResource)
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
    public static Performance createEntity(EntityManager em) {
        Performance performance = new Performance()
            .rating(DEFAULT_RATING);
        return performance;
    }

    @Before
    public void initTest() {
        performance = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerformance() throws Exception {
        int databaseSizeBeforeCreate = performanceRepository.findAll().size();

        // Create the Performance
        restPerformanceMockMvc.perform(post("/api/performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(performance)))
            .andExpect(status().isCreated());

        // Validate the Performance in the database
        List<Performance> performanceList = performanceRepository.findAll();
        assertThat(performanceList).hasSize(databaseSizeBeforeCreate + 1);
        Performance testPerformance = performanceList.get(performanceList.size() - 1);
        assertThat(testPerformance.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createPerformanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = performanceRepository.findAll().size();

        // Create the Performance with an existing ID
        performance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceMockMvc.perform(post("/api/performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(performance)))
            .andExpect(status().isBadRequest());

        // Validate the Performance in the database
        List<Performance> performanceList = performanceRepository.findAll();
        assertThat(performanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = performanceRepository.findAll().size();
        // set the field null
        performance.setRating(null);

        // Create the Performance, which fails.

        restPerformanceMockMvc.perform(post("/api/performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(performance)))
            .andExpect(status().isBadRequest());

        List<Performance> performanceList = performanceRepository.findAll();
        assertThat(performanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerformances() throws Exception {
        // Initialize the database
        performanceRepository.saveAndFlush(performance);

        // Get all the performanceList
        restPerformanceMockMvc.perform(get("/api/performances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performance.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())));
    }
    

    @Test
    @Transactional
    public void getPerformance() throws Exception {
        // Initialize the database
        performanceRepository.saveAndFlush(performance);

        // Get the performance
        restPerformanceMockMvc.perform(get("/api/performances/{id}", performance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(performance.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPerformance() throws Exception {
        // Get the performance
        restPerformanceMockMvc.perform(get("/api/performances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerformance() throws Exception {
        // Initialize the database
        performanceRepository.saveAndFlush(performance);

        int databaseSizeBeforeUpdate = performanceRepository.findAll().size();

        // Update the performance
        Performance updatedPerformance = performanceRepository.findById(performance.getId()).get();
        // Disconnect from session so that the updates on updatedPerformance are not directly saved in db
        em.detach(updatedPerformance);
        updatedPerformance
            .rating(UPDATED_RATING);

        restPerformanceMockMvc.perform(put("/api/performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerformance)))
            .andExpect(status().isOk());

        // Validate the Performance in the database
        List<Performance> performanceList = performanceRepository.findAll();
        assertThat(performanceList).hasSize(databaseSizeBeforeUpdate);
        Performance testPerformance = performanceList.get(performanceList.size() - 1);
        assertThat(testPerformance.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingPerformance() throws Exception {
        int databaseSizeBeforeUpdate = performanceRepository.findAll().size();

        // Create the Performance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerformanceMockMvc.perform(put("/api/performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(performance)))
            .andExpect(status().isBadRequest());

        // Validate the Performance in the database
        List<Performance> performanceList = performanceRepository.findAll();
        assertThat(performanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerformance() throws Exception {
        // Initialize the database
        performanceRepository.saveAndFlush(performance);

        int databaseSizeBeforeDelete = performanceRepository.findAll().size();

        // Get the performance
        restPerformanceMockMvc.perform(delete("/api/performances/{id}", performance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Performance> performanceList = performanceRepository.findAll();
        assertThat(performanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Performance.class);
        Performance performance1 = new Performance();
        performance1.setId(1L);
        Performance performance2 = new Performance();
        performance2.setId(performance1.getId());
        assertThat(performance1).isEqualTo(performance2);
        performance2.setId(2L);
        assertThat(performance1).isNotEqualTo(performance2);
        performance1.setId(null);
        assertThat(performance1).isNotEqualTo(performance2);
    }
}
