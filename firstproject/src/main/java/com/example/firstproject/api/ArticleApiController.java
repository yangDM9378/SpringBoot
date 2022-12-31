package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleService articleService;

    // GET
    // 1. 전체 목록
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }
//    // 2. 단일 목록
//    @GetMapping("/api/articles/{id}")
//    public Article index(@PathVariable Long id) {
//        return articleRepository.findById(id).orElse(null);
//    }
//    // POST
//    @PostMapping("/api/articles")
//    public Article create(@RequestBody ArticleForm dto) {
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//    // PATCH
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id,
//                          @RequestBody ArticleForm dto) {
//        //1. 수정용 엔티티 생성 - dto->entity
//        Article article = dto.toEntity();
//        log.info("id:{}, article: {}", id, article.toString());
//
//        //2. 대상 엔티티를 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        //3. 잘못된 요청 처리(대상 없거나, id가 다른)
//        if (target == null || id != article.getId()) {
//            // 400 잘못된
//            log.info("잘못된요청 id:{}, article: {}",id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        //4. 업데이트 정상응답 -> 키 값을 몇개만 보낼때
//        target.patch(article);
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }
//    //DELETE
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id){
//        // 대상 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//        // 잘못된 요청 처리
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 대상 삭제
//        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

}
