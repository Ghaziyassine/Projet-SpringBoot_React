package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TypePlante;
import com.mycompany.myapp.repository.TypePlanteRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.TypePlante}.
 */
@RestController
@RequestMapping("/api/type-plantes")
@Transactional
public class TypePlanteResource {

    private final Logger log = LoggerFactory.getLogger(TypePlanteResource.class);

    private static final String ENTITY_NAME = "typePlante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePlanteRepository typePlanteRepository;

    public TypePlanteResource(TypePlanteRepository typePlanteRepository) {
        this.typePlanteRepository = typePlanteRepository;
    }

    /**
     * {@code POST  /type-plantes} : Create a new typePlante.
     *
     * @param typePlante the typePlante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePlante, or with status {@code 400 (Bad Request)} if the typePlante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypePlante> createTypePlante(@RequestBody TypePlante typePlante) throws URISyntaxException {
        log.debug("REST request to save TypePlante : {}", typePlante);
        if (typePlante.getId() != null) {
            throw new BadRequestAlertException("A new typePlante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePlante result = typePlanteRepository.save(typePlante);
        return ResponseEntity
            .created(new URI("/api/type-plantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-plantes/:id} : Updates an existing typePlante.
     *
     * @param id the id of the typePlante to save.
     * @param typePlante the typePlante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePlante,
     * or with status {@code 400 (Bad Request)} if the typePlante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePlante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypePlante> updateTypePlante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypePlante typePlante
    ) throws URISyntaxException {
        log.debug("REST request to update TypePlante : {}, {}", id, typePlante);
        if (typePlante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typePlante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typePlanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypePlante result = typePlanteRepository.save(typePlante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePlante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-plantes/:id} : Partial updates given fields of an existing typePlante, field will ignore if it is null
     *
     * @param id the id of the typePlante to save.
     * @param typePlante the typePlante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePlante,
     * or with status {@code 400 (Bad Request)} if the typePlante is not valid,
     * or with status {@code 404 (Not Found)} if the typePlante is not found,
     * or with status {@code 500 (Internal Server Error)} if the typePlante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypePlante> partialUpdateTypePlante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypePlante typePlante
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypePlante partially : {}, {}", id, typePlante);
        if (typePlante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typePlante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typePlanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypePlante> result = typePlanteRepository
            .findById(typePlante.getId())
            .map(existingTypePlante -> {
                if (typePlante.getNom() != null) {
                    existingTypePlante.setNom(typePlante.getNom());
                }
                if (typePlante.getHumiditeMax() != null) {
                    existingTypePlante.setHumiditeMax(typePlante.getHumiditeMax());
                }
                if (typePlante.getHumiditeMin() != null) {
                    existingTypePlante.setHumiditeMin(typePlante.getHumiditeMin());
                }
                if (typePlante.getTemperature() != null) {
                    existingTypePlante.setTemperature(typePlante.getTemperature());
                }

                return existingTypePlante;
            })
            .map(typePlanteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePlante.getId().toString())
        );
    }

    /**
     * {@code GET  /type-plantes} : get all the typePlantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePlantes in body.
     */
    @GetMapping("")
    public List<TypePlante> getAllTypePlantes() {
        log.debug("REST request to get all TypePlantes");
        return typePlanteRepository.findAll();
    }

    /**
     * {@code GET  /type-plantes/:id} : get the "id" typePlante.
     *
     * @param id the id of the typePlante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePlante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypePlante> getTypePlante(@PathVariable("id") Long id) {
        log.debug("REST request to get TypePlante : {}", id);
        Optional<TypePlante> typePlante = typePlanteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typePlante);
    }

    /**
     * {@code DELETE  /type-plantes/:id} : delete the "id" typePlante.
     *
     * @param id the id of the typePlante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypePlante(@PathVariable("id") Long id) {
        log.debug("REST request to delete TypePlante : {}", id);
        typePlanteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
