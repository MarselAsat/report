package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.ManualTagNameService;
import com.nppgks.reports.service.PoverkaType;
import com.nppgks.reports.service.poverka3622.data.FinalData;
import com.nppgks.reports.service.poverka3622.data.InitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nppgks.reports.opc.ArrayParser.to2dimArray;
import static com.nppgks.reports.service.poverka3622.data.InitialData.Fields.*;
import static com.nppgks.reports.service.poverka3622.data.InitialData.Fields.ZS;
import static java.lang.Double.parseDouble;

@Service
@RequiredArgsConstructor
public class PoverkaService {

    private final OpcRequests opcRequests;
    private final ManualTagNameService manualTagNameService;

    public void doPoverka3622(){
        List<TagNameForOpc> tagNamesForOpc = manualTagNameService.getTagNamesByInitialAndType(true, PoverkaType._3622.name());
        List<String> tagNamesStr = tagNamesForOpc
                .stream()
                .map(TagNameForOpc::name)
                .toList();

        Map<String, String> tagNamesMap = tagNamesForOpc.stream()
                .collect(Collectors.toMap(TagNameForOpc::name, TagNameForOpc::permanentName));
        Map<String, String> initialDataFromOpc = opcRequests.getTagDataFromOpc(tagNamesStr);
        InitialData initialData = opcDataToInitialData(initialDataFromOpc, tagNamesMap);
        PoverkaRunner poverkaRunner = new PoverkaRunner(initialData);
        FinalData finalData = poverkaRunner.run();

    }

    private InitialData opcDataToInitialData(Map<String, String> dataFromOpc, Map<String, String> tagNamesMap){
        InitialData initialData = new InitialData();
        initialData.setF_p_max(parseDouble(dataFromOpc.get(tagNamesMap.get(f_p_max))));
        initialData.setK_e_ij(to2dimArray(dataFromOpc.get(tagNamesMap.get(K_e_ij))));
        initialData.setQ_ij(to2dimArray(dataFromOpc.get(tagNamesMap.get(Q_ij))));
        initialData.setN_e_ij(to2dimArray(dataFromOpc.get(tagNamesMap.get(N_e_ij))));
        initialData.setN_p_ij(to2dimArray(dataFromOpc.get(tagNamesMap.get(N_p_ij))));
        initialData.setT_ij(to2dimArray(dataFromOpc.get(tagNamesMap.get(T_ij))));
        initialData.setM_ij(to2dimArray(dataFromOpc.get(tagNamesMap.get(M_ij))));
        initialData.setQ_p_max(parseDouble(dataFromOpc.get(tagNamesMap.get(Q_p_max))));
        initialData.setMF_p(parseDouble(dataFromOpc.get(tagNamesMap.get(MF_p))));
        initialData.setTheta_e(parseDouble(dataFromOpc.get(tagNamesMap.get(theta_e))));
        initialData.setTheta_N(parseDouble(dataFromOpc.get(tagNamesMap.get(theta_N))));
        initialData.setTheta_p(parseDouble(dataFromOpc.get(tagNamesMap.get(theta_p))));
        initialData.setTheta_PDp(parseDouble(dataFromOpc.get(tagNamesMap.get(theta_PDp))));
        initialData.setTheta_PDt(parseDouble(dataFromOpc.get(tagNamesMap.get(theta_PDt))));
        initialData.setTheta_t(parseDouble(dataFromOpc.get(tagNamesMap.get(theta_t))));
        initialData.setZS(parseDouble(dataFromOpc.get(tagNamesMap.get(ZS))));
        return initialData;
    }
}
