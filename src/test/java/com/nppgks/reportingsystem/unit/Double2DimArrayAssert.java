package com.nppgks.reportingsystem.unit;

import org.assertj.core.api.AbstractAssert;

public class Double2DimArrayAssert extends AbstractAssert<Double2DimArrayAssert, double[][]> {
    protected Double2DimArrayAssert(double[][] doubles) {
        super(doubles, Double2DimArrayAssert.class);
    }

    public static Double2DimArrayAssert assertThat(double[][] actual) {
        return new Double2DimArrayAssert(actual);
    }
    

    public Double2DimArrayAssert isCloseTo(double[][] expected, double offset){
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                if(Math.abs(expected[i][j]-actual[i][j])>offset){
                    failWithMessage("Actual %s is not close to expected %s (offset = %s)",
                            actual[i][j], expected[i][j], offset);
                }
            }

        }
        return this;
    }
}
