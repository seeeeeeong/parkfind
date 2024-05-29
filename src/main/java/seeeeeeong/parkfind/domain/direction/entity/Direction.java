package seeeeeeong.parkfind.domain.direction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seeeeeeong.parkfind.common.entity.BaseTimeEntity;

@Getter
@Entity(name = "direction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    private String targetParkName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    private double distance;

    @Builder
    private Direction(String inputAddress, double inputLatitude, double inputLongitude, String targetParkName, String targetAddress, double targetLatitude, double targetLongitude, double distance) {
        this.inputAddress = inputAddress;
        this.inputLatitude = inputLatitude;
        this.inputLongitude = inputLongitude;
        this.targetParkName = targetParkName;
        this.targetAddress = targetAddress;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.distance = distance;
    }

    public Direction of(String inputAddress, double inputLatitude, double inputLongitude, String targetParkName, String targetAddress, double targetLatitude, double targetLongitude, double distance) {
        return Direction.builder()
                .inputAddress(inputAddress)
                .inputLatitude(inputLatitude)
                .inputLongitude(inputLongitude)
                .targetParkName(targetParkName)
                .targetAddress(targetAddress)
                .targetLatitude(targetLatitude)
                .targetLongitude(targetLongitude)
                .targetLongitude(targetLongitude)
                .build();
    }
}
