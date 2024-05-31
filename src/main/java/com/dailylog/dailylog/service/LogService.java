package com.dailylog.dailylog.service;

import com.dailylog.dailylog.exceptions.LogNotFoundException;
import com.dailylog.dailylog.model.Log;
import com.dailylog.dailylog.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        //Busco el id
        Optional<Log> idToDelete = logRepository.findById(id);

        //Si existe, borrarlo, sino mensaje "no existe id"
        if (idToDelete.isPresent()) {
            logRepository.deleteById(id);
        } else {
            throw new LogNotFoundException("El id " + id + " no fue encontrado");
        }
    }
}
