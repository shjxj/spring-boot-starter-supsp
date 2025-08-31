package com.supsp.springboot.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.supsp.springboot.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum QueryType implements BaseEnum {
    custom("custom", ""),
    allEq("allEq", ""),
    eq("eq", ""),
    ne("ne", ""),
    gt("gt", ""),
    ge("ge", ""),
    lt("lt", ""),
    le("le", ""),
    between("between", ""),
    notBetween("notBetween", ""),
    like("like", ""),
    notLike("notLike", ""),
    likeLeft("likeLeft", ""),
    notLikeLeft("notLikeLeft", ""),
    likeRight("likeRight", ""),
    notLikeRight("notLikeRight", ""),
    isNull("isNull", ""),
    isNotNull("isNotNull", ""),
    in("in", ""),
    notIn("notIn", ""),
    inSql("inSql", ""),
    notInSql("notInSql", ""),
    exists("exists", ""),
    notExists("notExists", ""),
    sensitive("sensitive", ""),;

    @EnumValue
    private final String code;

    private final String content;

    private static final Map<String, QueryType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (QueryType val : EnumSet.allOf(QueryType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static QueryType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
