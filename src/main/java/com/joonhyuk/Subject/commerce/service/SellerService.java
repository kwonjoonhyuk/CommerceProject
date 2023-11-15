package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.domain.product.OptionPageResponse;
import com.joonhyuk.Subject.commerce.domain.product.ProductDto;
import com.joonhyuk.Subject.commerce.domain.product.ProductOption;
import com.joonhyuk.Subject.commerce.domain.product.ProductOptionDto;
import com.joonhyuk.Subject.commerce.domain.product.ProductPageResponse;
import com.joonhyuk.Subject.commerce.domain.product.form.AddProductForm;
import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.product.form.AddProductOptionForm;
import com.joonhyuk.Subject.commerce.domain.product.form.UpdateProductForm;
import com.joonhyuk.Subject.commerce.domain.product.form.UpdateProductOptionForm;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductOptionRepository;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final ProductRepository productRepository;
  private final ProductOptionRepository productOptionRepository;

  // 상품 등록
  public Product addProduct(Long sellerId, AddProductForm form) {
    return productRepository.save(Product.of(sellerId, form));
  }

  // 상품 수정
  @Transactional
  public Product updateProduct(Long sellerId, UpdateProductForm form) {

    Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

    product.setCategory(form.getCategory());
    product.setName(form.getName());
    product.setPrice(form.getPrice());
    product.setDescription(form.getDescription());

    for (UpdateProductOptionForm updateForm : form.getOptionFormList()) {
      ProductOption productOption = product.getProductOptions().stream()
          .filter(productItem -> productItem.getId().equals(updateForm.getProductId()))
          .findFirst().orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION));
      productOption.setSize(updateForm.getSize());
      productOption.setCount(updateForm.getCount());
    }

    return product;

  }

  // 상품 삭제(삭제된 상품에 대한 옵션도 모두 삭제됨)
  @Transactional
  public void deleteProduct(Long sellerId, Long productId) {
    Product product = productRepository.findBySellerIdAndId(sellerId, productId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    productRepository.delete(product);
  }

  // 상품 옵션 추가
  @Transactional
  public Product addOption(Long sellerId, AddProductOptionForm form) {
    Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
        .orElseThrow(() -> new CustomException(ErrorCode.DO_NOT_MATCH_USER));
    if (product.getProductOptions().stream()
        .anyMatch(option -> option.getSize().equals(form.getSize()))) {
      throw new CustomException(ErrorCode.ALREADY_EXIST_SIZE);
    }
    ProductOption option = ProductOption.of(sellerId, form);
    product.getProductOptions().add(option);
    return product;
  }

  // 상품 옵션 수정
  @Transactional
  public ProductOption updateOption(Long sellerId, UpdateProductOptionForm form) {
    ProductOption productOption = productOptionRepository.findById(form.getId())
        .filter(option -> option.getSellerId().equals(sellerId))
        .orElseThrow(() -> new CustomException(ErrorCode.DO_NOT_MATCH_USER));

    productOption.setSize(form.getSize());
    productOption.setCount(form.getCount());
    return productOption;
  }

  // 상품 옵션 삭제
  @Transactional
  public void deleteOption(Long sellerId, Long optionId) {
    ProductOption option = productOptionRepository.findBySellerIdAndId(sellerId, optionId)
        .orElseThrow(() -> new CustomException(ErrorCode.DO_NOT_MATCH_USER_OPTION));
    productOptionRepository.delete(option);
  }

  // 내가 팔고있는 상품 확인(페이징처리)
  public ProductPageResponse getProduct(Long sellerId, int pageNo, int pageSize) {

    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<Product> productPage = productRepository.findProductBySellerId(sellerId, pageable);
    List<Product> products = productPage.getContent();

    return ProductPageResponse.builder()
        .productList(products.stream().map(ProductDto::exceptOption).collect(Collectors.toList()))
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(productPage.getTotalElements())
        .totalPages(productPage.getTotalPages())
        .last(productPage.isLast())
        .build();
  }

  // 내가 팔고있는 상품의 옵션 확인(페이징처리)
  public OptionPageResponse getOption(Long sellerId, int pageNo, int pageSize) {

    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<ProductOption> productPage = productOptionRepository.findProductOptionsBySellerId(sellerId,
        pageable);
    List<ProductOption> products = productPage.getContent();

    return OptionPageResponse.builder()
        .optionList(products.stream().map(ProductOptionDto::from).collect(Collectors.toList()))
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(productPage.getTotalElements())
        .totalPages(productPage.getTotalPages())
        .last(productPage.isLast())
        .build();
  }

}
