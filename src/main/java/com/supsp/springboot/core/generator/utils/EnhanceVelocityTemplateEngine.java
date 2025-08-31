package com.supsp.springboot.core.generator.utils;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.supsp.springboot.core.utils.JsonUtil;
import com.supsp.springboot.core.utils.StrUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EnhanceVelocityTemplateEngine extends VelocityTemplateEngine {

    @Override
    protected void outputCustomFile(
            @NotNull
            List<CustomFile> customFiles,
            @NotNull
            TableInfo tableInfo,
            @NotNull
            Map<String, Object> objectMap
    ) {

        String projectPath = System.getProperty("user.dir");
        // log.info("projectPath: {}", projectPath);

        Map<String, String> customMap = (Map<String, String>) objectMap.get("custom");

        String model = customMap.get("model");
        String pkgParent = customMap.get("pkgParent");

        String baseSrcDir = "src/main/java/" + pkgParent.replace(".", File.separator);
        String javaDir = customMap.get("javaDir");
        String modelRealDir = customMap.get("modelRealDir");
        String modelVoDir = customMap.get("modelVoDir");
        String modelUtilsDir = customMap.get("modelUtilsDir");

        String modelActionServiceDir = customMap.get("modelActionServiceDir");
        String modelActionServiceImplDir = customMap.get("modelActionServiceImplDir");

        String adminCtrlDir = customMap.get("adminCtrlDir");
        String tenantCtrlDir = customMap.get("tenantCtrlDir");
        String merchantCtrlDir = customMap.get("merchantCtrlDir");
        String consumerCtrlDir = customMap.get("consumerCtrlDir");
        String apiCtrlDir = customMap.get("apiCtrlDir");

        String entityPath = "entity/entity-" + model;
        String adminModulePath = model + "/" + model + "-admin";
        String entityServicePath = "service/service-" + model;
        String adminServiceImplPath = model + "/" + model + "-admin";
        String apiModulePath = model + "/" + model + "-api";

        log.info(
                """
                        \n■■■■■
                        ============================================
                        projectPath: {}
                        model: {}
                        baseSrcDir: {}
                        javaDir: {}
                        modelRealDir: {}
                        modelVoDir: {}
                        modelUtilsDir: {}
                        modelActionServiceDir: {}
                        modelActionServiceImplDir: {}
                        entityPath: {}
                        adminModulePath: {}
                        entityServicePath: {}
                        adminServiceImplPath: {}
                        apiModulePath: {}
                        ============================================
                        adminCtrlDir: {}
                        tenantCtrlDir: {}
                        merchantCtrlDir: {}
                        consumerCtrlDir: {}
                        apiCtrlDir: {}
                        """,
                projectPath,
                model,
                baseSrcDir,
                javaDir,
                modelRealDir,
                modelVoDir,
                modelUtilsDir,
                modelActionServiceDir,
                modelActionServiceImplDir,
                entityPath,
                adminModulePath,
                entityServicePath,
                adminServiceImplPath,
                apiModulePath,
                adminCtrlDir,
                tenantCtrlDir,
                merchantCtrlDir,
                consumerCtrlDir,
                apiCtrlDir
        );

        String adminSrc = model + "admin";
        String apiSrc = model + "api";
        String entitySrc = "entity" + model;

        String entityName = tableInfo.getEntityName();
        String[] entityNameArr = StrUtils.splitByCharacterTypeCamelCase(entityName);

        String[] nameArr = entityNameArr;
//        if (
//                ArrayUtils.isNotEmpty(entityNameArr)
//                        && entityNameArr.length > 1
//                        && entityNameArr[0].trim().equalsIgnoreCase(model.trim())
//        ) {
//            nameArr = ArrayUtils.remove(nameArr, 0);
//        }

        String entityValue = StrUtils.join(nameArr, "");
        String controllerUrl = StrUtils.join(nameArr, "/").toLowerCase();
        String permission = StrUtils.join(entityNameArr, "::").toLowerCase();


        String apiControllerUrl = StrUtils.join(nameArr, "/").toLowerCase();
        String apiPermission = StrUtils.join(entityNameArr, "::").toLowerCase();

        String parentPath = getPathInfo(OutputFile.parent);

        log.info(
                """
                        \n■■■■■
                        ============================================
                        adminSrc: {}
                        apiSrc: {}
                        entitySrc: {}
                        entityName: {}
                        entityValue: {}
                        controllerUrl: {}
                        permission: {}
                        apiCntrollerUrl: {}
                        apiPermission: {}
                        parentPath: {}
                        """,
                adminSrc,
                apiSrc,
                entitySrc,
                entityName,
                entityValue,
                controllerUrl,
                permission,
                apiControllerUrl,
                apiPermission,
                parentPath
        );

        Map<String, String> userCustom = new HashMap<>();
        userCustom.put("entityValue", entityValue);
        userCustom.put("controllerUrl", controllerUrl);
        userCustom.put("permission", permission);

        userCustom.put("apiControllerUrl", apiControllerUrl);
        userCustom.put("apiPermission", apiPermission);

        objectMap.put("userCustom", userCustom);

        Map<String, String> userPackage = new HashMap<>();
        userPackage.put("entityServiceApi", "com.supsp.service" + model + ".service.api");
        userPackage.put("entityServiceApiImpl", "com.supsp." + model + "admin.service.impl.api");
        userPackage.put("apiController", "com.supsp." + model + "api.controller");

        userPackage.put("entityControllerService", "com.supsp." + model + "api.service");
        userPackage.put("entityControllerServiceImpl", "com.supsp." + model + "api.service.impl");
        objectMap.put("userPackage", userPackage);

        Map<String, String> serial = new HashMap<>();
        serial.put("RequestVersionUID", String.valueOf(RandomUtil.randomLong()));
        serial.put("ParamsVersionUID", String.valueOf(RandomUtil.randomLong()));
        serial.put("FilterVersionUID", String.valueOf(RandomUtil.randomLong()));
        serial.put("SorterVersionUID", String.valueOf(RandomUtil.randomLong()));
        serial.put("VoVersionUID", String.valueOf(RandomUtil.randomLong()));
        objectMap.put("serial", serial);

        customFiles.forEach(file -> {
            String fileName = modelRealDir +
                    File.separator +
                    file.getFilePath() +
                    File.separator +
                    entityName +
                    file.getFileName();

            if ("params".equals(file.getFilePath())) {
                fileName = modelRealDir +
                        File.separator +
                        file.getFilePath() +
                        File.separator +
                        entityName +
                        File.separator +
                        entityName +
                        file.getFileName();
            }

            if ("vo".equals(file.getFilePath())) {
                fileName = modelVoDir +
                        File.separator +
                        entityName +
                        file.getFileName();
            }

            if ("utils".equals(file.getFilePath())) {
                fileName = modelUtilsDir +
                        File.separator +
                        entityName +
                        file.getFileName();
            }

            if ("action".equals(file.getFilePath())) {
                fileName = modelActionServiceDir +
                        File.separator + "I" +
                        entityName +
                        file.getFileName();
            }

            if ("actionImpl".equals(file.getFilePath())) {
                fileName = modelActionServiceImplDir +
                        File.separator +
                        entityName +
                        file.getFileName();
            }

            if (
                    "admin".equals(file.getFilePath())
            ) {
                fileName = adminCtrlDir +
                        File.separator +
                        entityName  +
                        "Controller.java";
            }

            if (
                    "tenant".equals(file.getFilePath())
            ) {
                fileName = tenantCtrlDir +
                        File.separator +
                        entityName  +
                        "Controller.java";
            }

            if ("merchant".equals(file.getFilePath())) {
                fileName = merchantCtrlDir +
                        File.separator +
                        entityName  +
                        "Controller.java";
            }

            if (
                    "consumer".equals(file.getFilePath())
            ) {
                fileName = consumerCtrlDir +
                        File.separator +
                        entityName  +
                        "Controller.java";
            }

            if ("api".equals(file.getFilePath())) {
                fileName = apiCtrlDir +
                        File.separator +
                        entityName  +
                        "Controller.java";
            }

            log.info(
                    """
                            \n■■■■■
                            ============================================
                            file: {}
                            getFilePath: {}
                            getFileName: {}
                            fileName: {}
                            """,
                    JsonUtil.toString(file),
                    file.getFilePath(),
                    file.getFileName(),
                    fileName
            );

            this.outputFile(
                    new File(fileName),
                    objectMap,
                    file.getTemplatePath(),
                    file.isFileOverride()
            );
        });
    }
}
