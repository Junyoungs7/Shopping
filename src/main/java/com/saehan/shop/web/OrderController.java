package com.saehan.shop.web;

import com.saehan.shop.config.auth.dto.SessionUser;
import com.saehan.shop.service.order.OrderService;
import com.saehan.shop.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final HttpSession httpSession;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order (@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String> (sb.toString(), HttpStatus.BAD_REQUEST);
        }



        String email = principal.getName();
        Long orderId;

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        System.out.println("email : " + email);
        System.out.println("sessionUser : " + sessionUser.getEmail());
        System.out.println("orderDto - itemId : " + orderDto.getItemId());
        System.out.println("orderDto - itemCount : " + orderDto.getCount());


        try{
            orderId = orderService.order(orderDto, sessionUser.getEmail());
            System.out.println("orderId : " + orderId);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
