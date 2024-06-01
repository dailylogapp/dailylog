package com.dailylog.dailylog.service;

import com.dailylog.dailylog.exceptions.LogNotFoundException;
import com.dailylog.dailylog.model.Log;
import com.dailylog.dailylog.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.util.Locale.filter;

@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    //CRUD

    public List<Log> getAllLogs(){
        return logRepository.findAll();
    }

    public void addlog(Log log) {
        logRepository.save(log);
    }

    public Log findById(Long id){
        if (logRepository.existsById(id)) {
            return logRepository.findById(id).get();
        } else {
            throw new LogNotFoundException();
        }
    }

    public void deleteLog(Long id) {
        if (logRepository.existsById(id)) {
            logRepository.deleteById(id);
        } else {
            throw new LogNotFoundException();
        }
    }

    public void updateLog(Log log, Long id) {
        if (logRepository.existsById(id)) {
            Log existingLog = logRepository.findById(id).orElseThrow(() -> new LogNotFoundException());
            log.setId(existingLog.getId()); // Setear el ID correcto antes de guardar
            logRepository.save(log);
        } else {
            throw new LogNotFoundException();
        }
    }

    // METODOS DE LA LOGICA DEL NEGOCIO

    // Recibo el mes (validado en el controller) y luego de convertir a stream el listado de logs, fitro y mapeo los del mes para luego sumar sus prices.
    public double getLogsPerMonth(int month) {
        List<Log> allLogs = logRepository.findAll();
        return allLogs.stream()
                .filter(log -> log.getDate().getMonthValue() == month)
                .mapToDouble(Log::getPrice) //Mapea cada log filtrado al valor de su precio
                .sum();
    }
}
