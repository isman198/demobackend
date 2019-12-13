package id.co.tech.cakra.pegadaian.demo.service.impl;

import id.co.tech.cakra.pegadaian.demo.service.EfekService;
import id.co.tech.cakra.pegadaian.demo.domain.Efek;
import id.co.tech.cakra.pegadaian.demo.repository.EfekRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Efek.
 */
@Service
@Transactional
public class EfekServiceImpl implements EfekService {

    private final Logger log = LoggerFactory.getLogger(EfekServiceImpl.class);

    private final EfekRepository efekRepository;

    public EfekServiceImpl(EfekRepository efekRepository) {
        this.efekRepository = efekRepository;
    }

    /**
     * Save a efek.
     *
     * @param efek the entity to save
     * @return the persisted entity
     */
    @Override
    public Efek save(Efek efek) {
        log.debug("Request to save Efek : {}", efek);
        return efekRepository.save(efek);
    }

    /**
     * Get all the efeks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Efek> findAll() {
        log.debug("Request to get all Efeks");
        return efekRepository.findAll();
    }


    /**
     * Get one efek by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Efek> findOne(Long id) {
        log.debug("Request to get Efek : {}", id);
        return efekRepository.findById(id);
    }

    /**
     * Delete the efek by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Efek : {}", id);        efekRepository.deleteById(id);
    }
}
