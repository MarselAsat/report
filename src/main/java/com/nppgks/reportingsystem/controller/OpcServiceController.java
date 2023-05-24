package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/opcService")
@AllArgsConstructor
public class OpcServiceController {

    private final OpcServiceRequests opcServiceRequests;
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

    @PostMapping("/readValue")
    @ResponseBody
    public String getValueByName(@RequestBody String tagName){
        return opcServiceRequests.getTagDataFromOpc(tagName);
    }
}
