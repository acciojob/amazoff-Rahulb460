package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }
    public void addDp(String partnerId) {
        orderRepository.addDp(partnerId);
    }
    public void addPair(String orderId, String partnerId) {
        orderRepository.addPair(orderId, partnerId);
    }
    public Order findOrder (String orderId) {
        return orderRepository.findOrder(orderId);
    }
    public DeliveryPartner findPartner(String partnerId) {
        return orderRepository.findPartner(partnerId);
    }
    public int getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }
    public int getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }
    public void deletePartnerById(String partnerID) {
        orderRepository.deletePartnerById(partnerID);
    }
    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
