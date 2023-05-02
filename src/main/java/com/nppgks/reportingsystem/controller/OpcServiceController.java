package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.service.dbservices.AllTagNamesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/opcService")
@AllArgsConstructor
public class OpcServiceController {

    private final OpcServiceRequests opcServiceRequests;
    private final AllTagNamesService allTagNamesService;

    @GetMapping("/testOpcServerConnection")
    @ResponseBody
    public boolean testConnection(){
        return opcServiceRequests.testOpcServerConnection();
    }

    @GetMapping("/reconnect")
    @ResponseBody
    public boolean reconnect(){
        return opcServiceRequests.reconnectToOpcServer();
    }

    @GetMapping("/readValue/{tagName}")
    @ResponseBody
    public String getValueByName(@PathVariable String tagName){
        return opcServiceRequests.getTagDataFromOpc(tagName);
    }

    @GetMapping("/opcServer")
    public String getOpcServerPage(ModelMap modelMap){
        modelMap.put("tagNames", allTagNamesService.getAllOperativeAndCalculationTagNames());
        return "opc-server";
    }
}
