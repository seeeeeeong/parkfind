package seeeeeeong.parkfind.domain.park.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkDto {
    private Long id;
    private String parkName;
    private String parkAddress;
    private double latitude;
    private double longitude;
}
