package com.supsp.springboot.core.interfaces;

public interface IAccountShop extends IData {


    String getArea();

    void setArea(String area);

    Long getCity();

    void setCity(Long city);

    Long getCommunity();

    void setCommunity(Long community);

    Long getDistrict();

    void setDistrict(Long district);

    Long getOrgId();

    void setOrgId(Long orgId);

    Long getParentOrgId();

    void setParentOrgId(Long parentOrgId);

    Long getParentStoreId();

    void setParentStoreId(Long parentStoreId);

    Long getProvince();

    void setProvince(Long province);

    Long getShopId();

    void setShopId(Long shopId);

    String getShopName();

    void setShopName(String shopName);

    String getShopType();

    void setShopType(String shopType);

    String getShopTypeValue();

    void setShopTypeValue(String shopTypeValue);

    Long getStoreId();

    void setStoreId(Long storeId);

    Long getStreet();

    void setStreet(Long street);

    Long getTopOrgId();

    void setTopOrgId(Long topOrgId);

    Long getTopStoreId();

    void setTopStoreId(Long topStoreId);
}
