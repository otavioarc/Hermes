package com.hermes.demo.controllers;

import com.hermes.core.IHermes;
import com.hermes.demo.domain.commands.GetDateCommand;
import com.hermes.demo.domain.commands.GreetingCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DemoController {

    private final IHermes hermes;

    @Autowired
    public DemoController(IHermes hermes) {
        this.hermes = hermes;
    }

    @GetMapping("/date")
    public ResponseEntity<Date> getDate(GetDateCommand command) {
        Date date = (Date) hermes.send(command);

        return new ResponseEntity<>(date, HttpStatus.OK);
    }

    @GetMapping("/greeting")
    public ResponseEntity<String> getGreeting(GreetingCommand command) {
        String greeting = (String) hermes.send(command);

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
