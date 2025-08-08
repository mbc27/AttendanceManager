package com.rabbiter.am.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EnumService {
    @Value("${attendance.on-time}")
    private String onTime;

    @Value("${attendance.off-time}")
    private String offTime;

    public String getAttendanceTime() {
        if (onTime == null || offTime == null) {
            System.err.println("请检查application.yaml文件的attendance配置！配置未存在。");
            return "08:30-17:30";
        }
        String pattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(onTime);
        Matcher m2 = r.matcher(offTime);
        // 检查字符串是否与正则表达式匹配
        if (!m.matches() || !m2.matches()) {
            System.err.println("请检查application.yaml文件的attendance配置！存在非法时间字符串。");
            return "08:30-17:30";
        }
        return String.format("%s-%s", onTime, offTime);
    }
}
