package seeeeeeong.parkfind.domain.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seeeeeeong.parkfind.domain.park.entity.Park;

@Repository
public interface ParkRepository extends JpaRepository<Park, Long> {
}
