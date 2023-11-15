package com.joonhyuk.Subject.commerce.domain.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDto {

  private Long id;
  private String address;
  private String addressDetail;
  private String zoneNo;

  public static DeliveryAddressDto from(DeliveryAddress address) {
    return DeliveryAddressDto.builder()
        .id(address.getId())
        .address(address.getAddress())
        .addressDetail(address.getAddressDetail())
        .zoneNo(address.getZoneNo())
        .build();
  }


}
