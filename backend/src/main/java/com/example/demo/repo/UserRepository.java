package com.example.demo.repo;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

     List<Users> findUsersByEmail(String Email);

     List<Users> findUsersById(Long id);

//    public List<Users> findAll();

     Boolean existsByEmail(String email);

//    public List<Users> findByEmailAndPassword(String  email, String password);

    Optional<Users> findByEmail(String email);

    void deleteUsersByEmail(String email);

    List<Users> findAllByRole(String role);

    List<Users> id(Long id);

    @Query("""
        SELECT new com.example.demo.dto.UserResponseDTO(
            u.id,
            u.firstName,
            u.lastName,
            u.email,
            u.phoneNo,
            u.address,
            u.role,
            u.status,
            COUNT(o),
            u.createdAt
        )
        FROM Users u
        LEFT JOIN Orders o ON o.user = u
        where u.role = 'USER'
        GROUP BY
            u.id,
            u.firstName,
            u.lastName,
            u.email,
            u.phoneNo,
            u.address,
            u.role,
            u.status,
            u.createdAt
    """)
    List<UserResponseDTO> getUsersForAdmin();
}
