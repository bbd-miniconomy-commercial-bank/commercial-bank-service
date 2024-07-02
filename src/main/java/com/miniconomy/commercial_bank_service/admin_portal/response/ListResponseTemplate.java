package com.miniconomy.commercial_bank_service.admin_portal.response;

import java.util.List;

import lombok.Data;

@Data
public class ListResponseTemplate<T> {
    
    int pageIndex;
    int itemsPerPage;
    int currentItemCount;
    List<T> items;

    public ListResponseTemplate(int pageIndex, int itemsPerPage, List<T> items) {
        this.pageIndex = pageIndex;
        this.itemsPerPage = itemsPerPage;
        this.currentItemCount = items.size();
        this.items = items;
    }

    public void setItems(List<T> items) {
        this.currentItemCount = items.size();
        this.items = items;
    }
    
}
