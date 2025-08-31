package com.supsp.springboot.core.generator.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomFileEnum {
    /**
     * model
     */
    Model("Model", "templates/model/Model.java.vm"),
    /**
     * PageRequest
     */
    PageRequest("Request", "templates/model/Params/Request.java.vm"),
    /**
     * PageParams
     */
    PageParams("Params", "templates/model/Params/Params.java.vm"),
    /**
     * PageFilter
     */
    PageFilter("Filter", "templates/model/Params/Filter.java.vm"),
    /**
     * Vo
     */
    EntityVo("Vo", "templates/model/vo/EntityVo.java.vm"),
    /**
     * Vo
     */
    EntityUtils("Utils", "templates/model/utils/Utils.java.vm"),

    /**
     * Action
     */
    Action("Action", "templates/model/action/ActionService.java.vm"),

    /**
     * ActionImpl
     */
    ActionImpl("ActionImpl", "templates/model/action/ActionServiceImpl.java.vm"),

    /**
     * admin
     */
    Admin("Admin", "templates/controller/AdminController.java.vm"),
    /**
     * TenantController
     */
    Tenant("Tenant", "templates/controller/TenantController.java.vm"),
    /**
     * ApiController
     */
    Merchant("Merchant", "templates/controller/MerchantController.java.vm"),
    /**
     * ConsumerController
     */
    Consumer("Consumer", "templates/controller/ConsumerController.java.vm"),
    /**
     * ApiController
     */
    Api("Api", "templates/controller/ApiController.java.vm"),
    ;

    private final String fileName;

    private final String templatePath;
}
