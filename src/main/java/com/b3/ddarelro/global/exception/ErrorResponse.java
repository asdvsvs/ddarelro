package com.b3.ddarelro.global.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    //private String message;
   private List<String> messages = new ArrayList<>();


    @Builder
    public ErrorResponse(int status, List<String> messages){
        this.status = status;
        //this.message = message;
        this.messages = (messages != null) ? messages : new ArrayList<>();
    }

    public void addMessage(String message){
        this.messages.add(message);
        System.out.println("this.messages = " + this.messages);
    }

}
