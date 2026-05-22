package com.example.LibraryManagement.Repository;



import com.example.LibraryManagement.Entity.Order;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMember(Member member);

    List<Order> findByBook(Book book);

    List<Order> findByStatus(Order.OrderStatus status);

    List<Order> findByMemberOrderByOrderDateDesc(Member member);
}
