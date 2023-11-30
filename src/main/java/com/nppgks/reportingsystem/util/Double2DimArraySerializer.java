package com.nppgks.reportingsystem.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Double2DimArraySerializer extends JsonSerializer<double[][]> {
    @Override
    public void serialize(double[][] double2DimArray, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(20);
        int rowsCount = double2DimArray.length;
        int columnsCount = double2DimArray[0].length;
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < rowsCount; i++) {
            stringBuilder.append("[");
            for (int j = 0; j < columnsCount; j++) {
                stringBuilder.append(df.format(double2DimArray[i][j]));
                if (j != columnsCount - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("]");
            if (i != rowsCount - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        jsonGenerator.writeString(stringBuilder.toString());
    }
}
