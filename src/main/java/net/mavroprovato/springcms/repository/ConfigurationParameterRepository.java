package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.ConfigurationParameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The category repository
 */
public interface ConfigurationParameterRepository extends JpaRepository<ConfigurationParameter, Integer> {
}
