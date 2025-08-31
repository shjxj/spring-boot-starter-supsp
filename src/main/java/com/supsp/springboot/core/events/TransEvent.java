package com.supsp.springboot.core.events;

import com.supsp.springboot.core.enums.StatementType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
public class TransEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 5102370220539039279L;

    /**
     * 对应 Statement 类型
     */
    private StatementType statementType;

    /**
     * sql
     */
    private String sql;

    /**
     * 相关 table
     */
    private Set<String> tables;

    public TransEvent(Object source) {
        super(source);
    }
}
