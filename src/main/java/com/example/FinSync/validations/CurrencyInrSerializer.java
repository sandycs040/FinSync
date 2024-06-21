package com.example.FinSync.validations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyInrSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en","IN"));
        String formatCurrnecy = numberFormat.format(value);
        gen.writeString(formatCurrnecy);
    }
}
