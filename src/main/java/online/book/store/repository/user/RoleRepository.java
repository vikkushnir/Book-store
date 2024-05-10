package online.book.store.repository.user;

import java.util.Optional;
import online.book.store.model.Role;
import online.book.store.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
