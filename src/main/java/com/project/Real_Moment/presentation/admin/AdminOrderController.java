package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminOrderService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/admin/orderList")
    public ResponseEntity<OrderDto.OrderListPaging> getOrderList(@RequestParam(name = "itemName", required = false) String itemName,
                                                           @RequestParam(name = "loginId", required = false) String loginId,
                                                           @RequestParam(name = "merchantUid", required = false) String merchantUid,
                                                           @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                                           @RequestParam(name = "lastDate", required = false) LocalDate lastDate,
                                                           @RequestParam(name = "status", required = false) String status,
                                                           @RequestParam(name = "nowPage", required = false, defaultValue = "1") int nowPage) {
        CondDto.AdminOrderListCond condDto = new CondDto.AdminOrderListCond(itemName, loginId, merchantUid, startDate, lastDate, status, nowPage);
        return ResponseEntity.ok().body(adminOrderService.getOrderList(condDto));
    }

    @GetMapping("/admin/order")
    public ResponseEntity<OrderDto.OrderDet> getOrderDet(@RequestParam("orderId") Long orderId) {
        return ResponseEntity.ok().body(adminOrderService.getOrderDet(orderId));
    }
}
