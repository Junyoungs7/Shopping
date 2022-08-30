package com.saehan.shop.web;

import com.saehan.shop.config.auth.dto.SessionUser;
import com.saehan.shop.service.order.OrderService;
import com.saehan.shop.web.dto.OrderDto;
import com.saehan.shop.web.dto.OrderHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

        Long orderId;

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");


        try{
            orderId = orderService.order(orderDto, sessionUser.getEmail());
            System.out.println("orderId : " + orderId);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "orders/{page}"})
    public String orderHist(@PathVariable("page")Optional<Integer> page, Model model){

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);

        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(sessionUser.getEmail(), pageable);

        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "order/orderHist";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(!orderService.validateOrder(orderId, sessionUser.getEmail())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
