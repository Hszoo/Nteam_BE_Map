package com.Nteam.backend.map.dto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapDto {
    // 주소
    private String address_name;

    // 경도
    private double longitude;

    // 위도
    private double latitude;
}

