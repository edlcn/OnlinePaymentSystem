package com.example.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDAO extends JpaRepository<Users,Long> {

    Users findByMail(String mail);
    Users findUsersById(Long id);
}
