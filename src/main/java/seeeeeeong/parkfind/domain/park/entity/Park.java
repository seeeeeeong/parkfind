package seeeeeeong.parkfind.domain.park.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seeeeeeong.parkfind.common.entity.BaseTimeEntity;

@Getter
@Entity(name = "park")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Park extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parkName;
    private String parkAddress;
    private double latitude;
    private double longitude;

    @Builder
    private Park(String parkName, String parkAddress, double latitude, double longitude) {
        this.parkName = parkName;
        this.parkAddress = parkAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Park of(String parkName, String parkAddress, double latitude, double longitude) {
        return Park.builder()
                .parkName(parkName)
                .parkAddress(parkAddress)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public void changeParkAddress(String address) {
        this.parkAddress = address;
    }
}
