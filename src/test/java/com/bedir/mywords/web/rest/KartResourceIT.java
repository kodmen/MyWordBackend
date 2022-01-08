package com.bedir.mywords.web.rest;

import com.bedir.mywords.MyWordsApp;
import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.repository.KartRepository;

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
 * Integration tests for the {@link KartResource} REST controller.
 */
@SpringBootTest(classes = MyWordsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KartResourceIT {

    private static final String DEFAULT_ON_YUZ = "AAAAAAAAAA";
    private static final String UPDATED_ON_YUZ = "BBBBBBBBBB";

    private static final String DEFAULT_ARKA_YUZ = "AAAAAAAAAA";
    private static final String UPDATED_ARKA_YUZ = "BBBBBBBBBB";

    private static final Integer DEFAULT_ONEM_SIRA = 1;
    private static final Integer UPDATED_ONEM_SIRA = 2;

    @Autowired
    private KartRepository kartRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKartMockMvc;

    private Kart kart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kart createEntity(EntityManager em) {
        Kart kart = new Kart()
            .onYuz(DEFAULT_ON_YUZ)
            .arkaYuz(DEFAULT_ARKA_YUZ)
            .onemSira(DEFAULT_ONEM_SIRA);
        return kart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kart createUpdatedEntity(EntityManager em) {
        Kart kart = new Kart()
            .onYuz(UPDATED_ON_YUZ)
            .arkaYuz(UPDATED_ARKA_YUZ)
            .onemSira(UPDATED_ONEM_SIRA);
        return kart;
    }

    @BeforeEach
    public void initTest() {
        kart = createEntity(em);
    }

    @Test
    @Transactional
    public void createKart() throws Exception {
        int databaseSizeBeforeCreate = kartRepository.findAll().size();
        // Create the Kart
        restKartMockMvc.perform(post("/api/karts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kart)))
            .andExpect(status().isCreated());

        // Validate the Kart in the database
        List<Kart> kartList = kartRepository.findAll();
        assertThat(kartList).hasSize(databaseSizeBeforeCreate + 1);
        Kart testKart = kartList.get(kartList.size() - 1);
        assertThat(testKart.getOnYuz()).isEqualTo(DEFAULT_ON_YUZ);
        assertThat(testKart.getArkaYuz()).isEqualTo(DEFAULT_ARKA_YUZ);
        assertThat(testKart.getOnemSira()).isEqualTo(DEFAULT_ONEM_SIRA);
    }

    @Test
    @Transactional
    public void createKartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kartRepository.findAll().size();

        // Create the Kart with an existing ID
        kart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKartMockMvc.perform(post("/api/karts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kart)))
            .andExpect(status().isBadRequest());

        // Validate the Kart in the database
        List<Kart> kartList = kartRepository.findAll();
        assertThat(kartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKarts() throws Exception {
        // Initialize the database
        kartRepository.saveAndFlush(kart);

        // Get all the kartList
        restKartMockMvc.perform(get("/api/karts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kart.getId().intValue())))
            .andExpect(jsonPath("$.[*].onYuz").value(hasItem(DEFAULT_ON_YUZ)))
            .andExpect(jsonPath("$.[*].arkaYuz").value(hasItem(DEFAULT_ARKA_YUZ)))
            .andExpect(jsonPath("$.[*].onemSira").value(hasItem(DEFAULT_ONEM_SIRA)));
    }
    
    @Test
    @Transactional
    public void getKart() throws Exception {
        // Initialize the database
        kartRepository.saveAndFlush(kart);

        // Get the kart
        restKartMockMvc.perform(get("/api/karts/{id}", kart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kart.getId().intValue()))
            .andExpect(jsonPath("$.onYuz").value(DEFAULT_ON_YUZ))
            .andExpect(jsonPath("$.arkaYuz").value(DEFAULT_ARKA_YUZ))
            .andExpect(jsonPath("$.onemSira").value(DEFAULT_ONEM_SIRA));
    }
    @Test
    @Transactional
    public void getNonExistingKart() throws Exception {
        // Get the kart
        restKartMockMvc.perform(get("/api/karts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKart() throws Exception {
        // Initialize the database
        kartRepository.saveAndFlush(kart);

        int databaseSizeBeforeUpdate = kartRepository.findAll().size();

        // Update the kart
        Kart updatedKart = kartRepository.findById(kart.getId()).get();
        // Disconnect from session so that the updates on updatedKart are not directly saved in db
        em.detach(updatedKart);
        updatedKart
            .onYuz(UPDATED_ON_YUZ)
            .arkaYuz(UPDATED_ARKA_YUZ)
            .onemSira(UPDATED_ONEM_SIRA);

        restKartMockMvc.perform(put("/api/karts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKart)))
            .andExpect(status().isOk());

        // Validate the Kart in the database
        List<Kart> kartList = kartRepository.findAll();
        assertThat(kartList).hasSize(databaseSizeBeforeUpdate);
        Kart testKart = kartList.get(kartList.size() - 1);
        assertThat(testKart.getOnYuz()).isEqualTo(UPDATED_ON_YUZ);
        assertThat(testKart.getArkaYuz()).isEqualTo(UPDATED_ARKA_YUZ);
        assertThat(testKart.getOnemSira()).isEqualTo(UPDATED_ONEM_SIRA);
    }

    @Test
    @Transactional
    public void updateNonExistingKart() throws Exception {
        int databaseSizeBeforeUpdate = kartRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKartMockMvc.perform(put("/api/karts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kart)))
            .andExpect(status().isBadRequest());

        // Validate the Kart in the database
        List<Kart> kartList = kartRepository.findAll();
        assertThat(kartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKart() throws Exception {
        // Initialize the database
        kartRepository.saveAndFlush(kart);

        int databaseSizeBeforeDelete = kartRepository.findAll().size();

        // Delete the kart
        restKartMockMvc.perform(delete("/api/karts/{id}", kart.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kart> kartList = kartRepository.findAll();
        assertThat(kartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
