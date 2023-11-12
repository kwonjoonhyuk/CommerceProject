package com.joonhyuk.Subject.commerce.domain.product.form;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductForm {


  private Long id;

  @NotNull
  @NotBlank(message = "카테고리는 공백이 들어갈 수 없습니다.")
  private String category;

  private String name;
  private Integer price;
  private String description;
  private List<UpdateProductOptionForm> optionFormList;
}
