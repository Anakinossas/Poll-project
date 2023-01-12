package it.zerob.poll.controller;

import it.zerob.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bo")
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping("/dataSurvey")
    public ResponseEntity getInsertData(){
        return new ResponseEntity(pollService.getInsertData(), HttpStatus.OK);
    }
}
