package com.giahuy.demo.Service;

import com.giahuy.demo.entity.*;
import com.giahuy.demo.repository.OrderDetailRepository;
import com.giahuy.demo.repository.OrderRepository;
import com.giahuy.demo.repository.ProductRepository;
import com.giahuy.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptService {

    UserRepository userRepo;
    ProductRepository productRepo;
    OrderRepository orderRepo;
    OrderDetailRepository orderDetailRepo;


    @Transactional
    public boolean addReceipt(Map<String, Cart> carts, String vnp_TxnRef)
    {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> optionalUser = userRepo.findByUsername(username);
            User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

            for (Cart cart : carts.values()) {
                Optional<Product> productOptional = productRepo.findById(String.valueOf(cart.getId()));
                if (!productOptional.isPresent()) {
                    throw new RuntimeException("Product not found for ID: " + cart.getId());
                }
            }

            Date date = new Date();
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())  // Chuyển đổi theo múi giờ hệ thống
                    .toLocalDateTime();

            Order order = new Order();
            order.setId(Integer.valueOf(vnp_TxnRef));
            order.setUser_id(user);
            order.setOrderDate(localDateTime);
            orderRepo.save(order);

            for(Cart c : carts.values()){
                OrderDetails details = new OrderDetails();
                Optional<Product> productOptional = productRepo.findById(String.valueOf(c.getId()));
                if (productOptional.isPresent()) {
                    details.setProduct(productOptional.get());
                } else {
                    throw new RuntimeException("Product not found for ID: " + c.getId());
                }
                details.setOrder(order);
                details.setQuantity(c.getQuantity());
                details.setUnitPrice(c.getPrice());
                orderDetailRepo.save(details);
            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Integer calculateTotalAmount(Map<String, Cart> carts) {
        Integer totalAmount = 0;
        for (Cart cart : carts.values()) {
            totalAmount += (cart.getPrice() * cart.getQuantity());
        }
        return totalAmount;
    }

}
