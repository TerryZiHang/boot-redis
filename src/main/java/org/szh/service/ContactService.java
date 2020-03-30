package org.szh.service;

import java.util.List;

import org.szh.bean.ContactInfo;

public interface ContactService {
    
    List<ContactInfo> getAll();

    ContactInfo save(ContactInfo base);

    ContactInfo updateContact(ContactInfo base);
    
    ContactInfo getById(String markId);
    
    void deleteAll();
    
    void deleteById(String markId) ;  
}
