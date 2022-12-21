package com.payment.creditcarddatabase.virtualPosDatabase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualPosDatabaseDAO extends JpaRepository<VirtualPosDatabase,Long> {
    VirtualPosDatabase findVirtualPosDatabaseById(Long id);
}
