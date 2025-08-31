package com.supsp.springboot.core.service;

public interface IIdGeneratorService {

    Long id(
            String module,
            String name,
            Integer len,
            Integer prefix,
            String type
    );

    Long id(
            String module,
            String name,
            Integer len,
            Integer prefix
    );

    Long id(String module, String name, String type);

    Long id(String module, String name);

    Long id(Integer len,Integer prefix);

    Long id();
}
