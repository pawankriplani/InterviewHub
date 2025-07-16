package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.UserRole;
import com.example.interview_hub.model.entity.UserRole.UserRoleId;
import com.example.interview_hub.model.entity.User;
import com.example.interview_hub.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser(User user);
    List<UserRole> findByRole(Role role);
    void deleteByUserAndRole(User user, Role role);
}
