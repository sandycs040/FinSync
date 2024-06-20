package com.example.FinSync.util;

import org.jasypt.util.text.BasicTextEncryptor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PasswordEncryptor implements AttributeConverter<String, String> {

    private final BasicTextEncryptor textEncryptor;

    public PasswordEncryptor() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray("${security.jwt.secret-key}".toCharArray()); // Use a secure way to manage this key
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return textEncryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return textEncryptor.decrypt(dbData);
    }
}
