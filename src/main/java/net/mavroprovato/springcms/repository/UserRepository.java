package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The user repository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by its user name.
     *
     * @param userName The user name.
     * @return The user.
     */
    User findOneByUserName(String userName);
}
