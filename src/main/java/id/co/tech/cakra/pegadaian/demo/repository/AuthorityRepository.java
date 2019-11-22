package id.co.tech.cakra.pegadaian.demo.repository;

import id.co.tech.cakra.pegadaian.demo.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
