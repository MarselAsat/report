package com.nppgks.reportingsystem.unit;

import org.assertj.core.api.AbstractAssert;

public class DoubleArrayAssert extends AbstractAssert<DoubleArrayAssert, double[]> {
    protected DoubleArrayAssert(double[] doubles) {
        super(doubles, DoubleArrayAssert.class);
    }

    public static DoubleArrayAssert assertThat(double[] actual) {
        return new DoubleArrayAssert(actual);
    }


    public DoubleArrayAssert isCloseTo(double[] expected, double offset){
        for (int i = 0; i < expected.length; i++) {
            if(Math.abs(expected[i]-actual[i])>offset){
                failWithMessage("Actual %s is not close to expected %s (offset = %s)",
                        actual[i], expected[i], offset);
            }
        }
        return this;
    }
}
