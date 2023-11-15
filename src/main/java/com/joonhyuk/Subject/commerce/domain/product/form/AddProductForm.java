package com.joonhyuk.Subject.commerce.domain.product.form;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductForm {

  @NotBlank(message = "카테고리를 설정해주세요")
  private String category;

  @NotBlank(message = "상품명을 입력해주세요")
  private String name;

  @NotBlank(message = "가격을 설정해주세요")
  private Integer price;

  @NotBlank(message = "상품에 대한 설명을 입력해주세요")
  private String description;

  @NotBlank(message = "한개 이상의 상품 옵션을 등록해주세요")
  private final List<AddProductOptionForm> optionFormList = new ArrayList<>();

}
