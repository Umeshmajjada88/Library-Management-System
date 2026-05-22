package com.example.LibraryManagement.Service;


import com.example.LibraryManagement.DTO.OrderDTO;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Entity.Order;
import com.example.LibraryManagement.Exception.ResourceNotFoundException;
import com.example.LibraryManagement.Repository.BookRepository;
import com.example.LibraryManagement.Repository.MemberRepository;
import com.example.LibraryManagement.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// Full implementation of two EMPTY classes from your original project:
//   PlaceOrder.java → placeOrder()
//   ViewOrders.java → getOrdersByMember(), getAllOrders()
@Service
public class OrderService {

    @Autowired private OrderRepository  orderRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BookRepository   bookRepository;

    // PlaceOrder.java
    public OrderDTO placeOrder(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        Order saved = orderRepository.save(new Order(member, book, LocalDate.now()));
        return toDTO(saved);
    }

    // ViewOrders.java — member's own orders
    public List<OrderDTO> getOrdersByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + memberId));
        return orderRepository.findByMemberOrderByOrderDateDesc(member)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ViewOrders.java — admin sees all orders
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getPendingOrders() {
        return orderRepository.findByStatus(Order.OrderStatus.PENDING)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Admin: fulfil or cancel an order
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        return toDTO(orderRepository.save(order));
    }

    private OrderDTO toDTO(Order o) {
        return new OrderDTO(
                o.getId(),
                o.getMember().getId(), o.getMember().getName(),
                o.getBook().getId(),   o.getBook().getName(),
                o.getOrderDate(), o.getStatus().name()
        );
    }
}
