package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Service;
import com.nppgks.reportingsystem.util.ArrayParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalculationsController {
    private final MI3622Service MI3622Service;
    private final String MI_3622 = "MI3622";

    @GetMapping("/calc" + MI_3622)
    public String doCalc3622(ModelMap modelMap) {
        List<TagData> tagDataList = MI3622Service.doCalc3622();
        tagDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
            modelMap.put(
                    td.getTagName().getPermanentName(), value);
        });
        return "report_pages/calc3622-report-page";
    }

    @ResponseBody
    @GetMapping("/calc" + MI_3622 + "/save")
    public String saveDataCalc3622() {
        return MI3622Service.saveInDb();
    }
}
