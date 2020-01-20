package com.yaps.petstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * This text menu is used by the employee to manage Customer information.
 * It can find, create, remove and update Customers.
 */
public final class Menu {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static StringTokenizer line;
    private static final String DELIMITER = "-";

    private static final CustomerDAO _dao = new CustomerDAO();

    // ======================================
    // =             Main Method            =
    // ======================================
    public static void main(final String[] args) {
        show();
    }

    // ======================================
    // =        Presentation Methods        =
    // ======================================
    private static void show() {

        try {

            final BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));
            String menuChoice;
            boolean quitNow = false;

            do {

                System.out.println("\n\t------------------  Y A P S  -----------------");
                System.out.println("\t------------------ Pet Store -----------------\n\n");
                System.out.println("\t(0) - Quit");
                System.out.println("\t----------------------");
                System.out.println("\t(1) - Create Customer");
                System.out.println("\t(2) - Find Customer");
                System.out.println("\t(3) - Delete Customer");
                System.out.println("\t(4) - Update Customer");
                System.out.println("\t----------------------");

                System.out.print("\n\tEnter your choice : ");

                menuChoice = msgStream.readLine();
                if (menuChoice != null && menuChoice.trim().length() != 0) {

                    switch (Integer.parseInt(menuChoice)) {

                        case 1:
                            createCustomer();
                            break;
                        case 2:
                            findCustomer();
                            break;
                        case 3:
                            deleteCustomer();
                            break;
                        case 4:
                            updateCustomer();
                            break;

                        case 0:
                            quitNow = true;
                            System.out.println("\n\tPress enter to quit...");
                            break;

                        default:
                            System.out.println("\n\tError : Invalid menu choice !!!. Press enter to enter another choice...");
                            break;
                    }
                }

                // Pause
                msgStream.readLine();
                clearScreen();
            } while (!quitNow);
        } catch (Exception e) {
            System.out.println("\n\tMenu Exception : \n\t" + e.getMessage());
        }
    }

    private static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }

    private static void createCustomer() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Create a Customer   ---");
            System.out.println("\tUsage   : Customer Id - Firstname - Lastname - Telephone - Street1      - Street2      - City     - State - Zipcode - Country");
            System.out.println("\tExample : Smith01     - John      - Smith    - 357 1541  - Ritherdon Rd - Hygh Wicombe - New York - NY    - 1354    - USA\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 10) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            final Customer customer = new Customer(line.nextToken().trim(), line.nextToken().trim(), line.nextToken().trim());
            customer.setTelephone(line.nextToken().trim());
            customer.setStreet1(line.nextToken().trim());
            customer.setStreet2(line.nextToken().trim());
            customer.setCity(line.nextToken().trim());
            customer.setState(line.nextToken().trim());
            customer.setZipcode(line.nextToken().trim());
            customer.setCountry(line.nextToken().trim());
            _dao.insert(customer);
            System.out.println("\n\tInfo : Customer created. Press enter to continue...");
        } catch (CustomerDuplicateKeyException e) {
            System.out.println("\n\tWarning : This Customer already exists !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot create this Customer !!!\n\t" + e.getMessage());
        }


    }

    private static void findCustomer() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Find a Customer   ---");
            System.out.println("\tUsage : Customer Id\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 1) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            // Calls the method that retreives all the data of the object
            final Customer customer = _dao.find(line.nextToken().trim());
            System.out.println("\n" + customer);
            System.out.println("\n\tPress enter to continue...");
        } catch (CustomerNotFoundException e) {
            System.out.println("\n\tWarning : This Customer doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Customer !!! \n\t" + e.getMessage());
        }

    }

    private static void deleteCustomer() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Delete a Customer   ---");
            System.out.println("\tUsage : Customer Id\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 1) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            // Calls the method that retreives all the data of the object
            final Customer customer = _dao.find(line.nextToken().trim());
            System.out.println("\n" + customer + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to remove this Customer (y/n) : ");
                line = readsInputLine();
                if (line.countTokens() != 1) {
                    System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                    readsInputLine();
                } else {
                    nbArgumentsOK = true;
                }
            }

            if ("y".equalsIgnoreCase(line.nextToken().trim())) {
                _dao.remove(customer.getId());
                System.out.println("\n\tInfo : Customer deleted. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Customer not deleted. Press enter to continue...");
            }

        } catch (CustomerNotFoundException e) {
            System.out.println("\n\tWarning : This Customer doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Customer !!! \n\t" + e.getMessage());
        }

    }

    private static void updateCustomer() {

        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Update a Customer   ---");
            System.out.println("\tUsage : Customer Id\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 1) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            // Calls the method that retreives all the data of the object
            final Customer customer = _dao.find(line.nextToken().trim());
            System.out.println("\n" + customer + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to update this Customer (y/n) : ");
                line = readsInputLine();
                if (line.countTokens() != 1) {
                    System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                    readsInputLine();
                } else {
                    nbArgumentsOK = true;
                }
            }

            if ("y".equalsIgnoreCase(line.nextToken().trim())) {
                nbArgumentsOK = false;
                while (!nbArgumentsOK) {
                    System.out.println("\tUsage   : Firstname - Lastname - Telephone - Street1      - Street2      - City     - State - Zipcode - Country");
                    System.out.println("\tExample : John      - Smith    - 357 1541  - Ritherdon Rd - Hygh Wicombe - New York - NY    - 1354    - USA\n");
                    line = readsInputLine();
                    if (line.countTokens() != 9) {
                        System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                        readsInputLine();
                    } else {
                        nbArgumentsOK = true;
                    }
                }
                customer.setFirstname(line.nextToken().trim());
                customer.setLastname(line.nextToken().trim());
                customer.setTelephone(line.nextToken().trim());
                customer.setStreet1(line.nextToken().trim());
                customer.setStreet2(line.nextToken().trim());
                customer.setCity(line.nextToken().trim());
                customer.setState(line.nextToken().trim());
                customer.setZipcode(line.nextToken().trim());
                customer.setCountry(line.nextToken().trim());

                _dao.update(customer);
                System.out.println("\n\tInfo : Customer updated. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Customer not updated. Press enter to continue...");
            }

        } catch (CustomerNotFoundException e) {
            System.out.println("\n\tWarning : This Customer doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Customer !!! \n\t" + e.getMessage());
        }
    }

    private static StringTokenizer readsInputLine() {
        // Reads the line from the input and put it in a StringTokenizer
        StringTokenizer line = null;
        System.out.print("\t");
        try {
            final BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));
            line = new StringTokenizer(msgStream.readLine(), DELIMITER);
        } catch (IOException e) {
            System.out.println("Error : Cannot read the line entered by the user");
        }
        return line;
    }

}
