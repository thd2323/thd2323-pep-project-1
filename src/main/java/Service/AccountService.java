package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    //get all accounts
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    //add account
    public Account addAccount(Account account){
       
        return accountDAO.addAccount(account);
        
    }

}
