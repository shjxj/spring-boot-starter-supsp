package com.supsp.springboot.core.service;


import com.supsp.springboot.core.exceptions.ModelException;

public interface ISensitiveCryptService {
    <T> T encrypt(T object) throws ModelException;

    <T> T decrypt(T object) throws ModelException;

    String encryptKey();

    String encryptString(String data);

    String decryptString(String data);
}
