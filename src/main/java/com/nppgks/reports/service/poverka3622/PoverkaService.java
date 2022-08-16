package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.ManualTagNameService;
import com.nppgks.reports.service.PoverkaType;
import com.nppgks.reports.service.poverka3622.data.FinalData;
import com.nppgks.reports.service.poverka3622.data.InitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nppgks.reports.opc.ArrayParser.to2dimArray;
import static com.nppgks.reports.service.poverka3622.data.FinalData.Fields.*;
import static com.nppgks.reports.service.poverka3622.data.InitialData.Fields.*;
import static com.nppgks.reports.service.poverka3622.data.InitialData.Fields.ZS;
import static java.lang.Double.parseDouble;

@Service
@RequiredArgsConstructor
public class PoverkaService {

    private final OpcRequests opcRequests;
    private final ManualTagNameService manualTagNameService;

    public void doPoverka3622(){
        List<TagNameForOpc> initialTagNamesForOpc = manualTagNameService.getTagNamesByInitialAndType(true, PoverkaType._3622.name());
        List<String> initialTagNamesStr = initialTagNamesForOpc
                .stream()
                .map(TagNameForOpc::name)
                .toList();

        Map<String, String> initialTagNamesMap = initialTagNamesForOpc.stream()
                .collect(Collectors.toMap(TagNameForOpc::name, TagNameForOpc::permanentName));

        Map<String, String> initialDataFromOpc = opcRequests.getTagDataFromOpc(initialTagNamesStr);
        InitialData initialData = convertMapToInitialData(initialDataFromOpc, initialTagNamesMap);
        PoverkaRunner poverkaRunner = new PoverkaRunner(initialData);
        FinalData finalData = poverkaRunner.run();
        Map<String, Object> finalDataForOpc = convertFinalDataToMap(finalData, initialTagNamesMap);
        opcRequests.sendTagDataToOpc(finalDataForOpc);

    }

    private Map<String, Object> convertFinalDataToMap(FinalData finalData,Map<String, String> tagNamesMap) {
        Map<String, Object> map = new HashMap<>();
        map.put(tagNamesMap.get(delta_j), finalData.getDelta_j());
        map.put(tagNamesMap.get(f_ij), finalData.getF_ij());
        map.put(tagNamesMap.get(M_e_ij), finalData.getM_e_ij());
        map.put(tagNamesMap.get(K_ij), finalData.getK_ij());
        map.put(tagNamesMap.get(MF_ij), finalData.getMF_ij());
        map.put(tagNamesMap.get(Q_j), finalData.getQ_j());
        map.put(tagNamesMap.get(f_j), finalData.getF_j());
        map.put(tagNamesMap.get(K_j), finalData.getK_j());
        map.put(tagNamesMap.get(MF_j), finalData.getMF_j());
        map.put(tagNamesMap.get(S_j), finalData.getS_j());
        map.put(tagNamesMap.get(S_0j), finalData.getS_0j());
        map.put(tagNamesMap.get(t_095), finalData.getT_095());
        map.put(tagNamesMap.get(eps_j), finalData.getEps_j());
        map.put(tagNamesMap.get(theta_zj), finalData.getTheta_zj());
        map.put(tagNamesMap.get(theta_sigma_j), finalData.getTheta_sigma_j());
        map.put(tagNamesMap.get(S_theta_j), finalData.getS_theta_j());
        map.put(tagNamesMap.get(S_sigma_j), finalData.getS_sigma_j());
        map.put(tagNamesMap.get(t_sigma_j), finalData.getT_sigma_j());
        map.put(tagNamesMap.get(Q_min), finalData.getQ_min());
        map.put(tagNamesMap.get(Q_max), finalData.getQ_max());
        map.put(tagNamesMap.get(eps_PDk), finalData.getEps_PDk());
        map.put(tagNamesMap.get(S_PDk), finalData.getS_PDk());
        map.put(tagNamesMap.get(theta_PDk), finalData.getTheta_PDk());
        map.put(tagNamesMap.get(theta_PDz), finalData.getTheta_PDz());
        map.put(tagNamesMap.get(theta_sigma_PDk), finalData.getTheta_sigma_PDk());
        map.put(tagNamesMap.get(S_theta_PDk), finalData.getS_theta_PDk());
        map.put(tagNamesMap.get(S_sigma_PDk), finalData.getS_sigma_PDk());
        map.put(tagNamesMap.get(t_sigma_PDk), finalData.getT_sigma_PDk());
        map.put(tagNamesMap.get(delta_PDk), finalData.getDelta_PDk());

        return map;
    }

    private InitialData convertMapToInitialData(Map<String, String> dataFromOpc, Map<String, String> tagNamesMap){
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
