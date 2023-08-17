package com.nppgks.reportingsystem.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DoubleArraySerializer extends JsonSerializer<double[]> {
    @Override
    public void serialize(double[] doubleArray, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(20);
        int length = doubleArray.length;
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            stringBuilder.append(df.format(doubleArray[i]));
            if (i != length - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        jsonGenerator.writeString(stringBuilder.toString());
    }
}
