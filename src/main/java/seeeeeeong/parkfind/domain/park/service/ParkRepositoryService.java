package seeeeeeong.parkfind.domain.park.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seeeeeeong.parkfind.domain.park.entity.Park;
import seeeeeeong.parkfind.domain.park.repository.ParkRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkRepositoryService {

    private final ParkRepository parkRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        Park entity = parkRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)) {
            return;
        }

        entity.changeParkAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Park> findAll() {
        return parkRepository.findAll();
    }
}
