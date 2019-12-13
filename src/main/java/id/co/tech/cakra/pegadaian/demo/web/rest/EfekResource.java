package id.co.tech.cakra.pegadaian.demo.web.rest;
import id.co.tech.cakra.pegadaian.demo.domain.Efek;
import id.co.tech.cakra.pegadaian.demo.service.EfekService;
import id.co.tech.cakra.pegadaian.demo.web.rest.errors.BadRequestAlertException;
import id.co.tech.cakra.pegadaian.demo.web.rest.util.HeaderUtil;
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
 * REST controller for managing Efek.
 */
@RestController
@RequestMapping("/api")
public class EfekResource {

    private final Logger log = LoggerFactory.getLogger(EfekResource.class);

    private static final String ENTITY_NAME = "efek";

    private final EfekService efekService;

    public EfekResource(EfekService efekService) {
        this.efekService = efekService;
    }

    /**
     * POST  /efeks : Create a new efek.
     *
     * @param efek the efek to create
     * @return the ResponseEntity with status 201 (Created) and with body the new efek, or with status 400 (Bad Request) if the efek has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/efeks")
    public ResponseEntity<Efek> createEfek(@Valid @RequestBody Efek efek) throws URISyntaxException {
        log.debug("REST request to save Efek : {}", efek);
        if (efek.getId() != null) {
            throw new BadRequestAlertException("A new efek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Efek result = efekService.save(efek);
        return ResponseEntity.created(new URI("/api/efeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /efeks : Updates an existing efek.
     *
     * @param efek the efek to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated efek,
     * or with status 400 (Bad Request) if the efek is not valid,
     * or with status 500 (Internal Server Error) if the efek couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/efeks")
    public ResponseEntity<Efek> updateEfek(@Valid @RequestBody Efek efek) throws URISyntaxException {
        log.debug("REST request to update Efek : {}", efek);
        if (efek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Efek result = efekService.save(efek);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, efek.getId().toString()))
            .body(result);
    }

    /**
     * GET  /efeks : get all the efeks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of efeks in body
     */
    @GetMapping("/efeks")
    public List<Efek> getAllEfeks() {
        log.debug("REST request to get all Efeks");
        return efekService.findAll();
    }

    /**
     * GET  /efeks/:id : get the "id" efek.
     *
     * @param id the id of the efek to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the efek, or with status 404 (Not Found)
     */
    @GetMapping("/efeks/{id}")
    public ResponseEntity<Efek> getEfek(@PathVariable Long id) {
        log.debug("REST request to get Efek : {}", id);
        Optional<Efek> efek = efekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(efek);
    }

    /**
     * DELETE  /efeks/:id : delete the "id" efek.
     *
     * @param id the id of the efek to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/efeks/{id}")
    public ResponseEntity<Void> deleteEfek(@PathVariable Long id) {
        log.debug("REST request to delete Efek : {}", id);
        efekService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
