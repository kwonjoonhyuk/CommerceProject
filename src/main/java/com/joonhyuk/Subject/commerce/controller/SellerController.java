package com.joonhyuk.Subject.commerce.controller;

import com.joonhyuk.Subject.commerce.domain.product.OptionPageResponse;
import com.joonhyuk.Subject.commerce.domain.product.ProductOptionDto;
import com.joonhyuk.Subject.commerce.domain.product.ProductPageResponse;
import com.joonhyuk.Subject.commerce.domain.product.form.AddProductForm;
import com.joonhyuk.Subject.commerce.domain.product.ProductDto;
import com.joonhyuk.Subject.commerce.domain.product.form.AddProductOptionForm;
import com.joonhyuk.Subject.commerce.domain.product.form.UpdateProductForm;
import com.joonhyuk.Subject.commerce.domain.product.form.UpdateProductOptionForm;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.service.SellerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

  private final UserCustomRepository customRepository;
  private final SellerService sellerService;

  // 상품 추가
  @PostMapping("/addProduct")
  public ResponseEntity<ProductDto> addProduct(Authentication auth,
      @RequestBody AddProductForm form) {
    Long sellerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(ProductDto.from(sellerService.addProduct(sellerId, form)));
  }

  // 상품 수정
  @PutMapping("/updateProduct")
  public ResponseEntity<ProductDto> updateProduct(Authentication auth,
      @RequestBody @Valid UpdateProductForm form) {
    Long sellerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(ProductDto.from(sellerService.updateProduct(sellerId, form)));
  }

  // 상품 삭제(삭제된 상품에 대한 옵션도 모두 삭제됨)
  @DeleteMapping("/deleteProduct")
  public ResponseEntity<String> deleteProduct(Authentication auth,
      @RequestParam Long productId) {
    Long sellerId = customRepository.findIdByEmail(auth.getName());
    sellerService.deleteProduct(sellerId, productId);
    return ResponseEntity.ok("삭제되었습니다.");
  }

  // 상품 옵션 추가
  @PostMapping("/addOption")
  public ResponseEntity<ProductDto> addOption(Authentication auth,
      @RequestBody @Valid AddProductOptionForm form) {
    Long sellerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(ProductDto.from(sellerService.addOption(sellerId, form)));
  }

  // 상품 옵션 수정
  @PutMapping("/updateOption")
  public ResponseEntity<ProductOptionDto> updateOption(Authentication auth,
      @RequestBody @Valid UpdateProductOptionForm form) {
    Long sellerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(ProductOptionDto.from(sellerService.updateOption(sellerId, form)));
  }

  // 상품 옵션 삭제
  @DeleteMapping("/deleteOption")
  public ResponseEntity<String> deleteOption(Authentication auth,
      @RequestParam Long optionId) {
    Long sellerId = customRepository.findIdByEmail(auth.getName());
    sellerService.deleteOption(sellerId, optionId);
    return ResponseEntity.ok("삭제되었습니다.");
  }

  // 내가 팔고있는 상품 확인(페이징처리)
  @GetMapping("/getProduct")
  public ResponseEntity<ProductPageResponse> getProduct(Authentication auth,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize) {

    Long sellerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(sellerService.getProduct(sellerId, pageNo, pageSize));
  }

  // 내가 팔고있는 상품의 옵션 확인(페이징처리)

  @GetMapping("/getOption")
  public ResponseEntity<OptionPageResponse> getOption(Authentication auth,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize) {

    Long sellerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(sellerService.getOption(sellerId, pageNo, pageSize));
  }
}
