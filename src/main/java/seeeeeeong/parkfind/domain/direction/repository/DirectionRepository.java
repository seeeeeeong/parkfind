package seeeeeeong.parkfind.domain.direction.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seeeeeeong.parkfind.domain.direction.entity.Direction;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
