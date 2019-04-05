package com.myapp.azmartialarts.web.rest;

import com.myapp.azmartialarts.AzmartialartsApp;

import com.myapp.azmartialarts.domain.BeltLevel;
import com.myapp.azmartialarts.repository.BeltLevelRepository;
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
 * Test class for the BeltLevelResource REST controller.
 *
 * @see BeltLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AzmartialartsApp.class)
public class BeltLevelResourceIntTest {

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    @Autowired
    private BeltLevelRepository beltLevelRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBeltLevelMockMvc;

    private BeltLevel beltLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeltLevelResource beltLevelResource = new BeltLevelResource(beltLevelRepository);
        this.restBeltLevelMockMvc = MockMvcBuilders.standaloneSetup(beltLevelResource)
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
    public static BeltLevel createEntity(EntityManager em) {
        BeltLevel beltLevel = new BeltLevel()
            .level(DEFAULT_LEVEL);
        return beltLevel;
    }

    @Before
    public void initTest() {
        beltLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeltLevel() throws Exception {
        int databaseSizeBeforeCreate = beltLevelRepository.findAll().size();

        // Create the BeltLevel
        restBeltLevelMockMvc.perform(post("/api/belt-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beltLevel)))
            .andExpect(status().isCreated());

        // Validate the BeltLevel in the database
        List<BeltLevel> beltLevelList = beltLevelRepository.findAll();
        assertThat(beltLevelList).hasSize(databaseSizeBeforeCreate + 1);
        BeltLevel testBeltLevel = beltLevelList.get(beltLevelList.size() - 1);
        assertThat(testBeltLevel.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createBeltLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beltLevelRepository.findAll().size();

        // Create the BeltLevel with an existing ID
        beltLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeltLevelMockMvc.perform(post("/api/belt-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beltLevel)))
            .andExpect(status().isBadRequest());

        // Validate the BeltLevel in the database
        List<BeltLevel> beltLevelList = beltLevelRepository.findAll();
        assertThat(beltLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = beltLevelRepository.findAll().size();
        // set the field null
        beltLevel.setLevel(null);

        // Create the BeltLevel, which fails.

        restBeltLevelMockMvc.perform(post("/api/belt-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beltLevel)))
            .andExpect(status().isBadRequest());

        List<BeltLevel> beltLevelList = beltLevelRepository.findAll();
        assertThat(beltLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeltLevels() throws Exception {
        // Initialize the database
        beltLevelRepository.saveAndFlush(beltLevel);

        // Get all the beltLevelList
        restBeltLevelMockMvc.perform(get("/api/belt-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beltLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }
    

    @Test
    @Transactional
    public void getBeltLevel() throws Exception {
        // Initialize the database
        beltLevelRepository.saveAndFlush(beltLevel);

        // Get the beltLevel
        restBeltLevelMockMvc.perform(get("/api/belt-levels/{id}", beltLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beltLevel.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBeltLevel() throws Exception {
        // Get the beltLevel
        restBeltLevelMockMvc.perform(get("/api/belt-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeltLevel() throws Exception {
        // Initialize the database
        beltLevelRepository.saveAndFlush(beltLevel);

        int databaseSizeBeforeUpdate = beltLevelRepository.findAll().size();

        // Update the beltLevel
        BeltLevel updatedBeltLevel = beltLevelRepository.findById(beltLevel.getId()).get();
        // Disconnect from session so that the updates on updatedBeltLevel are not directly saved in db
        em.detach(updatedBeltLevel);
        updatedBeltLevel
            .level(UPDATED_LEVEL);

        restBeltLevelMockMvc.perform(put("/api/belt-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeltLevel)))
            .andExpect(status().isOk());

        // Validate the BeltLevel in the database
        List<BeltLevel> beltLevelList = beltLevelRepository.findAll();
        assertThat(beltLevelList).hasSize(databaseSizeBeforeUpdate);
        BeltLevel testBeltLevel = beltLevelList.get(beltLevelList.size() - 1);
        assertThat(testBeltLevel.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingBeltLevel() throws Exception {
        int databaseSizeBeforeUpdate = beltLevelRepository.findAll().size();

        // Create the BeltLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBeltLevelMockMvc.perform(put("/api/belt-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beltLevel)))
            .andExpect(status().isBadRequest());

        // Validate the BeltLevel in the database
        List<BeltLevel> beltLevelList = beltLevelRepository.findAll();
        assertThat(beltLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeltLevel() throws Exception {
        // Initialize the database
        beltLevelRepository.saveAndFlush(beltLevel);

        int databaseSizeBeforeDelete = beltLevelRepository.findAll().size();

        // Get the beltLevel
        restBeltLevelMockMvc.perform(delete("/api/belt-levels/{id}", beltLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BeltLevel> beltLevelList = beltLevelRepository.findAll();
        assertThat(beltLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeltLevel.class);
        BeltLevel beltLevel1 = new BeltLevel();
        beltLevel1.setId(1L);
        BeltLevel beltLevel2 = new BeltLevel();
        beltLevel2.setId(beltLevel1.getId());
        assertThat(beltLevel1).isEqualTo(beltLevel2);
        beltLevel2.setId(2L);
        assertThat(beltLevel1).isNotEqualTo(beltLevel2);
        beltLevel1.setId(null);
        assertThat(beltLevel1).isNotEqualTo(beltLevel2);
    }
}
