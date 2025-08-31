package com.supsp.springboot.core.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.consts.ModelConstants;
import com.supsp.springboot.core.generator.utils.CustomFileEnum;
import com.supsp.springboot.core.generator.utils.EnhanceVelocityTemplateEngine;
import com.supsp.springboot.core.utils.CommonTools;
import com.supsp.springboot.core.utils.StrUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class GeneratorUtil {
    private final static Logger log = LoggerFactory.getLogger(GeneratorUtil.class);

    /**
     * export MYSQL_HOST="127.0.0.1"
     * export MYSQL_PORT="3306"
     * export MYSQL_USER="***"
     * export MYSQL_PWD="***"
     */

//    private final static String DB_HOST = System.getenv("MYSQL_HOST");
//    private final static String DB_PORT = System.getenv("MYSQL_PORT");
//    private final static String DB_USER = System.getenv("MYSQL_USER");
//    private final static String DB_PWD = System.getenv("MYSQL_PWD");
//    private final static String DB_NAME = "sup_shop";

    private final static String AUTHOR = "com.supsp";
    private final static String PKG_PARENT = "com.supsp";
    private final static String PARENT_MODULE = System.getProperty("project");
    private final static String ROOT_PKG = PKG_PARENT + "." + PARENT_MODULE;

    private final static String ROOT_CORE_PKG = ROOT_PKG + ".core";

    private final static String SUPER_ENTITY_CLASS = ROOT_CORE_PKG + ".model.BaseModelEntity";
    // com.supsp.springboot.core.interfaces
    private final static String SUPER_MAPPER_CLASS = ROOT_CORE_PKG + ".model.IEntityMapper";
    private final static String SUPER_SERVICE_CLASS = ROOT_CORE_PKG + ".model.IEntityService";
    private final static String SUPER_SERVICE_IMPL_CLASS = ROOT_CORE_PKG + ".model.BaseEntityServiceImpl";
    private final static String SUPER_CONTROLLER_CLASS = ROOT_CORE_PKG + ".base.BaseEntityController";


    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的 {" + tip + "};");
    }

    private static String scanner(String tip, String defaultVal) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n请设置" + tip + "[" + defaultVal + "]：");
        if (scanner.hasNextLine()) {
            String ipt = scanner.nextLine();
            if (StrUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        return defaultVal;
    }

    private static boolean dirIsExists(String dir) {
        if (StrUtils.isBlank(dir)) {
            return false;
        }
        File file = new File(dir);
        return file.exists();
    }

    // 处理 all 情况
    protected static List<String> getTables(String modelName, String tables) {
        List<String> tableList = new ArrayList<>();
//        if (
//                StrUtils.isBlank(tables)
//                        || StringUtils.equalsIgnoreCase(tables, "all")
//        ) {
        if (StringUtils.equalsIgnoreCase(tables, "all")) {
            tableList.add("^" + modelName + "_.*");
            return tableList;
        }
        return Arrays.stream(tables.split(","))
                .filter(StringUtils::isNotBlank)
                .map(v -> modelName + '_' + v.trim())
                .toList();
    }


    public static void doGenerator()
            throws Exception {

        log.info(
                """
                        
                        project: {}
                        userDir: {}
                        javaDir: {}
                        """,
                System.getProperty("project"),
                System.getProperty("userDir"),
                System.getProperty("javaDir")
        );
        String userDir = System.getProperty("userDir");
        String project = System.getProperty("project");
        String projectPath = userDir + "/" + project;
        String srcDir = projectPath + "/src/main";
        String javaDir = srcDir + System.getProperty("javaDir");
        String resourcesDir = srcDir + "/resources";
        String resourcesMapperDir = resourcesDir + "/mapper";
        String modelDir = javaDir + "/model";

        String controllerDir = javaDir + "/controller";
        String adminControllerDir = controllerDir + "/admin";
        String tenantControllerDir = controllerDir + "/tenant";
        String merchantControllerDir = controllerDir + "/merchant";
        String consumerControllerDir = controllerDir + "/consumer";
        String apiControllerDir = controllerDir + "/api";

        log.info(
                """
                        \n■■■■■
                        ============================================
                        projectPath: {}
                        srcDir: {}
                        javaDir: {}
                        resourcesDir: {}
                        resourcesMapperDir: {}
                        ============================================
                        modelDir: {}
                        ============================================
                        ■ controllerDir: {}
                        adminControllerDir: {}
                        tenantControllerDir: {}
                        merchantControllerDir: {}
                        consumerControllerDir: {}
                        apiControllerDir: {}
                        ============================================
                        ■ resourcesDir
                        """,
                projectPath,
                srcDir,
                javaDir,
                resourcesDir,
                resourcesMapperDir,
                modelDir,
                controllerDir,
                adminControllerDir,
                tenantControllerDir,
                merchantControllerDir,
                consumerControllerDir,
                apiControllerDir
        );

        String model = scanner("模块名称").trim().toLowerCase();
        // model = model.trim().toLowerCase();

        String xmlDir = resourcesMapperDir + "/" + model;

        String mapperDir = resourcesMapperDir + "/" + model;

        String modelRealDir = modelDir + "/" + model;
        String modelControllerDir = modelRealDir + "/controller";
        String modelEntityDir = modelRealDir + "/entity";
        String modelParamsDir = modelRealDir + "/params";
        String modelVoDir = modelRealDir + "/vo";
        String modelUtilsDir = modelRealDir + "/utils";
        String modelMapperDir = modelRealDir + "/mapper";
        String modelModelDir = modelRealDir + "/model";
        String modelServiceDir = modelRealDir + "/service";
        String modelServiceImplDir = modelServiceDir + "/impl";
        String modelActionServiceDir = modelServiceDir + "/action";
        String modelActionServiceImplDir = modelServiceImplDir + "/action";

        String adminCtrlDir = adminControllerDir + "/" + model;
        String tenantCtrlDir = tenantControllerDir + "/" + model;
        String merchantCtrlDir = merchantControllerDir + "/" + model;
        String consumerCtrlDir = consumerControllerDir + "/" + model;
        String apiCtrlDir = apiControllerDir + "/" + model;
        log.info(
                """
                        \n■■■■■
                        模块名称: {}
                        xmlDir: {}
                        mapperDir: {}
                        modelRealDir: {}
                        ============================================
                        modelControllerDir: {}
                        modelEntityDir: {}
                        modelParamsDir: {}
                        modelVoDir: {}
                        modelUtilsDir: {}
                        modelMapperDir: {}
                        modelModelDir: {}
                        modelServiceDir: {}
                        modelServiceImplDir: {}
                        modelActionServiceDir: {}
                        modelActionServiceImplDir: {}
                        ============================================
                        adminCtrlDir: {}
                        tenantCtrlDir: {}
                        merchantCtrlDir: {}
                        consumerCtrlDir: {}
                        apiCtrlDir: {}
                        """,
                model,
                xmlDir,
                mapperDir,
                modelRealDir,
                modelControllerDir,
                modelEntityDir,
                modelParamsDir,
                modelVoDir,
                modelUtilsDir,
                modelMapperDir,
                modelModelDir,
                modelServiceDir,
                modelServiceImplDir,
                modelActionServiceDir,
                modelActionServiceImplDir,
                adminCtrlDir,
                tenantCtrlDir,
                merchantCtrlDir,
                consumerCtrlDir,
                apiCtrlDir
        );

        String tableName = scanner("表名, 多个英文逗号分隔, all 为全部 [模块前缀无需输入, 如模块名为 ums 表名为 ums_org, 则只需要输入 org]");

        List<String> tables = getTables(model, tableName);

//        String hasTenantId = scanner("是否生成配套tenantId的方法[Yes(Y是)/No(N否)]", "No");
//        boolean hasTenant = StringUtils.startsWithIgnoreCase(hasTenantId, "Y");
//
//        String hasMerchantId = scanner("是否生成配套merchantId的方法[Yes(Y是)/No(N否)]", "No");
//        boolean hasMerchant = StringUtils.startsWithIgnoreCase(hasMerchantId, "Y");
//
//        String hasShopId = scanner("是否生成配套shopId的方法[Yes(Y是)/No(N否)]", "Yes");
//        boolean hasShop = StringUtils.startsWithIgnoreCase(hasShopId, "Y");
//
//        String hasStoreId = scanner("是否生成配套storeId的方法[Yes(Y是)/No(N否)]", "No");
//        boolean hasStore = StringUtils.startsWithIgnoreCase(hasStoreId, "Y");
//
//        String hasOrgId = scanner("是否生成配套orgId的方法[Yes(Y是)/No(N否)]", "No");
//        boolean hasOrg = StringUtils.startsWithIgnoreCase(hasOrgId, "Y");

        String hasTypeVal = scanner("是否包含类型[Yes(Y是)/No(N否)]", "No");
        boolean hasType = StringUtils.startsWithIgnoreCase(hasTypeVal, "Y");

        String hasParentVal = scanner("是否包含上级[Yes(Y是)/No(N否)]", "No");
        boolean hasParent = StringUtils.startsWithIgnoreCase(hasParentVal, "Y");

//        String hasMemberId = scanner("是否生成配套memberId的方法[Yes(Y是)/No(N否)]", "No");
//        boolean hasMember = StringUtils.startsWithIgnoreCase(hasMemberId, "Y");

        String enableEntityOverride = scanner("是否覆盖 Entity 实体, 同时覆盖 PageRequest 和 VO等 [Yes(Y是)/No(N否)]", "No");
        boolean entityOverride = StringUtils.startsWithIgnoreCase(enableEntityOverride, "Y");

        String enableControllerOverride = scanner("是否覆盖 Controller 控制器 [Yes(Y是)/No(N否)]", "No");
        boolean controllerOverride = StringUtils.startsWithIgnoreCase(enableControllerOverride, "Y");

        String enableMapperOverride = scanner("是否覆盖 Mapper [Yes(Y是)/No(N否)]", "No");
        boolean mapperOverride = StringUtils.startsWithIgnoreCase(enableMapperOverride, "Y");

        String enableServiceOverride = scanner("是否覆盖 Service [Yes(Y是)/No(N否)]", "No");
        boolean serviceOverride = StringUtils.startsWithIgnoreCase(enableServiceOverride, "Y");

        String enableModelOverride = scanner("是否覆盖 Model [Yes(Y是)/No(N否)]", "No");
        boolean modelOverride = StringUtils.startsWithIgnoreCase(enableModelOverride, "Y");

        String enableActionOverride = scanner("是否覆盖 ActionService [Yes(Y是)/No(N否)]", "No");
        boolean actionOverride = StringUtils.startsWithIgnoreCase(enableActionOverride, "Y");

        String enableUtilsOverride = scanner("是否覆盖 Utils [Yes(Y是)/No(N否)]", "No");
        boolean utilsOverride = StringUtils.startsWithIgnoreCase(enableUtilsOverride, "Y");

        String buildAdminValue = scanner("是否生 Admin [管理端] 控制器 [Yes(Y是)/No(N否)]", "Yes");
        boolean buildAdmin = StringUtils.startsWithIgnoreCase(buildAdminValue, "Y");

        String buildTenantValue = scanner("是否生 Tenant [租户端] 控制器 [Yes(Y是)/No(N否)]", "No");
        boolean buildTenant = StringUtils.startsWithIgnoreCase(buildTenantValue, "Y");

        String buildMerchantValue = scanner("是否生 Merchant [商户端] 控制器 [Yes(Y是)/No(N否)]", "No");
        boolean buildMerchant = StringUtils.startsWithIgnoreCase(buildMerchantValue, "Y");

        String buildConsumerValue = scanner("是否生 Consumer [消费者端] 控制器 [Yes(Y是)/No(N否)]", "No");
        boolean buildConsumer = StringUtils.startsWithIgnoreCase(buildConsumerValue, "Y");

        String buildApiValue = scanner("是否生 Api [API端] 控制器 [Yes(Y是)/No(N否)]", "No");
        boolean buildApi = StringUtils.startsWithIgnoreCase(buildApiValue, "Y");

        String baseModelPkg = "model";
        String moduleModelPkg = baseModelPkg + "." + model;
        String modelBaseControllerPkg = moduleModelPkg + ".controller";
        String modelEntityPkg = moduleModelPkg + ".entity";
        String modelMapperPkg = moduleModelPkg + ".mapper";
        String modelServicePkg = moduleModelPkg + ".service";
        String modelServiceImplPkg = modelServicePkg + ".impl";

        String modelActionServicePkg = modelServicePkg + ".action";
        String modelActionServiceImplPkg = modelServiceImplPkg + ".action";

        String modelModelPkg = moduleModelPkg + ".model";
        String modelParamsPkg = moduleModelPkg + ".params";
        String modelVoPkg = moduleModelPkg + ".vo";
        String modelUtilsPkg = moduleModelPkg + ".utils";

        String packageModelBase = ROOT_PKG + "." + moduleModelPkg;

        String packageBaseController = ROOT_PKG + "." + modelBaseControllerPkg;
        String packageEntity = ROOT_PKG + "." + modelEntityPkg;
        String packageMapper = ROOT_PKG + "." + modelMapperPkg;
        String packageService = ROOT_PKG + "." + modelServicePkg;
        String packageServiceImpl = ROOT_PKG + "." + modelServiceImplPkg;

        String packageActionService = ROOT_PKG + "." + modelActionServicePkg;
        String packageActionServiceImpl = ROOT_PKG + "." + modelActionServiceImplPkg;

        String packageModel = ROOT_PKG + "." + modelModelPkg;
        String packageParams = ROOT_PKG + "." + modelParamsPkg;
        String packageVo = ROOT_PKG + "." + modelVoPkg;
        String packageUtils = ROOT_PKG + "." + modelUtilsPkg;

        String baseControllerPkg = "controller";
        String controllerAdminPkg = baseControllerPkg + ".admin";
        String controllerTenantPkg = baseControllerPkg + ".tenant";
        String controllerMerchantPkg = baseControllerPkg + ".merchant";
        String controllerConsumerPkg = baseControllerPkg + ".consumer";
        String controllerApiPkg = baseControllerPkg + ".api";

        String moduleCtrlAdminPkg = controllerAdminPkg + "." + model;
        String moduleCtrlTenantPkg = controllerTenantPkg + "." + model;
        String moduleCtrlMerchantPkg = controllerMerchantPkg + "." + model;
        String moduleCtrlConsumerPkg = controllerConsumerPkg + "." + model;
        String moduleCtrlApiPkg = controllerApiPkg + "." + model;

        String packageCtrlAdmin = ROOT_PKG + "." + moduleCtrlAdminPkg;
        String packageCtrlTenant = ROOT_PKG + "." + moduleCtrlTenantPkg;
        String packageCtrlMerchant = ROOT_PKG + "." + moduleCtrlMerchantPkg;
        String packageCtrlConsumer = ROOT_PKG + "." + moduleCtrlConsumerPkg;
        String packageCtrlApi = ROOT_PKG + "." + moduleCtrlApiPkg;

        log.info(
                """
                        \n■■■■■
                        tables: {}
                        ============================================
                        hasType: {} - {}
                        hasParent: {} - {}
                        ============================================
                        baseModelPkg: {}
                        moduleModelPkg: {}
                        modelEntityPkg: {}
                        modelMapperPkg: {}
                        modelServicePkg: {}
                        modelServiceImplPkg: {}
                        modelActionServicePkg: {}
                        modelActionServiceImplPkg: {}
                        modelBaseControllerPkg: {}
                        modelModelPkg: {}
                        modelParamsPkg: {}
                        modelVoPkg: {}
                        ============================================
                        packageModelBase: {}
                        packageBaseController: {}
                        packageEntity: {}
                        packageMapper: {}
                        packageService: {}
                        packageServiceImpl: {}
                        packageActionService: {}
                        packageActionServiceImpl: {}
                        packageModel: {}
                        packageParams: {}
                        packageVo: {}
                        packageUtils: {}
                        ============================================
                        moduleCtrlAdminPkg: {}
                        moduleCtrlTenantPkg: {}
                        moduleCtrlMerchantPkg: {}
                        moduleCtrlConsumerPkg: {}
                        moduleCtrlApiPkg: {}
                        ============================================
                        packageCtrlAdmin: {}
                        packageCtrlTenant: {}
                        packageCtrlMerchant: {}
                        packageCtrlConsumer: {}
                        packageCtrlApi: {}
                        """,
                tables,
                hasTypeVal, hasType,
                hasParentVal, hasParent,
                baseModelPkg,
                moduleModelPkg,
                modelEntityPkg,
                modelMapperPkg,
                modelServicePkg,
                modelServiceImplPkg,
                modelActionServicePkg,
                modelActionServiceImplPkg,
                modelBaseControllerPkg,
                modelModelPkg,
                modelParamsPkg,
                modelVoPkg,
                packageModelBase,
                packageBaseController,
                packageEntity,
                packageMapper,
                packageService,
                packageServiceImpl,
                packageActionService,
                packageActionServiceImpl,
                packageModel,
                packageParams,
                packageVo,
                packageUtils,
                moduleCtrlAdminPkg,
                moduleCtrlTenantPkg,
                moduleCtrlMerchantPkg,
                moduleCtrlConsumerPkg,
                moduleCtrlApiPkg,
                packageCtrlAdmin,
                packageCtrlTenant,
                packageCtrlMerchant,
                packageCtrlConsumer,
                packageCtrlApi
        );

        String dbHost = System.getProperty("dbHost");
        String dbPort = System.getProperty("dbPort");
        String dbUser = System.getProperty("dbUser");
        String dbPwd = System.getProperty("dbPwd");
        String dbName = System.getProperty("dbName");
        String dbUrl = "jdbc:mysql://" +
                dbHost +
                ":" +
                dbPort +
                "/" +
                dbName +
                "?useUnicode=true&useSSL=false&characterEncoding=utf8";

        log.info(
                """
                        \n■■■■■
                        dbName: {}
                        dbHost: {}
                        DB_PORT: {}
                        """,
                dbName,
                dbHost,
                dbPort
        );

        List<String> insertColumns = Arrays.asList(

                Constants.COLUMNS_MERCHANT_ID,
                Constants.COLUMNS_TENANT_ID
        );

        List<String> updateColumns = Arrays.asList(

//                Constants.COLUMNS_MERCHANT_ID,
//                Constants.COLUMNS_TENANT_ID,

                Constants.COLUMNS_CREATED_AT,
                Constants.COLUMNS_CREATED_MEMBER_TYPE,
                Constants.COLUMNS_CREATED_MEMBER_ID,
                Constants.COLUMNS_CREATED_MEMBER_NAME,
                Constants.COLUMNS_CREATED_MEMBER_ACCOUNT,
                Constants.COLUMNS_CREATED_MEMBER_IP,

                Constants.COLUMNS_CREATED_ORG_ID,
                Constants.COLUMNS_CREATED_ORG_NAME,
                Constants.COLUMNS_CREATED_STORE_ID,
                Constants.COLUMNS_CREATED_STORE_NAME,

                Constants.COLUMNS_UPDATED_AT,
                Constants.COLUMNS_UPDATED_MEMBER_TYPE,
                Constants.COLUMNS_UPDATED_MEMBER_ID,
                Constants.COLUMNS_UPDATED_MEMBER_NAME,
                Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
                Constants.COLUMNS_UPDATED_MEMBER_IP,

                Constants.COLUMNS_UPDATED_ORG_ID,
                Constants.COLUMNS_UPDATED_ORG_NAME,
                Constants.COLUMNS_UPDATED_STORE_ID,
                Constants.COLUMNS_UPDATED_STORE_NAME,

                Constants.COLUMNS_DELETED,
                Constants.COLUMNS_DELETED_MEMBER_TYPE,
                Constants.COLUMNS_DELETED_MEMBER_ID,
                Constants.COLUMNS_DELETED_MEMBER_NAME,
                Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
                Constants.COLUMNS_DELETED_MEMBER_IP,

                Constants.COLUMNS_DELETED_ORG_ID,
                Constants.COLUMNS_DELETED_ORG_NAME,
                Constants.COLUMNS_DELETED_STORE_ID,
                Constants.COLUMNS_DELETED_STORE_NAME,

                Constants.COLUMNS_DELETED_AT
        );

        List<String> readOnlyColumns = Arrays.asList(
                Constants.COLUMNS_MERCHANT_ID,
                Constants.COLUMNS_TENANT_ID,

                Constants.COLUMNS_CREATED_AT,
                Constants.COLUMNS_CREATED_MEMBER_TYPE,
                Constants.COLUMNS_CREATED_MEMBER_ID,
                Constants.COLUMNS_CREATED_MEMBER_NAME,
                Constants.COLUMNS_CREATED_MEMBER_ACCOUNT,
                Constants.COLUMNS_CREATED_MEMBER_IP,

                Constants.COLUMNS_CREATED_ORG_ID,
                Constants.COLUMNS_CREATED_ORG_NAME,
                Constants.COLUMNS_CREATED_STORE_ID,
                Constants.COLUMNS_CREATED_STORE_NAME,

                Constants.COLUMNS_UPDATED_AT,
                Constants.COLUMNS_UPDATED_MEMBER_TYPE,
                Constants.COLUMNS_UPDATED_MEMBER_ID,
                Constants.COLUMNS_UPDATED_MEMBER_NAME,
                Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
                Constants.COLUMNS_UPDATED_MEMBER_IP,

                Constants.COLUMNS_UPDATED_ORG_ID,
                Constants.COLUMNS_UPDATED_ORG_NAME,
                Constants.COLUMNS_UPDATED_STORE_ID,
                Constants.COLUMNS_UPDATED_STORE_NAME,

                Constants.COLUMNS_DELETED,
                Constants.COLUMNS_DELETED_MEMBER_TYPE,
                Constants.COLUMNS_DELETED_MEMBER_ID,
                Constants.COLUMNS_DELETED_MEMBER_NAME,
                Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
                Constants.COLUMNS_DELETED_MEMBER_IP,

                Constants.COLUMNS_DELETED_ORG_ID,
                Constants.COLUMNS_DELETED_ORG_NAME,
                Constants.COLUMNS_DELETED_STORE_ID,
                Constants.COLUMNS_DELETED_STORE_NAME,

                Constants.COLUMNS_DELETED_AT
        );

        List<String> docHiddenColumns = Arrays.asList(
                Constants.COLUMNS_DELETED,
                Constants.COLUMNS_DELETED_MEMBER_TYPE,
                Constants.COLUMNS_DELETED_MEMBER_ID,
                Constants.COLUMNS_DELETED_MEMBER_NAME,
                Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
                Constants.COLUMNS_DELETED_MEMBER_IP,
                Constants.COLUMNS_DELETED_AT
        );

        List<String> ignoreListColumns = Arrays.asList(
                Constants.COLUMNS_CREATED_MEMBER_TYPE,
                Constants.COLUMNS_CREATED_MEMBER_ID,
                Constants.COLUMNS_CREATED_MEMBER_NAME,
                Constants.COLUMNS_CREATED_MEMBER_ACCOUNT,
                Constants.COLUMNS_CREATED_MEMBER_IP,

                Constants.COLUMNS_CREATED_ORG_ID,
                Constants.COLUMNS_CREATED_ORG_NAME,
                Constants.COLUMNS_CREATED_STORE_ID,
                Constants.COLUMNS_CREATED_STORE_NAME,


                Constants.COLUMNS_UPDATED_MEMBER_TYPE,
                Constants.COLUMNS_UPDATED_MEMBER_ID,
                Constants.COLUMNS_UPDATED_MEMBER_NAME,
                Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
                Constants.COLUMNS_UPDATED_MEMBER_IP,

                Constants.COLUMNS_UPDATED_ORG_ID,
                Constants.COLUMNS_UPDATED_ORG_NAME,
                Constants.COLUMNS_UPDATED_STORE_ID,
                Constants.COLUMNS_UPDATED_STORE_NAME,


                Constants.COLUMNS_DELETED,
                Constants.COLUMNS_DELETED_MEMBER_TYPE,
                Constants.COLUMNS_DELETED_MEMBER_ID,
                Constants.COLUMNS_DELETED_MEMBER_NAME,
                Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
                Constants.COLUMNS_DELETED_MEMBER_IP,

                Constants.COLUMNS_DELETED_ORG_ID,
                Constants.COLUMNS_DELETED_ORG_NAME,
                Constants.COLUMNS_DELETED_STORE_ID,
                Constants.COLUMNS_DELETED_STORE_NAME,


                Constants.COLUMNS_DELETED_AT
        );

        List<String> deleteColumns = Arrays.asList(
                Constants.COLUMNS_DELETED,
                Constants.COLUMNS_DELETED_MEMBER_TYPE,
                Constants.COLUMNS_DELETED_MEMBER_ID,
                Constants.COLUMNS_DELETED_MEMBER_NAME,
                Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
                Constants.COLUMNS_DELETED_MEMBER_IP,
                Constants.COLUMNS_DELETED_AT
        );

        List<String> memberTypeColumns = Arrays.asList(
                Constants.COLUMNS_CREATED_MEMBER_TYPE,
                Constants.COLUMNS_UPDATED_MEMBER_TYPE,
                Constants.COLUMNS_DELETED_MEMBER_TYPE
        );

        List<String> dateTimeProperty = Arrays.asList(
                "LocalDateTime",
                "DateTime"
        );

        List<String> entityJsonIgnoreColumns = Arrays.asList(
                Constants.COLUMNS_ID,
                ""
        );

        List<String> ignoreVoColumns = Arrays.asList(
                Constants.COLUMNS_ID,
                ""
        );

        List<String> ignoreParamsColumns = Arrays.asList(
                Constants.COLUMNS_ID,
                Constants.COLUMNS_SHOW_STATUS,
                Constants.COLUMNS_ENABLE_STATUS,
                Constants.COLUMNS_AUDIT_STATUS
        );

        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.entity, modelEntityDir);
        pathInfo.put(OutputFile.mapper, modelMapperDir);
        pathInfo.put(OutputFile.service, modelServiceDir);
        pathInfo.put(OutputFile.serviceImpl, modelServiceImplDir);
        pathInfo.put(OutputFile.xml, xmlDir);
        pathInfo.put(OutputFile.controller, modelControllerDir);
        String parentModuleName = PARENT_MODULE;

        log.info(
                """
                        \n■■■■■
                        pathInfo: {}
                        PARENT_MODULE: {}
                        """,
                pathInfo,
                PARENT_MODULE
        );

        FastAutoGenerator.create(dbUrl, dbUser, dbPwd).globalConfig(
                        builder -> {
                            builder
                                    .outputDir(srcDir)
                                    .author(AUTHOR)
                                    .enableSpringdoc()
                                    .commentDate("yyyy-MM-dd HH:mm")
                                    .disableOpenDir();
                        }
                ).packageConfig(builder -> {
                    builder.parent(PKG_PARENT)
                            .moduleName(PARENT_MODULE)
                            .entity(modelEntityPkg)
                            .service(modelServicePkg)
                            .serviceImpl(modelServiceImplPkg)
                            .mapper(modelMapperPkg)
                            .controller(modelBaseControllerPkg)
                            .pathInfo(pathInfo);
                }).dataSourceConfig(builder -> {
                    builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
//                log.info(
//                        "☆☆☆☆☆ metaInfo.getColumnName: {}",
//                        metaInfo.getColumnName()
//                );
                        if (
                                ModelConstants.SYS_COLUMN_TYPE_MAP.containsKey(
                                        metaInfo.getColumnName().trim().toLowerCase()
                                )
                        ) {
//                    log.info(
//                            "■■■■■ ModelConstants.SYS_COLUMN_TYPE: \n{}\n{}",
//                            ModelConstants.SYS_COLUMN_TYPE_MAP.get(
//                                    metaInfo.getColumnName().trim().toLowerCase()
//                            ).getType(),
//                            ModelConstants.SYS_COLUMN_TYPE_MAP.get(
//                                    metaInfo.getColumnName().trim().toLowerCase()
//                            ).getPkg()
//                    );
                            return ModelConstants.SYS_COLUMN_TYPE_MAP.get(
                                    metaInfo.getColumnName().trim().toLowerCase()
                            );
                        }

                        // 兼容旧版本转换成Integer
                        if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                            return DbColumnType.SHORT;
                        }

                        return typeRegistry.getColumnType(metaInfo);
                    });
                }).strategyConfig(builder -> {
                    //.addExclude(model + "_")
                    // "^" + model + "_.*"
                    if (entityOverride) {
                        builder.entityBuilder().enableFileOverride();
                    }

                    if (controllerOverride) {
                        builder.controllerBuilder().enableFileOverride();
                    }

                    if (mapperOverride) {
                        builder.mapperBuilder().enableFileOverride();
                    }

                    if (serviceOverride) {
                        builder.serviceBuilder().enableFileOverride();
                    }

                    builder.addInclude(tables)
                            .enableCapitalMode()
                            .enableSkipView()
                            // entity
                            .entityBuilder()
                            .javaTemplate("templates/entity.java.vm")
                            // .enableFileOverride()
                            .superClass(SUPER_ENTITY_CLASS)
                            .enableActiveRecord()
                            .enableChainModel()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .logicDeleteColumnName("deleted")
                            .naming(NamingStrategy.underline_to_camel)
                            .enableColumnConstant()
                            // controller
                            .controllerBuilder()
                            .template("templates/controller.java.vm")
                            // .enableFileOverride()
                            .convertFileName(entityName -> {
                                return entityName + "BaseController";
                            })
                            .superClass(SUPER_CONTROLLER_CLASS)
                            .enableHyphenStyle()
                            .enableRestStyle()
                            // service
                            .serviceBuilder()
                            .serviceTemplate("templates/service.java.vm")
                            .serviceImplTemplate("templates/serviceImpl.java.vm")
                            // .enableFileOverride()
                            .superServiceClass(SUPER_SERVICE_CLASS)
                            .superServiceImplClass(SUPER_SERVICE_IMPL_CLASS)
                            // mepper
                            .mapperBuilder()
                            .mapperXmlTemplate("templates/mapper.xml.vm")
                            .mapperTemplate("templates/mapper.java.vm")
                            // .enableFileOverride()
                            .mapperAnnotation(Mapper.class)
                            .superClass(SUPER_MAPPER_CLASS)
                            .enableBaseResultMap()
                            .enableBaseColumnList();
                }).injectionConfig(builder -> {

                    builder.beforeOutputFile((tableInfo, objectMap) -> {

                        if (tableInfo == null) {
                            return;
                        }

                        String tabName = tableInfo.getName();
                        if (StrUtils.isBlank(tabName)) {
                            return;
                        }
                        String entityName = tableInfo.getEntityName();

                        String tableCamel = com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(tabName);

                        String tableBaseName = tabName;
                        if (tabName.startsWith(model + "_")) {
                            tableBaseName = StrUtils.substring(tabName, (model + "_").length()).trim();
                        }
                        String tableCapitalizeName = CommonTools.capitalizeTableName(tabName, model);
                        String tableUpperName = tableBaseName.toUpperCase();

                        log.info(
                                """
                                        \n■■■■■
                                        tabName: {}
                                        tableCamel: {}
                                        entityName: {}
                                        tableBaseName: {}
                                        tableCapitalizeName: {}
                                        tableUpperName: {}
                                        """,
                                tabName,
                                tableCamel,
                                entityName,
                                tableBaseName,
                                tableCapitalizeName,
                                tableUpperName
                        );

                        //
                        String hasPrefix = "N";
                        String overrideStr = "@Override";

                        // api
                        String uriPrefix = "";
                        String ctrlDoc = "";
                        String ctrlParams = "";
                        String callParams = "";

                        // model
                        String modelMethodDoc = "";
                        String modelMethodParams = "";
                        String modelCallSelfParams = "";

                        if (hasType) {
                            hasPrefix = "Y";
                            overrideStr = "";

                            // api
                            uriPrefix += "/{type}";
                            ctrlDoc += "\n     * @param type";
                            ctrlParams += "\n            @Parameter(description = \"类型\", name = \"type\", required = true)";
                            ctrlParams += "\n            @PathVariable(value = \"type\") String type,";
                            callParams += "type, ";

                            // model
                            modelMethodDoc += "\n     * @param type";
                            modelMethodParams += "\n            String type,";
                            modelCallSelfParams += "type, ";
                        }
                        if (hasParent) {
                            hasPrefix = "Y";
                            overrideStr = "";

                            // admin
                            uriPrefix += "/{pid}";

                            ctrlDoc += "\n     * @param pid";

                            ctrlParams += "\n            @Parameter(description = \"上级ID\", name = \"pid\", required = true)";
                            ctrlParams += "\n            @PathVariable(value = \"pid\") long pid,";

                            callParams += "pid, ";

                            // model
                            modelMethodDoc += "\n     * @param pid";
                            modelMethodParams += "\n            long pid,";
                            modelCallSelfParams += "pid, ";
                        }

                        String tableDataId = tableBaseName + "_id";
                        objectMap.put("tableBaseName", tableBaseName);
                        objectMap.put("tableCamel", tableCamel);
                        objectMap.put("tableDataId", tableDataId);
                        objectMap.put("tableCapitalizeName", tableCapitalizeName);
                        objectMap.put("tableUpperName", tableUpperName);
                        objectMap.put("insertColumns", insertColumns);
                        objectMap.put("updateColumns", updateColumns);
                        objectMap.put("readOnlyColumns", readOnlyColumns);
                        objectMap.put("docHiddenColumns", docHiddenColumns);
                        objectMap.put("ignoreListColumns", ignoreListColumns);
                        objectMap.put("deleteColumns", deleteColumns);
                        objectMap.put("entityJsonIgnoreColumns", entityJsonIgnoreColumns);
                        objectMap.put("ignoreVoColumns", ignoreVoColumns);
                        objectMap.put("ignoreParamsColumns", ignoreParamsColumns);
                        objectMap.put("memberTypeColumns", memberTypeColumns);
                        objectMap.put("dateTimeProperty", dateTimeProperty);

                        objectMap.put("hasType", hasType);
                        objectMap.put("hasParent", hasParent);

                        objectMap.put("hasPrefix", "y".equalsIgnoreCase(hasPrefix));
                        objectMap.put("overrideStr", overrideStr);

                        // api
                        objectMap.put("uriPrefix", uriPrefix);
                        objectMap.put("ctrlDoc", ctrlDoc);
                        objectMap.put("ctrlParams", ctrlParams);
                        objectMap.put("callParams", callParams);

                        // model
                        objectMap.put("modelMethodDoc", modelMethodDoc);
                        objectMap.put("modelMethodParams", modelMethodParams);
                        objectMap.put("modelCallSelfParams", modelCallSelfParams);

                    });

                    Map<String, String> custom = new HashMap<>();
                    custom.put("projectPath", projectPath);
                    custom.put("srcDir", srcDir);
                    custom.put("javaDir", javaDir);
                    custom.put("resourcesDir", resourcesDir);
                    custom.put("resourcesMapperDir", resourcesMapperDir);
                    custom.put("modelDir", modelDir);

                    custom.put("controllerDir", controllerDir);
                    custom.put("adminControllerDir", adminControllerDir);
                    custom.put("tenantControllerDir", tenantControllerDir);
                    custom.put("merchantControllerDir", merchantControllerDir);
                    custom.put("consumerControllerDir", consumerControllerDir);
                    custom.put("apiControllerDir", apiControllerDir);

                    custom.put("model", model);
                    custom.put("pkgParent", PKG_PARENT);

                    custom.put("modelRealDir", modelRealDir);
                    custom.put("modelControllerDir", modelControllerDir);
                    custom.put("modelEntityDir", modelEntityDir);
                    custom.put("modelParamsDir", modelParamsDir);
                    custom.put("modelVoDir", modelVoDir);
                    custom.put("modelUtilsDir", modelUtilsDir);
                    custom.put("modelMapperDir", modelMapperDir);
                    custom.put("modelModelDir", modelModelDir);
                    custom.put("modelServiceDir", modelServiceDir);
                    custom.put("modelServiceImplDir", modelServiceImplDir);

                    custom.put("modelActionServiceDir", modelActionServiceDir);
                    custom.put("modelActionServiceImplDir", modelActionServiceImplDir);

                    custom.put("adminCtrlDir", adminCtrlDir);
                    custom.put("tenantCtrlDir", tenantCtrlDir);
                    custom.put("merchantCtrlDir", merchantCtrlDir);
                    custom.put("consumerCtrlDir", consumerCtrlDir);
                    custom.put("apiCtrlDir", apiCtrlDir);

                    custom.put("baseModelPkg", baseModelPkg);
                    custom.put("moduleModelPkg", moduleModelPkg);
                    custom.put("modelBaseControllerPkg", modelBaseControllerPkg);
                    custom.put("modelEntityPkg", modelEntityPkg);
                    custom.put("modelMapperPkg", modelMapperPkg);
                    custom.put("modelServicePkg", modelServicePkg);
                    custom.put("modelServiceImplPkg", modelServiceImplPkg);
                    custom.put("modelModelPkg", modelModelPkg);
                    custom.put("modelParamsPkg", modelParamsPkg);
                    custom.put("modelVoPkg", modelVoPkg);

                    custom.put("packageModelBase", packageModelBase);
                    custom.put("packageBaseController", packageBaseController);
                    custom.put("packageEntity", packageEntity);
                    custom.put("packageMapper", packageMapper);
                    custom.put("packageService", packageService);
                    custom.put("packageServiceImpl", packageServiceImpl);
                    custom.put("packageActionService", packageActionService);
                    custom.put("packageActionServiceImpl", packageActionServiceImpl);

                    custom.put("packageModel", packageModel);
                    custom.put("packageParams", packageParams);
                    custom.put("packageVo", packageVo);
                    custom.put("packageUtils", packageUtils);

                    custom.put("packageCtrlAdmin", packageCtrlAdmin);
                    custom.put("packageCtrlTenant", packageCtrlTenant);
                    custom.put("packageCtrlMerchant", packageCtrlMerchant);
                    custom.put("packageCtrlConsumer", packageCtrlConsumer);
                    custom.put("packageCtrlApi", packageCtrlApi);

                    builder.customMap(Collections.singletonMap("custom", custom));

                    // Model
                    CustomFile.Builder modelBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.Model.getFileName() + ".java"
                            )
                            .templatePath(
                                    CustomFileEnum.Model.getTemplatePath()
                            )
                            .packageName(packageModel)
                            .filePath("model");
                    if (modelOverride) {
                        modelBuild.enableFileOverride();
                    }
                    builder.customFile(
                            modelBuild.build()
                    );

                    // PageRequest
                    CustomFile.Builder pageRequestBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.PageRequest.getFileName() + ".java"
                            )
                            .templatePath(
                                    CustomFileEnum.PageRequest.getTemplatePath()
                            )
                            .packageName(packageParams)
                            .filePath("params");
                    if (entityOverride) {
                        pageRequestBuild.enableFileOverride();
                    }
                    builder.customFile(
                            pageRequestBuild.build()
                    );

                    // PageParams
                    CustomFile.Builder pageParamsBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.PageParams.getFileName() + ".java"
                            )
                            .templatePath(
                                    CustomFileEnum.PageParams.getTemplatePath()
                            )
                            .packageName(packageParams)
                            .filePath("params");
                    if (entityOverride) {
                        pageParamsBuild.enableFileOverride();
                    }
                    builder.customFile(
                            pageParamsBuild.build()
                    );

                    // PageFilter
                    CustomFile.Builder pageFilterBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.PageFilter.getFileName() + ".java"
                            )
                            .templatePath(
                                    CustomFileEnum.PageFilter.getTemplatePath()
                            )
                            .packageName(packageParams)
                            .filePath("params");
                    if (entityOverride) {
                        pageFilterBuild.enableFileOverride();
                    }
                    builder.customFile(
                            pageFilterBuild.build()
                    );

                    // EntityVo
                    CustomFile.Builder entityVoBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.EntityVo.getFileName() + ".java"
                            )
                            .templatePath(
                                    CustomFileEnum.EntityVo.getTemplatePath()
                            )
                            .packageName(packageVo)
                            .filePath("vo");
                    if (entityOverride) {
                        entityVoBuild.enableFileOverride();
                    }
                    builder.customFile(
                            entityVoBuild.build()
                    );

                    // Action
                    CustomFile.Builder actionServiceBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.Action.getFileName() +
                                            "Service.java"
                            )
                            .templatePath(
                                    CustomFileEnum.Action.getTemplatePath()
                            )
                            .packageName(packageActionService)
                            .filePath("action");
                    if (actionOverride) {
                        actionServiceBuild.enableFileOverride();
                    }
                    builder.customFile(
                            actionServiceBuild.build()
                    );

                    // ActionImpl
                    CustomFile.Builder actionImplServiceBuild = new CustomFile.Builder()
                            .fileName(
                                    "ActionServiceImpl.java"
                            )
                            .templatePath(
                                    CustomFileEnum.ActionImpl.getTemplatePath()
                            )
                            .packageName(packageActionServiceImpl)
                            .filePath("actionImpl");
                    if (actionOverride) {
                        actionImplServiceBuild.enableFileOverride();
                    }
                    builder.customFile(
                            actionImplServiceBuild.build()
                    );

                    // EntityUtils
                    CustomFile.Builder entityUtilsBuild = new CustomFile.Builder()
                            .fileName(
                                    CustomFileEnum.EntityUtils.getFileName() +
                                            ".java"
                            )
                            .templatePath(
                                    CustomFileEnum.EntityUtils.getTemplatePath()
                            )
                            .packageName(packageUtils)
                            .filePath("utils");
                    if (utilsOverride) {
                        entityUtilsBuild.enableFileOverride();
                    }
                    builder.customFile(
                            entityUtilsBuild.build()
                    );

                    if (buildAdmin) {
                        // Admin Controller
                        CustomFile.Builder adminControllerBuild = new CustomFile.Builder()
                                .fileName(
                                        CustomFileEnum.Admin.getFileName() +
                                                "Controller.java"
                                )
                                .templatePath(
                                        CustomFileEnum.Admin.getTemplatePath()
                                )
                                .packageName(packageCtrlAdmin)
                                .filePath("admin");
                        if (controllerOverride) {
                            adminControllerBuild.enableFileOverride();
                        }
                        builder.customFile(
                                adminControllerBuild.build()
                        );
                    }

                    if (buildTenant) {
                        // Tenant Controller
                        CustomFile.Builder tenantControllerBuild = new CustomFile.Builder()
                                .fileName(
                                        CustomFileEnum.Tenant.getFileName() +
                                                "Controller.java"
                                )
                                .templatePath(
                                        CustomFileEnum.Tenant.getTemplatePath()
                                )
                                .packageName(packageCtrlTenant)
                                .filePath("tenant");
                        if (controllerOverride) {
                            tenantControllerBuild.enableFileOverride();
                        }
                        builder.customFile(
                                tenantControllerBuild.build()
                        );
                    }

                    if (buildMerchant) {
                        // Merchant Controller
                        CustomFile.Builder merchantControllerBuild = new CustomFile.Builder()
                                .fileName(
                                        CustomFileEnum.Merchant.getFileName() +
                                                "Controller.java"
                                )
                                .templatePath(
                                        CustomFileEnum.Merchant.getTemplatePath()
                                )
                                .packageName(packageCtrlMerchant)
                                .filePath("merchant");
                        if (controllerOverride) {
                            merchantControllerBuild.enableFileOverride();
                        }
                        builder.customFile(
                                merchantControllerBuild.build()
                        );
                    }

                    if (buildConsumer) {
                        // Consumer Controller
                        CustomFile.Builder consumerControllerBuild = new CustomFile.Builder()
                                .fileName(
                                        CustomFileEnum.Consumer.getFileName() +
                                                "Controller.java"
                                )
                                .templatePath(
                                        CustomFileEnum.Consumer.getTemplatePath()
                                )
                                .packageName(packageCtrlConsumer)
                                .filePath("consumer");
                        if (controllerOverride) {
                            consumerControllerBuild.enableFileOverride();
                        }
                        builder.customFile(
                                consumerControllerBuild.build()
                        );
                    }

                    if (buildApi) {
                        // Api Controller
                        CustomFile.Builder apiControllerBuild = new CustomFile.Builder()
                                .fileName(
                                        CustomFileEnum.Api.getFileName() +
                                                "Controller.java"
                                )
                                .templatePath(
                                        CustomFileEnum.Api.getTemplatePath()
                                )
                                .packageName(packageCtrlApi)
                                .filePath("api");
                        if (controllerOverride) {
                            apiControllerBuild.enableFileOverride();
                        }
                        builder.customFile(
                                apiControllerBuild.build()
                        );
                    }

                })
//                .templateConfig(builder -> {
//                    builder.entity("templates/entity.java.vm");
//                    builder.mapper("templates/mapper.java.vm");
//                    builder.xml("templates/mapper.xml.vm");
//                    builder.service("templates/service.java.vm");
//                    builder.serviceImpl("templates/serviceImpl.java.vm");
//                    builder.controller("templates/controller.java.vm");
//                })
                .templateEngine(new EnhanceVelocityTemplateEngine())
                .execute();
    }
}
