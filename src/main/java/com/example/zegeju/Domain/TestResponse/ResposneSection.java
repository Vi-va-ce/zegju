package com.example.zegeju.Domain.TestResponse;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class ResposneSection {
    private Map<String,String> answers;
}
