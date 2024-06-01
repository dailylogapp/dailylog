package com.dailylog.dailylog.service;

import com.dailylog.dailylog.exceptions.LogNotFoundException;
import com.dailylog.dailylog.model.Log;
import com.dailylog.dailylog.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

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
            throw new LogNotFoundException("ID inexistente");
        }
    }

    public void deleteLog(Long id) {
        if (logRepository.existsById(id)) {
            logRepository.deleteById(id);
        } else {
            throw new LogNotFoundException("El id " + id + " no fue encontrado");
        }
    }

    public void updateLog(Log log, Long id) {
        if (logRepository.existsById(id)) {
            Log existingLog = logRepository.findById(id).orElseThrow(() -> new LogNotFoundException("ID a modificar no encontrado"));
            log.setId(existingLog.getId()); // Set the correct ID before saving
            logRepository.save(log);
        } else {
            throw new LogNotFoundException("ID a modificar no encontrado");
        }
    }
}
