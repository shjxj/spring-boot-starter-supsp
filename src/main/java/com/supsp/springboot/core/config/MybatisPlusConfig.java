package com.supsp.springboot.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.supsp.springboot.core.config.interceptors.CacheObjectInterceptor;
import com.supsp.springboot.core.config.interceptors.OperatorInterceptor;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.supsp.supbackend.model.*.mapper"})
public class MybatisPlusConfig {

    @Resource
    private CacheObjectInterceptor cacheObjectInterceptor;

    @Resource
    private OperatorInterceptor operatorInterceptor;


    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求。默认false
        paginationInnerInterceptor.setOverflow(false);
        // 单页分页条数限制，默认无限制
        paginationInnerInterceptor.setMaxLimit(500L);
        // 设置数据库类型
        paginationInnerInterceptor.setDbType(DbType.MYSQL);

        // 添加分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 自定义插件

        // 自动更新操作人信息
        interceptor.addInnerInterceptor(operatorInterceptor);

        // 缓存数据更新 最后放入
        interceptor.addInnerInterceptor(cacheObjectInterceptor);

        return interceptor;
    }
}
