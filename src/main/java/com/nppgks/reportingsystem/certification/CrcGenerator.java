package com.nppgks.reportingsystem.certification;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.zip.*;

@Slf4j
public class CrcGenerator {

    public static String fileName = "BOOT-INF/classes/com/nppgks/reportingsystem/certification/CertificationAlgorithms.class";

    @SneakyThrows
    public static long getCertificationAlgorithmsCrc(String appVersion) {
        long crc = -1;
        try {
            String path = new java.io.File(".").getCanonicalPath() + "\\reporting_system-"+appVersion+".jar";
            ZipFile file = new ZipFile(path);
            Enumeration<? extends ZipEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().equals(fileName)) {
                    crc = entry.getCrc();
                }
            }
            file.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return crc;
    }
}
