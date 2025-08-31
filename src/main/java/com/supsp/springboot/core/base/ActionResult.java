package com.supsp.springboot.core.base;

import com.supsp.springboot.core.interfaces.IActionResult;
import com.supsp.springboot.core.interfaces.IResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@SuperBuilder
@Accessors(chain = true)
// @Schema(name = "ActionResult", description = "操作结果")
public class ActionResult extends Result<ActionData> implements IResult<ActionData>, IActionResult {
    @Serial
    private static final long serialVersionUID = -4782153450254695700L;

    @Override
    public Boolean get_R_() {
        return null;
    }

    public ActionResult(ActionData data) {
        super(data);
    }

    public ActionResult(ActionData data, String message) {
        super(data);
        this.message = message;
    }

    public ActionResult(boolean result, Long lastID, Long ID) {
        super(
                ActionData.builder().build()
                        .setResult(result)
                        .setLastID(lastID)
                        .setID(ID)
        );
    }

    public ActionResult(Long ID, Long lastID) {
        this(ID != null && ID > 0, ID, lastID);
    }

    public ActionResult(Long ID) {
        this(ID != null && ID > 0, ID, null);
    }

    public ActionResult(boolean result) {
        this(result, null, null);
    }

    public ActionResult(boolean result, String message) {
        this(
                ActionData.builder()
                        .result(result)
                        .build(),
                message
        );
    }

    public ActionResult(String message) {
        this(false, message);
    }

    public static ActionResult success(Long ID, Long lastID) {
        return new ActionResult(ID, lastID);
    }

    public static ActionResult success(Integer ID, Integer lastID) {
        return new ActionResult(Long.valueOf(ID), Long.valueOf(lastID));
    }

    public static ActionResult success(boolean result, Long ID, Long lastID) {
        return new ActionResult(result, ID, lastID);
    }

    public static ActionResult success(Long ID) {
        return new ActionResult(ID);
    }


    public static ActionResult success(boolean result) {
        return new ActionResult(result);
    }

    public static ActionResult fail(String message) {
        return new ActionResult(message);
    }

    public static ActionResult fail() {
        return new ActionResult("操作失败,请稍后再试");
    }


    @Override
    public Boolean getResult() {
        if (this.data == null) {
            return null;
        }
        return this.data.getResult();
    }

    @Override
    public void setResult(Boolean result) {
        if (this.data == null) {
            this.setData(new ActionData());
        }
        this.data.setResult(result);
    }

    @Override
    public Long getLastID() {
        if (this.data == null) {
            return null;
        }
        return this.data.getLastID();
    }

    @Override
    public void setLastID(Long lastID) {
        if (this.data == null) {
            this.setData(new ActionData());
        }
        this.data.setLastID(lastID);
    }

    @Override
    public Long getID() {
        if (this.data == null) {
            return null;
        }
        return this.data.getID();
    }

    @Override
    public void setID(Long ID) {
        if (this.data == null) {
            this.setData(new ActionData());
        }
        this.data.setID(ID);
    }
}
