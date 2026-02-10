package com.bank.main;

import java.util.Scanner;
import com.bank.service.BankService;

public class BankApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();
        int userId = 0;

        try {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter choice: ");
            int ch = Integer.parseInt(sc.nextLine());

            // REGISTER
            if (ch == 1) {
                System.out.print("Username: ");
                String u = sc.nextLine();

                System.out.print("Password: ");
                String p = sc.nextLine();

                service.register(u, p);
                System.out.println("✅ User Registered Successfully\n");
            }

            // LOGIN
            System.out.println("=== Login ===");
            System.out.print("Username: ");
            String u = sc.nextLine();

            System.out.print("Password: ");
            String p = sc.nextLine();

            userId = service.login(u, p);
            System.out.println("✅ Login Successful");

            int choice;
            do {
                System.out.println("\n===== BANK MENU =====");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Transaction History");
                System.out.println("5. Change Username");
                System.out.println("6. Change Password (PIN)");
                System.out.println("7. Delete Account");
                System.out.println("8. Exit");
                System.out.print("Enter choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter amount: ");
                        double dep = Double.parseDouble(sc.nextLine());
                        service.deposit(userId, dep);
                        System.out.println("✅ Amount Deposited");
                        break;

                    case 2:
                        System.out.print("Enter amount: ");
                        double wit = Double.parseDouble(sc.nextLine());
                        service.withdraw(userId, wit);
                        System.out.println("✅ Amount Withdrawn");
                        break;

                    case 3:
                        double bal = service.checkBalance(userId);
                        System.out.println("💰 Current Balance: " + bal);
                        break;

                    case 4:
                        service.showTransactions(userId);
                        break;

                    case 5:
                        System.out.print("Enter new username: ");
                        String newUser = sc.nextLine();
                        service.changeUsername(userId, newUser);
                        System.out.println("✅ Username Updated");
                        break;

                    case 6:
                        System.out.print("Enter new password: ");
                        String newPass = sc.nextLine();
                        service.changePassword(userId, newPass);
                        System.out.println("✅ Password Updated");
                        break;

                    case 7:
                        System.out.print("Are you sure? (yes/no): ");
                        String confirm = sc.nextLine();
                        if (confirm.equalsIgnoreCase("yes")) {
                            service.deleteAccount(userId);
                            System.out.println("❌ Account Deleted");
                            return;
                        }
                        break;

                    case 8:
                        System.out.println("🙏 Thank you for using Bank App");
                        break;

                    default:
                        System.out.println("❌ Invalid choice");
                }

            } while (choice != 8);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}



