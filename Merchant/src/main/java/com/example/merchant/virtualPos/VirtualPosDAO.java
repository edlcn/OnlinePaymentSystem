package com.example.merchant.virtualPos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualPosDAO extends JpaRepository<VirtualPos,Long> {
    VirtualPos findVirtualPosById(Long id);
}
