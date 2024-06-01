package com.dailylog.dailylog.controller;

import com.dailylog.dailylog.exceptions.LogNotFoundException;
import com.dailylog.dailylog.model.Log;
import com.dailylog.dailylog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    //TODO: ver de agregar confirmación antes de borrar.

    @DeleteMapping("/{id}")
    public String deleteLog(@PathVariable Long id){
        try {
            logService.deleteLog(id);
            return "Registro borrado correctamente";
        } catch (LogNotFoundException e) {
            return "ID no encontrado";
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLog(@RequestBody Log log, @PathVariable Long id) {
        try {
            logService.updateLog(log, id);
            return ResponseEntity.ok("ID modificado correctamente");
        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID a modificar no encontrado");
        }
    }

    //todo: logica del negocio: calculos de totales por rubro por mes; calculo totales desde-hasta. En el servicio(?)

    //todo: mostrar en un front (Tymeleaf?)

    //todo: Spring Security: loggearse para poder editar

}
