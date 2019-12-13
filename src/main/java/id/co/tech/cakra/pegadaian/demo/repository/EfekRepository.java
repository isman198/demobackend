package id.co.tech.cakra.pegadaian.demo.repository;

import id.co.tech.cakra.pegadaian.demo.domain.Efek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Efek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EfekRepository extends JpaRepository<Efek, Long> {

}
