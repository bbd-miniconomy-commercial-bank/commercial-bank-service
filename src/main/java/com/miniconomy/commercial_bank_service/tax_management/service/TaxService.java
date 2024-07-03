package com.miniconomy.commercial_bank_service.tax_management.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URISyntaxException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;
import com.miniconomy.commercial_bank_service.financial_management.request.TransactionRequest;
import com.miniconomy.commercial_bank_service.financial_management.utils.TransactionUtils;

import java.util.List;

@Service
public class TaxService {

  private TransactionRepository transactionRepository;

  public TaxService() throws URISyntaxException {
    this.transactionRepository = new TransactionRepository();
  }


  public void payTax(int year) throws URISyntaxException
  {
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
    .uri(new URI("https://api.zeus.projects.bbdgrad.com/tax-rate"))
    .GET()
    .build();

    try
    {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Response Code: " + response.statusCode());

      System.out.println(response.body());

      JSONObject jsonResponse = new JSONObject(response.body());

      String value = jsonResponse.getString("business");
      Double taxRate = Double.parseDouble(value) / 100;
      System.out.println("Tax Rate: " + taxRate);

      Long income = (long) 0;
      Long expenses = (long) 0;

      int pageNumber = 0;
      Pageable page = PageRequest.of(pageNumber, 25);
      
      String stringYear = String.valueOf(year);
      if(year < 10)
      {
        stringYear = "0" + stringYear;
      }

      List<Transaction> transactions = transactionRepository.findByAccountNameAndDate("commercial-bank", stringYear, page);
      while(transactions.size() > 0)
      {
        for (Transaction transaction : transactions) {
          if(transaction.getCreditAccountName().equals("commercial-bank"))
          {
            expenses += transaction.getTransactionAmount();
          }
          else
          {
            income += transaction.getTransactionAmount();
          }
        }

        page = PageRequest.of(++pageNumber, 25);
        transactions = transactionRepository.findByAccountNameAndDate("commercial-bank", stringYear, page);
      }

      Long profit = income - expenses;
      Long taxPayable = (long) (profit * taxRate);

      TransactionRequest taxTransaction = new TransactionRequest();
      taxTransaction.setAmount(taxPayable);
      taxTransaction.setDebitAccountName("central-revenue");
      taxTransaction.setCreditAccountName("commercial-bank");
      taxTransaction.setDebitRef("tax-from-commercial-bank");
      taxTransaction.setCreditRef("tax-payable");
      
      transactionRepository.insert(TransactionUtils.transactionMapper(taxTransaction));
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
}
