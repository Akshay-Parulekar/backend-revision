package com.example.backend_revision.controller;

import com.example.backend_revision.entity.Employee;
import com.example.backend_revision.repo.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.backend_revision.util.MyUtil.mapper;

@RestController
@RequestMapping("/emp/")
public class EmpController
{
    @Autowired
    EmpRepo empRepo;
    @Autowired
    SimpMessagingTemplate ws;

    @GetMapping("/emp/")
    List<Employee> getUpdates(@RequestBody Map<String, String> payload)
    {
        Long maxTsClient = Long.parseLong(payload.get("ts"));
        return empRepo.findByTsGreaterThan(maxTsClient);
    }

    @PostMapping("/save/")
    Employee save(@RequestBody Map<String, String> payload)
    {
        System.out.println("recieved payload = " + payload.toString());

        String idClient = payload.get("idClient");
        Employee emp = mapper.convertValue(payload, Employee.class);
        emp.setTs(System.nanoTime());

        try
        {
            Employee empSaved = empRepo.save(emp);
            ws.convertAndSend(idClient);
            return  empSaved;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
