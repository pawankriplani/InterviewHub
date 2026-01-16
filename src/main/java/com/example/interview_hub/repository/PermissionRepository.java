package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByPermissionName(String permissionName);
    List<Permission> findByPermissionNameIn(List<String> permissionNames);
    boolean existsByPermissionName(String permissionName);
}
