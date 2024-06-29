package com.miniconomy.commercial_bank_service.financial_management.response;

import java.util.List;

import lombok.Data;

@Data
public class PagenatedLoansResponse {
    
    int pageIndex;
    int itemsPerPage;
    int currentItemCount;
    List<LoanResponse> items;

    public PagenatedLoansResponse(int pageIndex, int itemsPerPage, List<LoanResponse> items) {
        this.pageIndex = pageIndex;
        this.itemsPerPage = itemsPerPage;
        this.currentItemCount = items.size();
        this.items = items;
    }

    public void setItems(List<LoanResponse> items) {
        this.currentItemCount = items.size();
        this.items = items;
    }
    
}
