package com.supsp.springboot.core.model;

import com.supsp.springboot.core.interfaces.IData;

public interface IEntity extends IData {

    Long getCurrentTimestamp();

    void setCurrentTimestamp(Long currentTimestamp);

}
