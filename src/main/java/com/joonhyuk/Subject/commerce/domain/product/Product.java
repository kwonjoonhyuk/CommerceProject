package com.joonhyuk.Subject.commerce.domain.product;

import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.product.form.AddProductForm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  private String category;
  private String name;
  private Integer price;
  private String description;
  private Double stars;
  private Long sellerId;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "productId")
  private List<ProductOption> productOptions = new ArrayList<>();


  public static Product of(Long sellerId, AddProductForm form) {

    return Product.builder()
        .sellerId(sellerId)
        .category(form.getCategory())
        .name(form.getName())
        .price(form.getPrice())
        .description(form.getDescription())
        .productOptions(form.getOptionFormList().stream()
            .map(productOptionForm -> ProductOption.of(sellerId, productOptionForm))
            .collect(Collectors.toList()))
        .stars(0.0)
        .build();
  }


}
