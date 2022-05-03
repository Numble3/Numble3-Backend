package com.numble.team3.image.annotation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation(value = "이미지 리사이즈")
@ApiResponses(
  value = {
    @ApiResponse(
      code = 201,
      message = "이미지 리사이즈 성공",
      examples = @Example(@ExampleProperty(mediaType = "application/json", value = "{\"url\" : \"https://image_url\"}"))
    ),
    @ApiResponse(
      code = 400,
      message = "이미지 리사이즈 실패 \t\n 1. 지원하지 않는 이미지 타입 \t\n 2. 지원하지 않는 리사이즈 방식",
      examples = @Example(@ExampleProperty(mediaType = "application/json", value= "{\n\"message\" : \"지원하지 않는 이미지 파일입니다.\"\n\"message\" : \"지원하지 않는 리사이즈 방식입니다.\"\n}"))
    ),
    @ApiResponse(
      code = 401,
      message = "회원 인증 실패 \t\n 1. access token이 유효하지 않음",
      examples = @Example(@ExampleProperty(mediaType= "application/json", value = "{}"))
    ),
    @ApiResponse(
      code = 500,
      message = "이미지 리사이즈 실패 \t\n 1. aws 문제",
      examples = @Example(@ExampleProperty(mediaType = "application/json", value ="{\"message\" : \"이미지 변환에 실패했습니다.\"}"))
    )
  }
)
@ResponseStatus(HttpStatus.CREATED)
public @interface ImageResizeSwagger {

}
