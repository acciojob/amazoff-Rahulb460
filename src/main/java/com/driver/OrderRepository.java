package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class OrderRepository {
    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> dpMap;
    private HashMap<String, List<String>> addPairMap;
    private HashMap<String, String> orderPartnerMap;

    public OrderRepository() {
        this.orderMap = new HashMap<>();
        this.dpMap = new HashMap<>();
        this.addPairMap = new HashMap<>();
        this.orderPartnerMap = new HashMap<>();
    }

    public void addOrder(Order order){
        orderMap.put(order.getId(), order);
    }
    public void addDp(String partnerId){
        dpMap.put(partnerId, new DeliveryPartner(partnerId));
    }
    public void addPair(String orderId, String partnerId) {
        if(orderMap.containsKey(orderId) && dpMap.containsKey(partnerId)) {
            List<String> currentOrdersByPartner = new ArrayList<>();
            if(addPairMap.containsKey(partnerId))
                currentOrdersByPartner = addPairMap.get(partnerId);

            currentOrdersByPartner.add(orderId);
            addPairMap.put(partnerId, currentOrdersByPartner);
            orderPartnerMap.put(orderId, partnerId);
        }
    }
    public Order findOrder(String orderid) {
        return orderMap.get(orderid);
    }
    public DeliveryPartner findPartner(String partnerId) {
        return dpMap.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId) {
        List<String> orderList = new ArrayList<>();
        if(addPairMap.containsKey(partnerId)){
            orderList = addPairMap.get(partnerId);
        }
        return orderList.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orderList = new ArrayList<>();
        if(addPairMap.containsKey(partnerId)){
            orderList = addPairMap.get(partnerId);
        }
        return orderList;
    }
    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }
    public int getCountOfUnassignedOrders() {
        int count = 0;
        List<String> orders = new ArrayList<>(orderMap.keySet());
        for(String s : orders) {
            if(!orderPartnerMap.containsKey(s)) {
                count++;
            }
        }
        return count;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int count = 0;
        List<String> orderList = new ArrayList<>();
        if(addPairMap.containsKey(partnerId)){
            orderList = addPairMap.get(partnerId);
        }

        String[] hourMin = time.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        int ans =  hoursInMins + mins;

        for(String orderId: orderList) {
            if(orderMap.get(orderId).getDeliveryTime() > ans) {
                count++;
            }
        }
        return count;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int ans = Integer.MIN_VALUE;
        List<String> orderList = new ArrayList<>();
        if(addPairMap.containsKey(partnerId)){
            orderList = addPairMap.get(partnerId);
        }

        for(String orderId : orderList) {
            ans = Math.max(ans, orderMap.get(orderId).getDeliveryTime());
        }
        String startTime = "00:00";
        int minutes = ans;
        int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
        int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
        String newtime = h+":"+m;
        return newtime;
    }

    public void deletePartnerById(String partnerID) {
        if(addPairMap.containsKey(partnerID)) {
            addPairMap.remove(partnerID);
            dpMap.remove(partnerID);
        }
    }
    public void deleteOrderById(String orderId) {
        if(orderMap.containsKey(orderId)) {
            orderMap.remove(orderId);
        }
        if(orderPartnerMap.containsKey(orderId)) {
            orderPartnerMap.remove(orderId);
        }
    }

}
