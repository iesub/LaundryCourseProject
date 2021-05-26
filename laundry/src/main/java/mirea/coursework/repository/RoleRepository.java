package mirea.coursework.repository;

import mirea.coursework.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
Репозиторий для ролей
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
}
