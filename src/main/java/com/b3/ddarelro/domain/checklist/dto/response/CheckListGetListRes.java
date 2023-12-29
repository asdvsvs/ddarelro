package com.b3.ddarelro.domain.checklist.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckListGetListRes {

    private final List<CheckListGetDetailRes> checkList;
    private final Double donePer;

    @Builder
    public CheckListGetListRes(List<CheckListGetDetailRes> checkList) {
        this.checkList = checkList;
        long completedCount = checkList.stream().filter(c -> c.completed()).count();
        this.donePer = (completedCount / (double) checkList.size()) * 100;
    }
}
