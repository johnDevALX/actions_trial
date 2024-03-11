package net.ekene.hello.service;

import java.util.Map;

public interface CustomerDetails {
    Map<String, Object> getCustomerData();
    String getCustomerName();
    String getAddress();
    String getDueDate();
}
