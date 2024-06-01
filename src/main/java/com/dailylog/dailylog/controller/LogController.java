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
import java.util.List;

@RestController
@RequestMapping("/logs")
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
    public String addLog(@RequestBody Log log){
        logService.addlog(log);
        return "Registro agregado correctamente";
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
    @GetMapping("/totalMesCategoria/{month}/{category}")
    public ResponseEntity<?> getLogsPerMonthPerCategory(@PathVariable int month, @PathVariable Category category) {
        if (month < 1 || month > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mes inválido. Debe estar entre 1 y 12.");
        }

        double total = logService.getLogsPerMonthPerCategory(month, category);
        return ResponseEntity.ok(total);
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




    // Total desde-hasta por categoria


    // Total desde-hasta por medio de pago




    //todo: logica del negocio: calculos de totales por rubro por mes; calculo totales desde-hasta. En el servicio(?)

    //todo: mostrar en un front (Tymeleaf?)

    //todo: Spring Security: loggearse para poder editar

}
