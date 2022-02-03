package com.bedir.mywords.web.rest;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.repository.DesteRepository;
import com.bedir.mywords.service.DesteService;
import com.bedir.mywords.service.dto.DesteDTO;
import com.bedir.mywords.service.utilities.DataResult;
import com.bedir.mywords.service.utilities.Result;
import com.bedir.mywords.service.utilities.SuccessResult;
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
 * REST controller for managing {@link com.bedir.mywords.domain.Deste}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DesteResource {

    private final Logger log = LoggerFactory.getLogger(DesteResource.class);

    private static final String ENTITY_NAME = "deste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesteRepository desteRepository;

    private final DesteService desteService;

    public DesteResource(DesteRepository desteRepository, DesteService desteService) {
        this.desteRepository = desteRepository;
        this.desteService = desteService;
    }

    /**
     * {@code POST  /destes} : Create a new deste.
     *
     * @param deste the deste to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deste, or with status {@code 400 (Bad Request)} if the deste has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/destes")
    public ResponseEntity<Deste> createDeste(@RequestBody Deste deste) throws URISyntaxException {
        log.debug("REST request to save Deste : {}", deste);
        if (deste.getId() != null) {
            throw new BadRequestAlertException("A new deste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deste result = desteRepository.save(deste);
        return ResponseEntity.created(new URI("/api/destes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/destes-user")
    public ResponseEntity<Deste> createDesteForUser(@RequestBody DesteDTO deste) throws URISyntaxException {
        log.debug("REST request to save Deste : {}", deste);

        Deste result = desteService.createDeste(deste);
        return ResponseEntity.created(new URI("/api/destes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destes} : Updates an existing deste.
     *
     * @param deste the deste to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deste,
     * or with status {@code 400 (Bad Request)} if the deste is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deste couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destes")
    public ResponseEntity<Deste> updateDeste(@RequestBody Deste deste) throws URISyntaxException {
        log.debug("REST request to update Deste : {}", deste);
        if (deste.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deste result = desteRepository.save(deste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deste.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destes} : Updates an existing deste.
     *
     * @param deste the deste to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deste,
     * or with status {@code 400 (Bad Request)} if the deste is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deste couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destes-user")
    public DataResult<Deste> updateDesteForUser(@RequestBody DesteDTO deste) throws URISyntaxException {
        log.debug("REST request to update Deste : {}", deste);
        return desteService.updateDeste(deste);

    }

    /**
     * {@code GET  /destes} : get all the destes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destes in body.
     */
    @GetMapping("/destes")
    public List<Deste> getAllDestes() {
        log.debug("REST request to get all Destes");
        return desteRepository.findAll();
    }

    /**
     *
     * @return
     */
    @GetMapping("/deste-user")
    public DataResult<List<Deste>> getAllDestesForUser() {
        log.debug("REST request to get all Destes");
        return desteService.getUserIdAllDeste();
    }

    /**
     * {@code GET  /destes/:id} : get the "id" deste.
     *
     * @param id the id of the deste to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deste, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/destes/{id}")
    public DataResult<Deste> getDeste(@PathVariable Long id) {
        log.debug("REST request to get Deste : {}", id);
        return desteService.getOneDeste(id);
//        Optional<Deste> deste = desteRepository.findById(id);
//        return ResponseUtil.wrapOrNotFound(deste);
    }

    /**
     * {@code DELETE  /destes/:id} : delete the "id" deste.
     *
     * @param id the id of the deste to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/destes/{id}")
    public Result deleteDeste(@PathVariable Long id) {
        log.debug("REST request to delete Deste : {}", id);
        return desteService.deleteDeste(id);

//        desteRepository.deleteById(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
