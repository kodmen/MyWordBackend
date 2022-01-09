package com.bedir.mywords.web.rest;

import com.bedir.mywords.MyWordsApp;
import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.repository.DesteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DesteResource} REST controller.
 */
@SpringBootTest(classes = MyWordsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DesteResourceIT {

    private static final String DEFAULT_RENK = "AAAAAAAAAA";
    private static final String UPDATED_RENK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DesteRepository desteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesteMockMvc;

    private Deste deste;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deste createEntity(EntityManager em) {
        Deste deste = new Deste()
            .renk(DEFAULT_RENK)
            .name(DEFAULT_NAME);
        return deste;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deste createUpdatedEntity(EntityManager em) {
        Deste deste = new Deste()
            .renk(UPDATED_RENK)
            .name(UPDATED_NAME);
        return deste;
    }

    @BeforeEach
    public void initTest() {
        deste = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeste() throws Exception {
        int databaseSizeBeforeCreate = desteRepository.findAll().size();
        // Create the Deste
        restDesteMockMvc.perform(post("/api/destes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deste)))
            .andExpect(status().isCreated());

        // Validate the Deste in the database
        List<Deste> desteList = desteRepository.findAll();
        assertThat(desteList).hasSize(databaseSizeBeforeCreate + 1);
        Deste testDeste = desteList.get(desteList.size() - 1);
        assertThat(testDeste.getRenk()).isEqualTo(DEFAULT_RENK);
        assertThat(testDeste.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDesteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = desteRepository.findAll().size();

        // Create the Deste with an existing ID
        deste.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesteMockMvc.perform(post("/api/destes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deste)))
            .andExpect(status().isBadRequest());

        // Validate the Deste in the database
        List<Deste> desteList = desteRepository.findAll();
        assertThat(desteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDestes() throws Exception {
        // Initialize the database
        desteRepository.saveAndFlush(deste);

        // Get all the desteList
        restDesteMockMvc.perform(get("/api/destes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deste.getId().intValue())))
            .andExpect(jsonPath("$.[*].renk").value(hasItem(DEFAULT_RENK)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDeste() throws Exception {
        // Initialize the database
        desteRepository.saveAndFlush(deste);

        // Get the deste
        restDesteMockMvc.perform(get("/api/destes/{id}", deste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deste.getId().intValue()))
            .andExpect(jsonPath("$.renk").value(DEFAULT_RENK))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDeste() throws Exception {
        // Get the deste
        restDesteMockMvc.perform(get("/api/destes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeste() throws Exception {
        // Initialize the database
        desteRepository.saveAndFlush(deste);

        int databaseSizeBeforeUpdate = desteRepository.findAll().size();

        // Update the deste
        Deste updatedDeste = desteRepository.findById(deste.getId()).get();
        // Disconnect from session so that the updates on updatedDeste are not directly saved in db
        em.detach(updatedDeste);
        updatedDeste
            .renk(UPDATED_RENK)
            .name(UPDATED_NAME);

        restDesteMockMvc.perform(put("/api/destes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeste)))
            .andExpect(status().isOk());

        // Validate the Deste in the database
        List<Deste> desteList = desteRepository.findAll();
        assertThat(desteList).hasSize(databaseSizeBeforeUpdate);
        Deste testDeste = desteList.get(desteList.size() - 1);
        assertThat(testDeste.getRenk()).isEqualTo(UPDATED_RENK);
        assertThat(testDeste.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeste() throws Exception {
        int databaseSizeBeforeUpdate = desteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesteMockMvc.perform(put("/api/destes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deste)))
            .andExpect(status().isBadRequest());

        // Validate the Deste in the database
        List<Deste> desteList = desteRepository.findAll();
        assertThat(desteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeste() throws Exception {
        // Initialize the database
        desteRepository.saveAndFlush(deste);

        int databaseSizeBeforeDelete = desteRepository.findAll().size();

        // Delete the deste
        restDesteMockMvc.perform(delete("/api/destes/{id}", deste.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deste> desteList = desteRepository.findAll();
        assertThat(desteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
