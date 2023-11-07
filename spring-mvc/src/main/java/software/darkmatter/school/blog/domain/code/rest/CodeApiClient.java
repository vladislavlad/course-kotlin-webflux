package software.darkmatter.school.blog.domain.code.rest;

import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCreateRequest;

public interface CodeApiClient {

    ApiResult createCode(CodeCreateRequest codeCreateRequest);

    ApiResult checkCode(CodeCheckRequest codeCheckRequest);
}
