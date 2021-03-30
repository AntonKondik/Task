package com.example.demo.util;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ContextHolder {
    private String userInfo;
}
