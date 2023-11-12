package com.joonhyuk.Subject.commerce.domain.Customer;

import com.joonhyuk.Subject.commerce.components.KaKaoAddress;
import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.Customer.form.AddAddressForm;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class DeliveryAddress extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long customerId;
  private String address;
  private String addressDetail;
  private String zoneNo;

  public static DeliveryAddress of(Long customerId, AddAddressForm form) {

    KaKaoAddress kaKaoAddress = new KaKaoAddress();
    ArrayList<String> arrayList = kaKaoAddress.getLocation(form.getAddress());

    return DeliveryAddress.builder()
        .customerId(customerId)
        .address(arrayList.get(0))
        .addressDetail(form.getAddress_detail())
        .zoneNo(arrayList.get(2))
        .build();
  }

}
