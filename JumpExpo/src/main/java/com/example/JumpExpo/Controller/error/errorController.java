package com.example.JumpExpo.Controller.error;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

@Controller
public class errorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error404";
            }
            // You can add more conditions for other HTTP status codes if needed
        }

        return "error500";
    }


    public String getErrorPath() {
        return null;
    }
}

