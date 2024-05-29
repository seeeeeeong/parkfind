package seeeeeeong.parkfind.domain.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MetaDto {

    @JsonProperty("total_count")
    private Integer totalCount;

}
