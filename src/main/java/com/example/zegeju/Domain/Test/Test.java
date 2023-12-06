package com.example.zegeju.Domain.Test;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Test {

    private String test_id;//diagnostic_test, practice_test1
    private String user_id;
    private List<Section> sections;///questions under each sections


//    private Date date;
}
