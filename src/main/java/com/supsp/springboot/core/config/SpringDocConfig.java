
package com.supsp.springboot.core.config;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.supsp.springboot.core.annotations.DBEntity;
import com.supsp.springboot.core.annotations.DataId;
import com.supsp.springboot.core.annotations.EntityColumn;
import com.supsp.springboot.core.annotations.SensitiveField;
import com.supsp.springboot.core.utils.EnumUtils;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

import java.util.Arrays;

@Configuration
@EnableKnife4j
@Slf4j
public class SpringDocConfig {

        @Resource
        private CoreProperties coreProperties;

        @Value("${supsp.doc.url:null}")
        private String url;

        @Value("${supsp.doc.license:null}")
        private String license;

        @Value("${supsp.doc.licenseUrl:null}")
        private String licenseUrl;

        @Value("${supsp.app.base-pkg:com.supsp}")
        private String basePkg;

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title(CoreProperties.APP_NAME)
                                                .description(CoreProperties.APP_NAME)
                                                .version(CoreProperties.SYSTEM_VERSION)
                                                .license(
                                                                new License()
                                                                                .name(license)
                                                                                .url(licenseUrl)))
                                .components(new Components()
                                                .addParameters(
                                                                CoreProperties.appHeaderNoCacheName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderNoCacheName())
                                                                                .description("是否忽略缓存 YES/NO")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderAppName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties.appHeaderAppName())
                                                                                .description("应用名称")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderAppCodeName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderAppCodeName())
                                                                                .description("应用类型编码 对应开发字典 版本类型 sysVersionType")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderChannelName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderChannelName())
                                                                                .description("渠道")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderPlatformName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderPlatformName())
                                                                                .description("平台 android ios web")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderDeviceName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderDeviceName())
                                                                                .description("设备")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderDeviceSNName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderDeviceSNName())
                                                                                .description("设备序号")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderVersionName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderVersionName())
                                                                                .description("应用版本")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderForceName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderForceName())
                                                                                .description("强制更新")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderOperatorIdName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderOperatorIdName())
                                                                                .description("Operator[操作人] ID")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.appHeaderCashierIdName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .appHeaderCashierIdName())
                                                                                .description("Cashier[收银员] ID")
                                                                                .required(false))

                                                // admin
                                                .addParameters(
                                                                CoreProperties.tokenAdminUserHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenAdminUserHeaderName())
                                                                                .description("Admin User")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenAdminOrgHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenAdminOrgHeaderName())
                                                                                .description("Admin Org")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenAdminDepHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenAdminDepHeaderName())
                                                                                .description("Admin Dep")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenAdminShopHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenAdminShopHeaderName())
                                                                                .description("Admin Shop")
                                                                                .required(false))

                                                // tenant
                                                .addParameters(
                                                                CoreProperties.tokenTenantUserHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenTenantUserHeaderName())
                                                                                .description("Tenant User")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenTenantOrgHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenTenantOrgHeaderName())
                                                                                .description("Tenant Org")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenTenantDepHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenTenantDepHeaderName())
                                                                                .description("Tenant Store")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenTenantShopHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenTenantShopHeaderName())
                                                                                .description("Tenant Shop")
                                                                                .required(false))

                                                // merchant
                                                .addParameters(
                                                                CoreProperties.tokenMerchantUserHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenMerchantUserHeaderName())
                                                                                .description("merchant User")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenMerchantOrgHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenMerchantOrgHeaderName())
                                                                                .description("merchant Org")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenMerchantDepHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenMerchantDepHeaderName())
                                                                                .description("merchant dep")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenMerchantShopHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenMerchantShopHeaderName())
                                                                                .description("merchant Shop")
                                                                                .required(false))

                                                // consumer
                                                .addParameters(
                                                                CoreProperties.tokenConsumerUserHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenConsumerUserHeaderName())
                                                                                .description("Consumer User")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenConsumerOrgHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenConsumerOrgHeaderName())
                                                                                .description("Consumer Org")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenConsumerDepHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenConsumerDepHeaderName())
                                                                                .description("Consumer dep")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenConsumerShopHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenConsumerShopHeaderName())
                                                                                .description("Consumer Shop")
                                                                                .required(false))

                                                // api
                                                .addParameters(
                                                                CoreProperties.tokenApiUserHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenApiUserHeaderName())
                                                                                .description("Api User")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenApiOrgHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenApiOrgHeaderName())
                                                                                .description("Api Org")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenApiDepHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenApiDepHeaderName())
                                                                                .description("Api Dep")
                                                                                .required(false))

                                                .addParameters(
                                                                CoreProperties.tokenApiShopHeaderName(),
                                                                new Parameter()
                                                                                .in(ParameterIn.HEADER.toString())
                                                                                .schema(new StringSchema())
                                                                                .name(CoreProperties
                                                                                                .tokenApiShopHeaderName())
                                                                                .description("Api Shop")
                                                                                .required(false))

                                                // Security
                                                .addSecuritySchemes(CoreProperties.tokenUserTypeHeaderName(),
                                                                new SecurityScheme()
                                                                                .name(CoreProperties
                                                                                                .tokenUserTypeHeaderName())
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme("Bearer")
                                                                                .description("user type")
                                                                                .bearerFormat("JWT"))
                                                .addSecuritySchemes(CoreProperties.tokenAdminHeaderName(),
                                                                new SecurityScheme()
                                                                                .name(CoreProperties
                                                                                                .tokenAdminHeaderName())
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme("Bearer")
                                                                                .description("Admin token")
                                                                                .bearerFormat("JWT"))
                                                .addSecuritySchemes(CoreProperties.tokenTenantHeaderName(),
                                                                new SecurityScheme()
                                                                                .name(CoreProperties
                                                                                                .tokenTenantHeaderName())
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme("Bearer")
                                                                                .description("Tenant token")
                                                                                .bearerFormat("JWT"))
                                                .addSecuritySchemes(CoreProperties.tokenMerchantHeaderName(),
                                                                new SecurityScheme()
                                                                                .name(CoreProperties
                                                                                                .tokenMerchantHeaderName())
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme("Bearer")
                                                                                .description("merchant token")
                                                                                .bearerFormat("JWT"))
                                                .addSecuritySchemes(CoreProperties.tokenConsumerHeaderName(),
                                                                new SecurityScheme()
                                                                                .name(CoreProperties
                                                                                                .tokenConsumerHeaderName())
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme("Bearer")
                                                                                .description("Consumer token")
                                                                                .bearerFormat("JWT"))
                                                .addSecuritySchemes(CoreProperties.tokenApiHeaderName(),
                                                                new SecurityScheme()
                                                                                .name(CoreProperties
                                                                                                .tokenApiHeaderName())
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme("Bearer")
                                                                                .description("Api token")
                                                                                .bearerFormat("JWT"))
                                // .addSecuritySchemes(CoreProperties.appHeaderOperatorIdName(),
                                // new SecurityScheme()
                                // .name(CoreProperties.appHeaderOperatorIdName())
                                // .type(SecurityScheme.Type.APIKEY)
                                // .in(SecurityScheme.In.HEADER)
                                // .scheme("Bearer")
                                // .description("Operator[操作人] ID")
                                // .bearerFormat("JWT")
                                // )
                                // .addSecuritySchemes(CoreProperties.appHeaderCashierIdName(),
                                // new SecurityScheme()
                                // .name(CoreProperties.appHeaderCashierIdName())
                                // .type(SecurityScheme.Type.APIKEY)
                                // .in(SecurityScheme.In.HEADER)
                                // .scheme("Bearer")
                                // .description("Cashier[收银员] ID")
                                // .bearerFormat("JWT")
                                // )
                                )
                                .externalDocs(
                                                new ExternalDocumentation()
                                                                .description(CoreProperties.APP_NAME)
                                                                .url(url));
        }

        @Bean
        public GroupedOpenApi adminGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("AdminApi")
                                .displayName("Admin管理端")
                                .pathsToMatch(
                                                "/admin/**")
                                .packagesToScan(
                                                basePkg + ".controller.admin")
                                .build();
        }

        @Bean
        public GroupedOpenApi tenantGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("TenantApi")
                                .displayName("Tenant租户端")
                                .pathsToMatch(
                                                "/tenant/**")
                                .packagesToScan(
                                                basePkg + ".controller.tenant")
                                .build();
        }

        @Bean
        public GroupedOpenApi merchantGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("MerchantApi")
                                .displayName("Merchant商家端")
                                .pathsToMatch(
                                                "/merchant/**")
                                .packagesToScan(
                                                basePkg + ".controller.merchant")
                                .build();
        }

        @Bean
        public GroupedOpenApi consumerGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("ConsumerApi")
                                .displayName("Consumer消费者端")
                                .pathsToMatch(
                                                "/consumer/**")
                                .packagesToScan(
                                                basePkg + ".controller.consumer")
                                .build();
        }

        @Bean
        public GroupedOpenApi apiGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("Api")
                                .displayName("Api端")
                                .pathsToMatch(
                                                "/api/**")
                                .packagesToScan(
                                                basePkg + ".controller.api")
                                .build();
        }

        @Bean
        public GroupedOpenApi homeGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("Home")
                                .displayName("Home公开接口")
                                .pathsToMatch(
                                                "/home/**")
                                .packagesToScan(
                                                basePkg + ".controller.home")
                                .build();
        }

        @Bean
        public GroupedOpenApi openGroupedOpenApi() {
                return GroupedOpenApi.builder()
                                .group("Open")
                                .displayName("Open开放接口")
                                .pathsToMatch(
                                                "/open/**")
                                .packagesToScan(
                                                basePkg + ".controller.open")
                                .build();
        }

        @Bean
        public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
                return (OpenAPI openApi) -> {
                        // 全局添加鉴权参数
                        if (openApi.getPaths() != null) {
                                openApi.getPaths().forEach((s, pathItem) -> {
                                        // 接口添加鉴权参数
                                        pathItem.readOperations()
                                                        .forEach(operation -> {
                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderNoCacheName())
                                                                                                .description("是否忽略缓存 YES/NO")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderAppName())
                                                                                                .description("应用名称")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderAppCodeName())
                                                                                                .description("应用类型编码 对应开发字典 版本类型 sysVersionType")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderChannelName())
                                                                                                .description("渠道")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderPlatformName())
                                                                                                .description("平台 android ios web")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderDeviceName())
                                                                                                .description("设备")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderDeviceSNName())
                                                                                                .description("设备序号")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderVersionName())
                                                                                                .description("应用版本")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderForceName())
                                                                                                .description("强制更新")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderOperatorIdName())
                                                                                                .description("Operator[操作人] ID")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .appHeaderCashierIdName())
                                                                                                .description("Cashier[收银员] ID")
                                                                                                .required(false));

                                                                // admin
                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenAdminUserHeaderName())
                                                                                                .description("Admin User")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenAdminOrgHeaderName())
                                                                                                .description("Admin Org")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenAdminDepHeaderName())
                                                                                                .description("Admin Store")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenAdminShopHeaderName())
                                                                                                .description("Admin Shop")
                                                                                                .required(false));

                                                                // tenant
                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenTenantUserHeaderName())
                                                                                                .description("Tenant User")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenTenantOrgHeaderName())
                                                                                                .description("Tenant Org")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenTenantDepHeaderName())
                                                                                                .description("Tenant Store")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenTenantShopHeaderName())
                                                                                                .description("Tenant Shop")
                                                                                                .required(false));

                                                                // merchant
                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenMerchantUserHeaderName())
                                                                                                .description("merchant User")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenMerchantOrgHeaderName())
                                                                                                .description("merchant Org")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenMerchantDepHeaderName())
                                                                                                .description("merchant Dep")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenMerchantShopHeaderName())
                                                                                                .description("merchant Shop")
                                                                                                .required(false));

                                                                // consumer
                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenConsumerUserHeaderName())
                                                                                                .description("Consumer User")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenConsumerOrgHeaderName())
                                                                                                .description("Consumer Org")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenConsumerDepHeaderName())
                                                                                                .description("Consumer Dep")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenConsumerShopHeaderName())
                                                                                                .description("Consumer Shop")
                                                                                                .required(false));

                                                                // api
                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenApiUserHeaderName())
                                                                                                .description("Api User")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenApiOrgHeaderName())
                                                                                                .description("Api Org")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenApiDepHeaderName())
                                                                                                .description("Api Dep")
                                                                                                .required(false));

                                                                operation.addParametersItem(
                                                                                new Parameter()
                                                                                                .in(ParameterIn.HEADER
                                                                                                                .toString())
                                                                                                .schema(new StringSchema())
                                                                                                .name(CoreProperties
                                                                                                                .tokenApiShopHeaderName())
                                                                                                .description("Api Shop")
                                                                                                .required(false));

                                                                operation.addSecurityItem(new SecurityRequirement()
                                                                                .addList(
                                                                                                CoreProperties.tokenUserTypeHeaderName())
                                                                                .addList(
                                                                                                CoreProperties.tokenAdminHeaderName())
                                                                                .addList(
                                                                                                CoreProperties.tokenTenantHeaderName())
                                                                                .addList(
                                                                                                CoreProperties.tokenMerchantHeaderName())
                                                                                .addList(
                                                                                                CoreProperties.tokenConsumerHeaderName())
                                                                                .addList(
                                                                                                CoreProperties.tokenApiHeaderName()));
                                                        });
                                });
                        }

                        // //
                        // for (Schema schema : openApi.getComponents().getSchemas().values()) {
                        // if (schema.getProperties() == null) {
                        // continue;
                        // }
                        //
                        // ((Map<String, Schema>) schema.getProperties()).forEach((String name, Schema
                        // value) -> {
                        // if (schema.getNullable() == null || !schema.getRequired().contains(name)) {
                        // value.setNullable(true);
                        // }
                        // });
                        // }
                };
        }

        @Bean
        public PropertyCustomizer propertyCustomizer() {
                return (Schema property, AnnotatedType type) -> {
                        try {
                                Class<?> clz = ((SimpleType) type.getType()).getRawClass();
                                Arrays.stream(type.getCtxAnnotations()).forEach((v) -> {
                                        switch (v) {
                                                case TableId tableId -> {
                                                        property.addExtension(
                                                                        "x-table-id",
                                                                        tableId.type().name());
                                                }
                                                case JsonIgnore jsonIgnore -> {
                                                        property.addExtension(
                                                                        "x-ignore",
                                                                        true);
                                                }
                                                case NotBlank notBlank -> {
                                                        property.addExtension(
                                                                        "x-notBlank-message",
                                                                        notBlank.message());
                                                        property.addExtension(
                                                                        "x-notBlank-groups",
                                                                        Arrays.stream(notBlank.groups())
                                                                                        .map(Class::getSimpleName)
                                                                                        .toArray());
                                                }
                                                case Min min -> {
                                                        property.addExtension(
                                                                        "x-min-message",
                                                                        min.message());
                                                }
                                                case Max max -> {
                                                        property.addExtension(
                                                                        "x-max-message",
                                                                        max.message());
                                                }
                                                case DBEntity dbEntity -> {
                                                        property.addExtension(
                                                                        "x-db-entity",
                                                                        true);
                                                }
                                                case DataId dataId -> {
                                                        property.addExtension(
                                                                        "x-data-id",
                                                                        true);
                                                }
                                                case SensitiveField sensitiveField -> {
                                                        property.addExtension(
                                                                        "x-is-sensitive",
                                                                        true);
                                                        property.addExtension(
                                                                        "x-sensitive-type",
                                                                        sensitiveField.type());
                                                        property.addExtension(
                                                                        "x-sensitive-db",
                                                                        sensitiveField.db());
                                                }
                                                case io.swagger.v3.oas.annotations.media.Schema fieldSchema -> {
                                                        property.addExtension(
                                                                        "x-schema-name",
                                                                        fieldSchema.name());
                                                        property.addExtension(
                                                                        "x-schema-title",
                                                                        fieldSchema.title());
                                                        property.addExtension(
                                                                        "x-schema-required-mode",
                                                                        fieldSchema.requiredMode());
                                                        property.addExtension(
                                                                        "x-schema-description",
                                                                        fieldSchema.description());
                                                        property.addExtension(
                                                                        "x-schema-nullable",
                                                                        fieldSchema.nullable());
                                                        property.addExtension(
                                                                        "x-schema-access-mode",
                                                                        fieldSchema.accessMode());
                                                }
                                                case EntityColumn entityColumn -> {
                                                        property.addExtension(
                                                                        "x-column-data-id",
                                                                        entityColumn.isDataId());
                                                        property.addExtension(
                                                                        "x-column-is-title",
                                                                        entityColumn.isTitle());
                                                        property.addExtension(
                                                                        "x-column-is-code",
                                                                        entityColumn.isCode());
                                                        property.addExtension(
                                                                        "x-column-is-keywords",
                                                                        entityColumn.isKeywords()
                                                                                        || entityColumn.isTitle()
                                                                                        || entityColumn.isCode()
                                                                                        || entityColumn.isDataId());
                                                        property.addExtension(
                                                                        "x-column-is-type",
                                                                        entityColumn.isType());
                                                        property.addExtension(
                                                                        "x-column-is-image",
                                                                        entityColumn.isImage());
                                                        property.addExtension(
                                                                        "x-column-is-file",
                                                                        entityColumn.isFile());
                                                        property.addExtension(
                                                                        "x-column-multiple-file",
                                                                        entityColumn.multipleFile());
                                                        property.addExtension(
                                                                        "x-column-is-dict",
                                                                        entityColumn.isDict());
                                                        property.addExtension(
                                                                        "x-column-dict-scene",
                                                                        entityColumn.scene());
                                                        property.addExtension(
                                                                        "x-column-dict-code",
                                                                        entityColumn.dictCode());
                                                        property.addExtension(
                                                                        "x-column-admin-select",
                                                                        entityColumn.select());
                                                        property.addExtension(
                                                                        "x-column-admin-defaultSelect",
                                                                        entityColumn.defaultSelect());
                                                        property.addExtension(
                                                                        "x-column-api-select",
                                                                        entityColumn.apiSelect());
                                                        property.addExtension(
                                                                        "x-column-api-defaultSelect",
                                                                        entityColumn.defaultApiSelect());
                                                }
                                                case TableField tableField -> {
                                                        property.addExtension(
                                                                        "x-field-insertStrategy",
                                                                        tableField.insertStrategy().name());
                                                        property.addExtension(
                                                                        "x-field-updateStrategy",
                                                                        tableField.updateStrategy().name());
                                                        property.addExtension(
                                                                        "x-field-update",
                                                                        tableField.update());
                                                        property.addExtension(
                                                                        "x-field-fill",
                                                                        tableField.fill().name());
                                                }
                                                default -> {
                                                        //
                                                }
                                        }

                                });

                                property.addExtension(
                                                "x-type-package",
                                                clz.getPackage().getName());
                                property.addExtension(
                                                "x-type-name",
                                                clz.getSimpleName());
                                if (clz.isEnum()) {
                                        property.addExtension(
                                                        "x-enum-values",
                                                        EnumUtils.docEnumExtValues(
                                                                        clz));
                                }

                        } catch (Exception e) {
                                // log.error
                        }
                        return property;
                }

                ;
        }

        @Bean
        public ParameterCustomizer parameterCustomizer() {
                return (Parameter parameterModel, MethodParameter methodParameter) -> {
                        try {
                                if (ObjectUtils.isNotEmpty(parameterModel.getSchema())) {
                                        parameterModel.getSchema().addExtension(
                                                        "x-type-package",
                                                        methodParameter.getParameterType().getPackage().getName());
                                        parameterModel.getSchema().addExtension(
                                                        "x-type-name",
                                                        methodParameter.getParameterType().getSimpleName());
                                }
                                if (methodParameter.getParameterType().isEnum()) {
                                        if (ObjectUtils.isNotEmpty(parameterModel.getSchema())) {
                                                parameterModel.getSchema().addExtension(
                                                                "x-enum-values",
                                                                EnumUtils.docEnumExtValues(
                                                                                methodParameter.getParameterType()));
                                        }
                                }
                        } catch (Exception e) {
                                // log.error(
                        }
                        return parameterModel;
                };
        }

}
