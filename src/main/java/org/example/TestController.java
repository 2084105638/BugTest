package org.example;



import org.example.netty.ChannelContextUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Sylphy
 * @Description
 * @create 2025/7/30
 */
@RestController
@RequestMapping("/test")
@Validated
public class TestController {
    @Resource
    private ChannelContextUtils channelContextUtils;
    
    @RequestMapping("/changePassword")
    public void changePassword() {
        String userId = "U123";
        channelContextUtils.removeContext(userId);
    }
    
    @RequestMapping("/logout")
    public void logout() {
        String userId = "U123";
        channelContextUtils.removeContext(userId);
    }
}
