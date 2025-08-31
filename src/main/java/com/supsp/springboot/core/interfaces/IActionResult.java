package com.supsp.springboot.core.interfaces;

public interface IActionResult {
    Boolean getResult();

    void setResult(Boolean result);

    Long getLastID();

    void setLastID(Long lastID);

    Long getID();

    void setID(Long ID);
}
