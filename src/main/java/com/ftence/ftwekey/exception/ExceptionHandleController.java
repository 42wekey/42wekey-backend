package com.ftence.ftwekey.exception;

import com.ftence.ftwekey.exception.login.ValidFailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exception")
public class ExceptionHandleController {

    @GetMapping("/login")
    public void LoginException() {

        throw new ValidFailException();
    }
}
