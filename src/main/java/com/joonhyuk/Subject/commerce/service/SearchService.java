package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductCustomRepository;
import com.joonhyuk.Subject.commerce.domain.search.SearchDto;
import com.joonhyuk.Subject.commerce.domain.search.SearchPageResponse;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

  private final ProductCustomRepository productCustomRepository;

  // 카테고리별 등록된 최신순 검색
  @Transactional(readOnly = true)
  public SearchPageResponse searchCategory(String category, int pageNo, int pageSize, String sort) {
    // 날짜순 최신순
    Pageable pageable = PageRequest.of(pageNo, pageSize);
    if (sort.equals("DESC")) {
      Page<Product> productPage = productCustomRepository.findProductsByCategoryOrderByModifiedAtDesc(
          category, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPage);
    }
    // 날짜순 오래된순
    if (sort.equals("ASC")) {
      Page<Product> productPageAsc = productCustomRepository.findProductsByCategoryOrderByModifiedAtAsc(
          category, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPageAsc);
    }
    return null;
  }

  //카테고리별 가격순(낮은가격순, 높은 가격순)
  @Transactional(readOnly = true)
  public SearchPageResponse searchPrice(String category, int pageNo, int pageSize, String sort) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);
    if (sort.equals("DESC")) {
      Page<Product> productPage = productCustomRepository.findProductsByCategoryOrderByPriceDesc(
          category, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPage);
    }
    // 날짜순 오래된순
    if (sort.equals("ASC")) {
      Page<Product> productPageAsc = productCustomRepository.findProductsByCategoryOrderByPriceAsc(
          category, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPageAsc);
    }
    return null;
  }

  // 별점순 (별점 높은순 , 낮은순)
  @Transactional(readOnly = true)
  public SearchPageResponse searchStars(String category, int pageNo, int pageSize, String sort) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);
    if (sort.equals("DESC")) {
      Page<Product> productPage = productCustomRepository.findProductsByCategoryOrderByStarsDesc(
          category, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPage);
    }
    // 날짜순 오래된순
    if (sort.equals("ASC")) {
      Page<Product> productPageAsc = productCustomRepository.findProductsByCategoryOrderByStarsAsc(
          category, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPageAsc);
    }
    return null;
  }

  // 상품이름명으로 검색(최신순으로)
  @Transactional(readOnly = true)
  public SearchPageResponse searchWord(String word, int pageNo, int pageSize, String sort) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);

    if (sort.equals("DESC")) {
      Page<Product> productPage = productCustomRepository.findProductsLikeWordOrderByModifiedAtDesc(
          word, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPage);
    }
    // 날짜순 오래된순
    if (sort.equals("ASC")) {
      Page<Product> productPageAsc = productCustomRepository.findProductsLikeWordOrderByModifiedAtAsc(
          word, pageable);
      return getSearchPageResponse(pageNo, pageSize, productPageAsc);
    }
    return null;
  }


  // 중복 메서드 SearchPageResponse return
  private SearchPageResponse getSearchPageResponse(int page, int pageSize,
      Page<Product> productPage) {
    if (productPage.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
    }
    List<Product> products = productPage.getContent();
    return SearchPageResponse.builder()
        .searchDtoList(products.stream().map(SearchDto::from).collect(
            Collectors.toList()))
        .pageNo(page + 1)
        .pageSize(pageSize)
        .totalElements(productPage.getTotalElements())
        .totalPages(productPage.getTotalPages())
        .last(productPage.isLast())
        .build();
  }
}
