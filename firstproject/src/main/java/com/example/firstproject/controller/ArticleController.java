package com.example.firstproject.controller;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ArticleController {
    @Autowired // 스프링 부트가 미리 생성해놓은 리파지터리 객체 가져오기
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
//        System.out.println(form.toString());
        log.info(form.toString());

        // 1. Dto를 Entity 변환
        Article article = form.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB로 저장하게 함
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id="+id);

        // 1: id로 데이터를 가져옴
//        Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
        // 3: 보여줄 페이지 설정!
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model) {
        // 1: 모든 Article을 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();
        // 2: 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);
        // 3: 뷰 페이지를 설정!
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        
        // DTO를 엔티티도 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 엔티티를 DB로 저장
        // 1. DB기존 데이터를 가져오기
//        Optional<Article> target = articleRepository.findById(articleEntity.getId());
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2.기존 데이터에 값 갱신
        if (target != null) {
            articleRepository.save(articleEntity); //엔티티 DB로 갱신
        }
        // 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();
    }
    
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제요청들어옴");

        // 1. 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        //2. 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            // 휘발성 데이터 표시하기
            rttr.addFlashAttribute("msg", "삭제 완료");
        }

        //3. 결과 페이지 리다이랙트
        return "redirect:/articles/";
    }
}
