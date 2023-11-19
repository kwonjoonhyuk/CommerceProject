package com.joonhyuk.Subject.commerce.controller;

import com.joonhyuk.Subject.commerce.domain.search.SearchPageResponse;
import com.joonhyuk.Subject.commerce.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

  private final SearchService searchService;

  // 카테고리별 등록된 날짜별 등록 오름차순 내림차순 검색
  @GetMapping("/category")
  public ResponseEntity<SearchPageResponse> searchCategory(
      @RequestParam(value = "category", required = true) String category,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sort", defaultValue = "DESC", required = false) String sort) {
    return ResponseEntity.ok(searchService.searchCategory(category, pageNo, pageSize, sort));
  }

  // 카테고리별 가격순(낮은가격순, 높은 가격순)
  @GetMapping("/price")
  public ResponseEntity<SearchPageResponse> searchPrice(
      @RequestParam(value = "category", required = true) String category,
      @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sort", defaultValue = "DESC", required = false) String sort) {
    return ResponseEntity.ok(searchService.searchPrice(category, pageNo, pageSize, sort));
  }

  // 별점순 (별점 높은순 , 낮은순)
  @GetMapping("/stars")
  public ResponseEntity<SearchPageResponse> searchStars(
      @RequestParam(value = "category", required = true) String category,
      @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sort", defaultValue = "DESC", required = false) String sort) {
    return ResponseEntity.ok(searchService.searchStars(category, pageNo, pageSize, sort));
  }

  // 상품이름명으로 검색(최신순으로)
  @GetMapping("/word")
  public ResponseEntity<SearchPageResponse> searchWord(
      @RequestParam(value = "word", required = true) String word,
      @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sort", defaultValue = "DESC", required = false) String sort) {
    return ResponseEntity.ok(searchService.searchWord(word, pageNo, pageSize, sort));
  }
}
