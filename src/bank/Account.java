package bank;

import cash_access.OverdraftException;

public class Account extends cash_access.Account {

    private String id;
    private double balance;
    private String owner;

    public Account(String id, String owner) {
        super();
        this.balance = 0.0;
        this.id = id;
        this.owner = owner;
    }

    public String getID() {
        return id;
    }

    @Override
    public void deposit(double amount) {
        System.out.println("deposit");
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws OverdraftException {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("withdraw");
        if (amount > balance) {
            throw new OverdraftException("The owner: '" + owner
                    + "' has not enough balance on his account: '" + id + "'.");
        } else {
            balance -= amount;
        }
    }

    @Override
    public double getBalance() {
        System.out.println("getBalance");
        return this.balance;
    }

}
