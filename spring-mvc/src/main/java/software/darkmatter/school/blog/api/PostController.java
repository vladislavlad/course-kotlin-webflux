package software.darkmatter.school.blog.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.api.dto.PostDto;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @PostMapping
    public void create(@RequestBody PostCreateDto body) {

    }

    @GetMapping
    public List<PostDto> list(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size
    ) {
        return Collections.emptyList();
    }
}
