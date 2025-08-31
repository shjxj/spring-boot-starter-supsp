package com.supsp.springboot.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class SysAsyncConfigurer implements AsyncConfigurer {

    @Value("${supsp.async.threadPool.corePoolSize:10}")
    private int corePoolSize;

    @Value("${supsp.async.threadPool.maxPoolSize:100}")
    private int maxPoolSize;

    @Value("${supsp.async.threadPool.queueCapacity:50}")
    private int queueCapacity;

    @Value("${supsp.async.threadPool.keepAliveSeconds:200}")
    private int keepAliveSeconds;

    @Value("${supsp.async.threadPool.awaitTerminationSeconds:60}")
    private int awaitTerminationSeconds;

    @Value("${supsp.async.threadPool.threadNamePrefix:xbzx-async}")
    private String threadNamePrefix;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 设置核心线程数 返回可用处理器的Java虚拟机的数量。
        // executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        // executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()*5);
        executor.setMaxPoolSize(maxPoolSize);
        // 线程池所使用的缓冲队列
        //  executor.setQueueCapacity(Runtime.getRuntime().availableProcessors()*2);
        executor.setQueueCapacity(queueCapacity);
        //线程名称的前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        // 配置线程的空闲时间
        executor.setKeepAliveSeconds(keepAliveSeconds);

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /*异步任务中异常处理*/
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params) -> {
            // todo 异步方法异常处理
            log.debug(
                    "class#method: {}#{}",
                    method.getDeclaringClass().getName(),
                    method.getName()
            );
            log.debug(
                    "type: {}",
                    ex.getClass().getName()
            );
            log.error(
                    "exception: {}",
                    ex.getMessage()
            );
        };
    }

}
