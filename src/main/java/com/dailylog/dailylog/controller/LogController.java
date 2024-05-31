package com.dailylog.dailylog.controller;

import com.dailylog.dailylog.exceptions.LogNotFoundException;
import com.dailylog.dailylog.model.Log;
import com.dailylog.dailylog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    public List<Log> getAllLogs(){
        return logService.getAllLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Log log = logService.findById(id);
            return ResponseEntity.ok(log);
        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID inexistente");
        }
    }

    @PostMapping("/addlog")
    public void addLog(@RequestBody Log log){
        logService.addlog(log);

    }

    @DeleteMapping("/{id}")
    public String deleteLog(@PathVariable Long id){
        try {
            logService.deleteLog(id);
            return "Registro borrado correctamente";
        } catch (LogNotFoundException e) {
            return "ID no encontrado";
        }
    }

}
