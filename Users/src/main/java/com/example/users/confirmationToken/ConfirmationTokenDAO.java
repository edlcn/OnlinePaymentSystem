package com.example.users.confirmationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmationTokenDAO extends JpaRepository<ConfirmationToken,Long> {

    ConfirmationToken findConfirmationTokenByToken(String token);
}
