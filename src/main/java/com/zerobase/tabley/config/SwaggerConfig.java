package com.zerobase.tabley.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * swaager 설정 메서드
     * apis()에 basePackage 옵션으로 error api ui에 나오지 않도록 설정
     * paths()에는 any()를 적용하여 모든 api 나오도록 설정 -> ant()를 사용하여 원하는 api만 나오도록 할 수 있음
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zerobase.tabley"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    /**
     * swagger의 index.hmtl 페이지에서 상단에 간단한 설명 작성
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("매장 예약 서비스 프로젝트 - tabely")
                .description("매장 점주는 매장을 CRUD 하고, 예약을 승인하는, 그리고 사용자는 앱을 통해 매장을 찾고 예약할 수 있는 백엔드 API 입니다.")
                .version("1.0")
                .build();
    }
}
