package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.vo.IpRegionInfo;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class Ip2regionSearcher implements DisposableBean {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\|");

    private final Searcher searcher;

    public Ip2regionSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    @SneakyThrows
    public String searchStr(String ip) {
        return searcher.search(ip);
    }

    public IpRegionInfo search(String ip) {
        String region = searchStr(ip);
        if (region == null) {
            return null;
        }
        IpRegionInfo ipRegionInfo = new IpRegionInfo();
        ipRegionInfo.setIp(ip);
        String[] splitInfos = SPLIT_PATTERN.split(region);
        // 补齐5位
        if (splitInfos.length < 5) {
            splitInfos = Arrays.copyOf(splitInfos, 5);
        }
        ipRegionInfo.setCountry(filterZero(splitInfos[0]));
        ipRegionInfo.setRegion(filterZero(splitInfos[1]));
        ipRegionInfo.setProvince(filterZero(splitInfos[2]));
        ipRegionInfo.setCity(filterZero(splitInfos[3]));
        ipRegionInfo.setIsp(filterZero(splitInfos[4]));
        return ipRegionInfo;
    }

    /**
     * 数据过滤，因为 ip2Region 采用 0 填充的没有数据的字段
     * @param info info
     * @return info
     */
    @Nullable
    private String filterZero(@Nullable String info) {
        // null 或 0 返回 null
        if (info == null || BigDecimal.ZERO.toString().equals(info)) {
            return null;
        }
        return info;
    }

    @Override
    public void destroy() throws Exception {
        if (this.searcher != null) {
            this.searcher.close();
        }
    }
}
