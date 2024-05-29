package seeeeeeong.parkfind.domain.direction.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputDto {
    private String parkName;
    private String parkAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;
}
