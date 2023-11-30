package com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas;

public class Gost51858 {

    public static String defineOilSymbol(boolean export, double sulfur, double density20, double density15,
                                  double out200, double out300, double water, double salt, double hydroSulfide, double meth){
        byte[] oilTypes = new byte[4];

        // Определение класса нефти
        if (sulfur <= 0.6) {
            oilTypes[0] = 1;
        } else if (sulfur <= 1.8) {
            oilTypes[0] = 2;
        } else if (sulfur <= 3.5) {
            oilTypes[0] = 3;
        } else {
            oilTypes[0] = 4;
        }

        // Определение типа нефти
        if (density20 <= 830) {
            oilTypes[1] = 0;
        } else if (density20 <= 850) {
            oilTypes[1] = 1;
        } else if (density20 <= 870) {
            oilTypes[1] = 2;
        } else if (density20 <= 895) {
            oilTypes[1] = 3;
        } else {
            oilTypes[1] = 4;
        }

        if (density15 <= 853.6 && density15 > 833.7 && oilTypes[1] < 1) {
            oilTypes[1] = 1;
        } else if (density15 <= 873.5 && density15 > 853.6 && oilTypes[1] < 2) {
            oilTypes[1] = 2;
        } else if (density15 <= 898.4 && density15 > 873.5 && oilTypes[1] < 3) {
            oilTypes[1] = 3;
        } else if (density15 > 898.4) {
            oilTypes[1] = 4;
        }

        if (export) {
            if (out200 >= 27 && out200 < 30 && oilTypes[1] < 1) {
                oilTypes[1] = 1;
            } else if (out200 >= 21 && out200 < 27 && oilTypes[1] < 2) {
                oilTypes[1] = 2;
            }

            if (out300 >= 47 && out300 < 52 && oilTypes[1] < 1) {
                oilTypes[1] = 1;
            } else if (out300 >= 42 && out300 < 47 && oilTypes[1] < 2) {
                oilTypes[1] = 2;
            }
        }

        // Определение группы нефти
        if (water <= 0.5) {
            oilTypes[2] = 1;
        } else if (water <= 1) {
            oilTypes[2] = 3;
        }


        if (salt <= 300 && salt > 100 && oilTypes[2] < 2) {
            oilTypes[2] = 2;
        } else if (salt <= 900 && salt > 300 && oilTypes[2] < 3) {
            oilTypes[2] = 3;
        }

        // Определение вида нефти
        if (hydroSulfide <= 20) {
            oilTypes[3] = 1;
        } else if (hydroSulfide <= 100) {
            oilTypes[3] = 2;
        }

        if (meth <= 100 && meth > 40 && oilTypes[3] < 2) {
            oilTypes[3] = 2;
        }

        String e = export ? "э" : "";

        return oilTypes[0] + "." + oilTypes[1] + e + "." + oilTypes[2] + "." + oilTypes[3];
    }
}
