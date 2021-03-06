package com.spring.book.springboot.web;

import com.spring.book.springboot.config.auth.LoginUser;
import com.spring.book.springboot.config.auth.dto.SessionUser;
import com.spring.book.springboot.service.posts.PostsService;
import com.spring.book.springboot.web.dto.PostsListResponseDto;
import com.spring.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

        //@LoginUser라는 어노테이션을 추가함으로서 주석처리
        /*SessionUser user = (SessionUser) httpSession.getAttribute("user");*/

        if(user != null){
            model.addAttribute("userName",user.getName());
        }
        return "index";
        // 머스테치 스타터 덕분에 src/main/resources/templates + index + .mustache로 전환 -> View Resolver가 처리
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}
