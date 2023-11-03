package software.darkmatter.school.blog.domain.code.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCreateRequest;

@Component
@RequiredArgsConstructor
public class CodeApiClientImpl implements CodeApiClient {

    private final WebClient webClient;

    @Override
    public Mono<ApiResult> createCode(CodeCreateRequest codeCreateRequest) {
        return webClient.post()
                        .uri("/api/code")
                        .body(Mono.just(codeCreateRequest), CodeCreateRequest.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .exchangeToMono(
                            response -> {
                                HttpStatus status = (HttpStatus) response.statusCode();
                                return switch (status) {
                                    case OK, CREATED -> buildApiResult(status);
                                    case UNPROCESSABLE_ENTITY -> buildApiResult(status, "Code not created");
                                    default -> buildApiResult(status, "Unknown error");
                                };
                            }
                        );
    }

    @Override
    public Mono<ApiResult> checkCode(CodeCheckRequest codeCheckRequest) {
        return webClient.post()
                        .uri("/api/code/check")
                        .body(Mono.just(codeCheckRequest), CodeCheckRequest.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .exchangeToMono(
                            response -> {
                                HttpStatus status = (HttpStatus) response.statusCode();
                                return switch (status) {
                                    case OK, CREATED -> buildApiResult(status);
                                    case UNPROCESSABLE_ENTITY -> buildApiResult(status, "Code not valid");
                                    default -> buildApiResult(status, "Unknown error");
                                };
                            }
                        );
    }

    private static Mono<ApiResult> buildApiResult(HttpStatus status) {
        return buildApiResult(status, null);
    }

    private static Mono<ApiResult> buildApiResult(HttpStatus status, String error) {
        return Mono.just(
            new ApiResult(status.value(), error)
        );
    }
}
