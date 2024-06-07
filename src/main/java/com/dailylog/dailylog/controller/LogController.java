package com.dailylog.dailylog.controller;

import com.dailylog.dailylog.exceptions.LogNotFoundException;
import com.dailylog.dailylog.model.Category;
import com.dailylog.dailylog.model.Log;
import com.dailylog.dailylog.model.PaymentMethod;
import com.dailylog.dailylog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "http://localhost:3000")
public class LogController {

    @Autowired
    private LogService logService;

    //CRUD

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
    public ResponseEntity<String> addLog(@RequestBody Log log){
        logService.addlog(log);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro agregado correctamente");
    }

    //TODO: ver de agregar confirmación antes de borrar.

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        try {
            logService.deleteLog(id);
            return ResponseEntity.ok("Registro borrado correctamente");
        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID no encontrado");
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

    // METODOS DE LA LOGICA DEL NEGOCIO

    //Total por mes
        //Recibo por url el N° del mes (1 a 12) y hago su validacion
    @GetMapping("/totalMes/{month}")
    public ResponseEntity<?> getLogsPerMonth(@PathVariable int month) {
        if (month < 1 || month > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mes inválido. Debe estar entre 1 y 12.");
        }
        double total = logService.getLogsPerMonth(month);
        return ResponseEntity.ok(total);
    }

    // Total por categoria por mes
        //Recibo por url el N° del mes y la categoria del enum y hago validacion del mes

    /*
   METODO ANTERIOR, REEMPLAZADO POR EL QUE UTILIZA UN SOLO PARAMETRO
    @GetMapping("/totalMesCategoria/{month}/{category}")
    public ResponseEntity<?> getLogsPerMonthPerCategory(@PathVariable int month, @PathVariable Category category) {
        if (month < 1 || month > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mes inválido. Debe estar entre 1 y 12.");
        }
        double total = logService.getLogsPerMonthPerCategory(month, category);
        return ResponseEntity.ok(total);
    }*/

    // Total por categoria
    // Recibo solo la categoria
    @GetMapping("/totalsByMonthAndCategory/{category}")
    public ResponseEntity<?> getTotalsByMonthAndCategory(@PathVariable Category category) {
        Map<Integer, Double> totalsByMonth = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            double total = logService.getLogsPerMonthPerCategory(month, category);
            totalsByMonth.put(month, total);
        }
        return ResponseEntity.ok(totalsByMonth);
    }


    // Total por medio de pago
        //Recibo por url el medio de pago del enum
    @GetMapping("/totalFormaDePago/{paymentMethod}")
    public ResponseEntity<?> getLogsPerPaymentMethod(@PathVariable PaymentMethod paymentMethod) {
        double total = logService.getLogsPerPaymentMethod(paymentMethod);
        return ResponseEntity.ok(total);
    }

    // Total por medio de pago y mes
    //Recibo por url el N° del mes y el medio de pago del enum y hago validacion del mes
    @GetMapping("/totalMesFormaDePago/{month}/{paymentMethod}")
    public ResponseEntity<?> getLogsPerMonthPerPaymentMethod(@PathVariable int month, @PathVariable PaymentMethod paymentMethod) {
        if (month < 1 || month > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mes inválido. Debe estar entre 1 y 12.");
        }

        double total = logService.getLogsPerMonthPerPaymentMethod(month, paymentMethod);
        return ResponseEntity.ok(total);
    }

   

    //todo: Spring Security: loggearse para poder editar

    //todo: mostrar en un front (Thymeleaf?)



    //todo: tests de los metodos de la logica del negocio (y del CRUD?)


}
