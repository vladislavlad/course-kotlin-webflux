package software.darkmatter.school.blog.domain.code.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCreateRequest;

@Component
@RequiredArgsConstructor
public class CodeApiClientImpl implements CodeApiClient {

    private final RestClient restClient;

    @Override
    public ApiResult createCode(CodeCreateRequest codeCreateRequest) {
        return restClient.post()
                         .uri("/api/code")
                         .body(codeCreateRequest)
                         .exchange(
                             (request, response) -> {
                                 var status = (HttpStatus) response.getStatusCode();
                                 return switch (status) {
                                     case OK, CREATED -> buildApiResult(status);
                                     case UNPROCESSABLE_ENTITY -> buildApiResult(status, "Code not created");
                                     default -> buildApiResult(status, "Unknown error");
                                 };
                             }
                         );
    }

    @Override
    public ApiResult checkCode(CodeCheckRequest codeCheckRequest) {
        return restClient.post()
                         .uri("/api/code/check")
                         .body(codeCheckRequest)
                         .exchange(
                             (request, response) -> {
                                 var status = (HttpStatus) response.getStatusCode();
                                 return switch (status) {
                                     case OK, CREATED -> buildApiResult(status);
                                     case UNPROCESSABLE_ENTITY -> buildApiResult(status, "Code not valid");
                                     default -> buildApiResult(status, "Unknown error");
                                 };
                             }
                         );
    }

    private static ApiResult buildApiResult(HttpStatus status) {
        return buildApiResult(status, null);
    }

    private static ApiResult buildApiResult(HttpStatus status, String error) {
        return new ApiResult(status.value(), error);
    }
}
