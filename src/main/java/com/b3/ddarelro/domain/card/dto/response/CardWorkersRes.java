//package com.b3.ddarelro.domain.card.dto.response;
//
//import com.b3.ddarelro.domain.worker.entity.*;
//import lombok.*;
//
//@Builder
//public record CardWorkersRes(
//    Long id,
//    String username
//) {
//
//    public CardWorkersRes formWith(Worker worker) {
//        return CardWorkersRes.builder()
//            .id(worker.getId())
//            .username(worker.getUser().getUsername())
//            .build();
//    }
//}
