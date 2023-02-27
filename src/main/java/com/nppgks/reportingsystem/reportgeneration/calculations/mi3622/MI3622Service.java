package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.dto.calc.CalcTagNameForOpc;
import com.nppgks.reportingsystem.opc.OpcRequests;
import com.nppgks.reportingsystem.constants.CalcMethod;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagNameService;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.FinalData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.InitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MI3622Service {

    private final OpcRequests opcRequests;
    private final CalcTagNameService calcTagNameService;

    private final MI3622InDbService MI3622InDbService;

    public void doCalc3622() {
        List<CalcTagNameForOpc> initialTagNames = calcTagNameService.getTagNamesByInitialAndType(true, CalcMethod.MI_3622.name());
        Map<String, String> initialTagNamesMap = createTagNamesMap(initialTagNames);

        List<String> initialTagNamesForOpc = initialTagNames
                .stream()
                .map(CalcTagNameForOpc::name)
                .toList();

        Map<String, String> initialDataFromOpc = opcRequests.getTagDataFromOpc(initialTagNamesForOpc);

        DataConverter.putInOrder2DArraysInOpcData(
                initialDataFromOpc,
                initialTagNamesMap.get("pointsCount"),
                initialTagNamesMap.get("measureCount"));

        InitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagNamesMap);
        MI3622Runner MI3622Runner = new MI3622Runner(initialData);
        FinalData finalData = MI3622Runner.run();

        List<CalcTagNameForOpc> finalTagNames = calcTagNameService.getTagNamesByInitialAndType(false, CalcMethod.MI_3622.name());

        Map<String, String> finalTagNamesMap = createTagNamesMap(finalTagNames);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(finalData, finalTagNamesMap);
        opcRequests.sendTagDataToOpc(finalDataForOpc);

        prepareAllDataForDB(initialTagNames, initialDataFromOpc, finalTagNames, finalDataForOpc);
    }

    private void prepareAllDataForDB(List<CalcTagNameForOpc> initialTagNames,
                                     Map<String, String> initialDataFromOpc,
                                     List<CalcTagNameForOpc> finalTagNames,
                                     Map<String, Object> finalDataForOpc) {
        MI3622InDbService.setInitialDataFromOpc(initialDataFromOpc);
        MI3622InDbService.setInitialTagNames(initialTagNames);
        MI3622InDbService.setFinalTagNames(finalTagNames);
        MI3622InDbService.setFinalDataForOpc(finalDataForOpc);
    }

    private Map<String, String> createTagNamesMap(List<CalcTagNameForOpc> tagNamesForOpc){
        return tagNamesForOpc.stream()
                .collect(Collectors.toMap(CalcTagNameForOpc::permanentName, CalcTagNameForOpc::name));
    }

    public void saveInDb() {
        MI3622InDbService.saveCalculations();
    }
}
