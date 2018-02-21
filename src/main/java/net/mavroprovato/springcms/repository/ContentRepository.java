package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Integer> {
}
