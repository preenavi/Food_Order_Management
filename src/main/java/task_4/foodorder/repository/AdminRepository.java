package task_4.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_4.foodorder.entity.Role;
import task_4.foodorder.entity.User;

import java.util.List;

/**
 * Admin Repository
 * Custom queries for admin operations
 */
@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    /**
     * Find all users with specific role
     */
    List<User> findByRole(Role role);

    /**
     * Find all active users
     */
    List<User> findByIsActiveTrue();

    /**
     * Find all inactive users
     */
    List<User> findByIsActiveFalse();

    /**
     * Count users by role
     */
    Long countByRole(Role role);

    /**
     * Count active users
     */
    Long countByIsActiveTrue();

    /**
     * Custom query to find users by role and active status
     */
    @Query("SELECT u FROM User u WHERE u.role = ?1 AND u.isActive = ?2")
    List<User> findByRoleAndActiveStatus(Role role, Boolean isActive);

    /**
     * Get total count of users excluding admins
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.role != 'ADMIN'")
    Long countNonAdminUsers();
}
