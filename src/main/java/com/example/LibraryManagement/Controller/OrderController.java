package com.example.LibraryManagement.Controller;


import com.example.LibraryManagement.DTO.OrderDTO;
import com.example.LibraryManagement.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// POST /api/orders                    → PlaceOrder.java
// GET  /api/orders/member/{memberId}  → ViewOrders.java (member)
// GET  /api/orders                    → ViewOrders.java (admin: all)
// GET  /api/orders/pending            → admin: pending only
// PUT  /api/orders/{id}/status        → admin: fulfil or cancel
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(
            @RequestParam Long memberId,
            @RequestParam Long bookId) {
        return new ResponseEntity<>(orderService.placeOrder(memberId, bookId), HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<OrderDTO>> getMyOrders(@PathVariable Long memberId) {
        return ResponseEntity.ok(orderService.getOrdersByMember(memberId));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
