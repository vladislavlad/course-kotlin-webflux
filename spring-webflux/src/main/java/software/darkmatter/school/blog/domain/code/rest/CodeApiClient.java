package software.darkmatter.school.blog.domain.code.rest;

import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCreateRequest;

public interface CodeApiClient {

    Mono<ApiResult> createCode(CodeCreateRequest codeCreateRequest);

    Mono<ApiResult> checkCode(CodeCheckRequest codeCheckRequest);
}
