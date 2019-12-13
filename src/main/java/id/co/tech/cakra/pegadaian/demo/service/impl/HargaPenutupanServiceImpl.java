package id.co.tech.cakra.pegadaian.demo.service.impl;

import id.co.tech.cakra.pegadaian.demo.service.HargaPenutupanService;
import id.co.tech.cakra.pegadaian.demo.domain.HargaPenutupan;
import id.co.tech.cakra.pegadaian.demo.repository.HargaPenutupanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HargaPenutupan.
 */
@Service
@Transactional
public class HargaPenutupanServiceImpl implements HargaPenutupanService {

    private final Logger log = LoggerFactory.getLogger(HargaPenutupanServiceImpl.class);

    private final HargaPenutupanRepository hargaPenutupanRepository;

    public HargaPenutupanServiceImpl(HargaPenutupanRepository hargaPenutupanRepository) {
        this.hargaPenutupanRepository = hargaPenutupanRepository;
    }

    /**
     * Save a hargaPenutupan.
     *
     * @param hargaPenutupan the entity to save
     * @return the persisted entity
     */
    @Override
    public HargaPenutupan save(HargaPenutupan hargaPenutupan) {
        log.debug("Request to save HargaPenutupan : {}", hargaPenutupan);
        return hargaPenutupanRepository.save(hargaPenutupan);
    }

    /**
     * Get all the hargaPenutupans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HargaPenutupan> findAll(Pageable pageable) {
        log.debug("Request to get all HargaPenutupans");
        return hargaPenutupanRepository.findAll(pageable);
    }


    /**
     * Get one hargaPenutupan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HargaPenutupan> findOne(Long id) {
        log.debug("Request to get HargaPenutupan : {}", id);
        return hargaPenutupanRepository.findById(id);
    }

    /**
     * Delete the hargaPenutupan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HargaPenutupan : {}", id);        hargaPenutupanRepository.deleteById(id);
    }
}
