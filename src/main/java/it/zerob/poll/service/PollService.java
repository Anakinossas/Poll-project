package it.zerob.poll.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private Integer height = 20;
    public String getInsertData(){
        return height.toString();
    }
}
