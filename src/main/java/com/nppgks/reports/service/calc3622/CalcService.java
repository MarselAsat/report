package com.nppgks.reports.service.calc3622;

import com.nppgks.reports.dto.calc.CalcTagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.constants.CalcMethod;
import com.nppgks.reports.service.db_services.calculation.CalcTagNameService;
import com.nppgks.reports.service.calc3622.data.DataConverter;
import com.nppgks.reports.service.calc3622.data.FinalData;
import com.nppgks.reports.service.calc3622.data.InitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalcService {

    private final OpcRequests opcRequests;
    private final CalcTagNameService calcTagNameService;

    private final Calc3622InDbService calc3622InDbService;

    public void doCalc3622() {
        List<CalcTagNameForOpc> initialTagNames = calcTagNameService.getTagNamesByInitialAndType(true, CalcMethod.MI_3622.name());
        Map<String, String> initialTagNamesMap = createTagNamesMap(initialTagNames);

        List<String> initialTagNamesForOpc = initialTagNames
                .stream()
                .map(CalcTagNameForOpc::name)
                .toList();

        Map<String, String> initialDataFromOpc = opcRequests.getTagDataFromOpc(initialTagNamesForOpc);

        DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagNamesMap);

        InitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagNamesMap);
        CalcRunner calcRunner = new CalcRunner(initialData);
        FinalData finalData = calcRunner.run();

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
        calc3622InDbService.setInitialDataFromOpc(initialDataFromOpc);
        calc3622InDbService.setInitialTagNames(initialTagNames);
        calc3622InDbService.setFinalTagNames(finalTagNames);
        calc3622InDbService.setFinalDataForOpc(finalDataForOpc);
    }

    private Map<String, String> createTagNamesMap(List<CalcTagNameForOpc> tagNamesForOpc){
        return tagNamesForOpc.stream()
                .collect(Collectors.toMap(CalcTagNameForOpc::permanentName, CalcTagNameForOpc::name));
    }

    public void saveInDb() {
        calc3622InDbService.saveCalculations();
    }
}
