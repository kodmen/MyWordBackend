package com.bedir.mywords.web.rest;

import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.repository.KartRepository;
import com.bedir.mywords.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bedir.mywords.domain.Kart}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KartResource {

    private final Logger log = LoggerFactory.getLogger(KartResource.class);

    private static final String ENTITY_NAME = "kart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KartRepository kartRepository;

    public KartResource(KartRepository kartRepository) {
        this.kartRepository = kartRepository;
    }

    /**
     * {@code POST  /karts} : Create a new kart.
     *
     * @param kart the kart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kart, or with status {@code 400 (Bad Request)} if the kart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/karts")
    public ResponseEntity<Kart> createKart(@RequestBody Kart kart) throws URISyntaxException {
        log.debug("REST request to save Kart : {}", kart);
        if (kart.getId() != null) {
            throw new BadRequestAlertException("A new kart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kart result = kartRepository.save(kart);
        return ResponseEntity.created(new URI("/api/karts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /karts} : Updates an existing kart.
     *
     * @param kart the kart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kart,
     * or with status {@code 400 (Bad Request)} if the kart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/karts")
    public ResponseEntity<Kart> updateKart(@RequestBody Kart kart) throws URISyntaxException {
        log.debug("REST request to update Kart : {}", kart);
        if (kart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kart result = kartRepository.save(kart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kart.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /karts} : get all the karts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of karts in body.
     */
    @GetMapping("/karts")
    public List<Kart> getAllKarts() {
        log.debug("REST request to get all Karts");
        return kartRepository.findAll();
    }

    /**
     * {@code GET  /karts/:id} : get the "id" kart.
     *
     * @param id the id of the kart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/karts/{id}")
    public ResponseEntity<Kart> getKart(@PathVariable Long id) {
        log.debug("REST request to get Kart : {}", id);
        Optional<Kart> kart = kartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kart);
    }

    /**
     * {@code DELETE  /karts/:id} : delete the "id" kart.
     *
     * @param id the id of the kart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/karts/{id}")
    public ResponseEntity<Void> deleteKart(@PathVariable Long id) {
        log.debug("REST request to delete Kart : {}", id);
        kartRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
