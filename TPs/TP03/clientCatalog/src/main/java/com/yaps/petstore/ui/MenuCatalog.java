package com.yaps.petstore.ui;

import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.domain.category.CategoryDAO;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.item.ItemDAO;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.domain.product.ProductDAO;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * This text menu is used by the employee to manage Catalog information. It can find, create,
 * remove and update Categories, Products and Items.
 */
public final class MenuCatalog {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static StringTokenizer line;
    private static final String DELIMITER = "-";
    private static CategoryDAO _cdao = new CategoryDAO();
    private static ProductDAO _pdao = new ProductDAO();
    private static ItemDAO _idao = new ItemDAO();

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
                System.out.println("\t--------------- Pet Store Catalog --------------\n\n");
                System.out.println("\t(0) - Quit");
                System.out.println("\t----------------------");
                System.out.println("\tCategory : (10)-Create\t(11)-Find\t(12)-Delete\t(13)-Update\t(14)-Find All");
                System.out.println("\tProduct  : (20)-Create\t(21)-Find\t(22)-Delete\t(23)-Update\t(24)-Find All");
                System.out.println("\tItem     : (30)-Create\t(31)-Find\t(32)-Delete\t(33)-Update\t(34)-Find All");
                System.out.println("\t----------------------");

                System.out.print("\n\tEnter your choice : ");

                menuChoice = msgStream.readLine();
                if (menuChoice != null && menuChoice.trim().length() != 0) {

                    switch (Integer.parseInt(menuChoice)) {

                        case 10:
                            createCategory();
                            break;
                        case 11:
                            findCategory();
                            break;
                        case 12:
                            deleteCategory();
                            break;
                        case 13:
                            updateCategory();
                            break;
                        case 14:
                            findCategories();
                            break;

                        case 20:
                            createProduct();
                            break;
                        case 21:
                            findProduct();
                            break;
                        case 22:
                            deleteProduct();
                            break;
                        case 23:
                            updateProduct();
                            break;
                        case 24:
                            findProducts();
                            break;

                        case 30:
                            createItem();
                            break;
                        case 31:
                            findItem();
                            break;
                        case 32:
                            deleteItem();
                            break;
                        case 33:
                            updateItem();
                            break;
                        case 34:
                            findItems();
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

    private static void createCategory() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Create a Category   ---");
            System.out.println("\tUsage   : Category Id - Name - Descritpion ");
            System.out.println("\tExample : CATFI       - Fish - Swiming animals\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 3) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            final Category category = new Category(line.nextToken().trim(), line.nextToken().trim(), line.nextToken().trim());
            _cdao.insert(category);
            System.out.println("\n\tInfo : Category created. Press enter to continue...");
        } catch (DuplicateKeyException e) {
            System.out.println("\n\tWarning : This Category already exists !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot create this Category !!!\n\t" + e.getMessage());
        }
    }

    private static void createProduct() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Create a Product   ---");
            System.out.println("\tUsage   : Category Id - Product Id - Name        - Descritpion");
            System.out.println("\tExample : CATFI       - PROD1      - Golden Fish - Red fish for your aquariums\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 4) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            final Category category = (Category)_cdao.select(line.nextToken().trim());
            // Associates the Category and creates the Product
            final Product product = new Product(line.nextToken().trim(), line.nextToken().trim(), line.nextToken().trim(), category);
            _pdao.insert(product);
            System.out.println("\n\tInfo : Product created. Press enter to continue...");
        } catch (DuplicateKeyException e) {
            System.out.println("\n\tWarning : This Product already exists !");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : Category must exist to create a product !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot create this Product !!!\n\t" + e.getMessage());
        }
    }

    private static void createItem() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Create a Item   ---");
            System.out.println("\tUsage   : Product Id - Item Id - Name             - Price");
            System.out.println("\tExample : PROD1      - ITEM1   - Male Golden Fish - 10.5\n");

            // Reads the line entered by the user
            line = readsInputLine();
            if (line.countTokens() != 4) {
                System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                readsInputLine();
            } else {
                nbArgumentsOK = true;
            }
        }

        try {
            final Product product = (Product)_pdao.select(line.nextToken().trim());
            // Associates the Product and creates the Item
            final Item item = new Item(line.nextToken().trim(), line.nextToken().trim(), Double.parseDouble(line.nextToken().trim()), product);
            _idao.insert(item);
            System.out.println("\n\tInfo : Item created. Press enter to continue...");
        } catch (DuplicateKeyException e) {
            System.out.println("\n\tWarning : This Item already exists !");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : Product must exist to create an item !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot create this Item !!!\n\t" + e.getMessage());
        }
    }

    private static void findCategory() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Find a Category   ---");
            System.out.println("\tUsage : Category Id\n");

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
            final Category category = (Category)_cdao.select(line.nextToken().trim());
            System.out.println("\n" + category);
            System.out.println("\n\tPress enter to continue...");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Category doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Category !!! \n\t" + e.getMessage());
        }
    }

    private static void findProduct() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Find a Product   ---");
            System.out.println("\tUsage : Product Id\n");

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
            final Product product = (Product)_pdao.select(line.nextToken().trim());

            // Retreives the data for the category
            final Category category = (Category)_cdao.select(product.getCategory().getId());
            product.setCategory(category);
            
            System.out.println("\n" + product);
            System.out.println("\n\tPress enter to continue...");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Product doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Product !!! \n\t" + e.getMessage());
        }
    }

    private static void findItem() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Find an Item   ---");
            System.out.println("\tUsage : Item Id\n");

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
            final Item item = (Item)_idao.select(line.nextToken().trim());

            // Retreives the data for the product
            Product product = item.getProduct();
            product = (Product)_pdao.select(product.getId());
            item.setProduct(product);
      
            System.out.println("\n" + item);
            System.out.println("\n\tPress enter to continue...");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Item doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Item !!! \n\t" + e.getMessage());
        }
    }

    private static void findCategories() {
        System.out.println("\n\n\t---   Find all Categories   ---");

        try {
            // Calls the method that retreives all the data of the object
            final Collection categories;
            categories = _cdao.selectAll();
            for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
                final Category category = (Category) iterator.next();
                System.out.println("\n" + category);
            }
            System.out.println("\n\tPress enter to continue...");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : No Category found in the system !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find Categories !!! \n\t" + e.getMessage());
        }
    }

    private static void findProducts() {
        System.out.println("\n\n\t---   Find all Products   ---");

        try {
            // Calls the method that retreives all the data of the object
            final Collection products;
            products = _pdao.selectAll();
            for (Iterator iterator = products.iterator(); iterator.hasNext();) {
                final Product product = (Product) iterator.next();

                // Retreives the data for the category
                final Category category = (Category)_cdao.select(product.getCategory().getId());
                product.setCategory(category);

                System.out.println("\n" + product);
            }
            System.out.println("\n\tPress enter to continue...");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : No Product found in the system !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find Products !!! \n\t" + e.getMessage());
        }

    }

    private static void findItems() {
        System.out.println("\n\n\t---   Find all Items   ---");

        try {
            // Calls the method that retreives all the data of the object
            final Collection items;
            items = _idao.selectAll();
            for (Iterator iterator = items.iterator(); iterator.hasNext();) {
                final Item item = (Item) iterator.next();

                // Retreives the data for the product
                Product product = item.getProduct();
                product = (Product)_pdao.select(product.getId()); // TODO
                item.setProduct(product);
                
                System.out.println("\n" + item);
            }
            System.out.println("\n\tPress enter to continue...");
        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : No Item found in the system !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find Items !!! \n\t" + e.getMessage());
        }

    }

    private static void deleteCategory() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Delete a Category   ---");
            System.out.println("\tUsage : Category Id\n");

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
            final Category category = (Category) _cdao.select(line.nextToken().trim());
            System.out.println("\n" + category + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to remove this Category (y/n) : ");
                line = readsInputLine();
                if (line.countTokens() != 1) {
                    System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                    readsInputLine();
                } else {
                    nbArgumentsOK = true;
                }
            }

            if ("y".equalsIgnoreCase(line.nextToken().trim())) {
                _cdao.remove(category.getId());
                System.out.println("\n\tInfo : Category deleted. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Category not deleted. Press enter to continue...");
            }

        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Category doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Category !!! \n\t" + e.getMessage());
        }

    }

    private static void deleteProduct() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Delete a Product   ---");
            System.out.println("\tUsage : Product Id\n");

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
            final Product product = (Product)_pdao.select(line.nextToken().trim());
            System.out.println("\n" + product + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to remove this Product (y/n) : ");
                line = readsInputLine();
                if (line.countTokens() != 1) {
                    System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                    readsInputLine();
                } else {
                    nbArgumentsOK = true;
                }
            }

            if ("y".equalsIgnoreCase(line.nextToken().trim())) {
                _pdao.remove(product.getId());
                System.out.println("\n\tInfo : Product deleted. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Product not deleted. Press enter to continue...");
            }

        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Product doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Product !!! \n\t" + e.getMessage());
        }

    }

    private static void deleteItem() {
        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Delete a Item   ---");
            System.out.println("\tUsage : Item Id\n");

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
            final Item item = (Item)_idao.select(line.nextToken().trim());
            System.out.println("\n" + item + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to remove this Item (y/n) : ");
                line = readsInputLine();
                if (line.countTokens() != 1) {
                    System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                    readsInputLine();
                } else {
                    nbArgumentsOK = true;
                }
            }

            if ("y".equalsIgnoreCase(line.nextToken().trim())) {
                _idao.remove(item.getId());
                System.out.println("\n\tInfo : Item deleted. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Item not deleted. Press enter to continue...");
            }

        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Item doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Item !!! \n\t" + e.getMessage());
        }

    }

    private static void updateCategory() {

        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Update a Category   ---");
            System.out.println("\tUsage : Cateogy Id\n");

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
            final Category category = (Category)_cdao.select(line.nextToken().trim());
            System.out.println("\n" + category + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to update this Category (y/n) : ");
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
                    System.out.println("\tUsage   : Name - Descritpion ");
                    System.out.println("\tExample : Fish - Swiming animals\n");
                    line = readsInputLine();
                    if (line.countTokens() != 2) {
                        System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                        readsInputLine();
                    } else {
                        nbArgumentsOK = true;
                    }
                }
                category.setName(line.nextToken().trim());
                category.setDescription(line.nextToken().trim());

                _cdao.update(category);
                System.out.println("\n\tInfo : Category updated. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Category not updated. Press enter to continue...");
            }

        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Category doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Category !!! \n\t" + e.getMessage());
        }
    }

    private static void updateProduct() {

        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Update a Product   ---");
            System.out.println("\tUsage : Product Id\n");

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
            final Product product = (Product)_pdao.select(line.nextToken().trim());
            System.out.println("\n" + product + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to update this Product (y/n) : ");
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
                    System.out.println("\tUsage   : Category Id - Name - Descritpion");
                    System.out.println("\tExample : CATFI       - Fish - Swiming animals\n");
                    line = readsInputLine();
                    if (line.countTokens() != 3) {
                        System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                        readsInputLine();
                    } else {
                        nbArgumentsOK = true;
                    }
                }
                final Category category = (Category)_cdao.select(line.nextToken().trim());

                product.setName(line.nextToken().trim());
                product.setDescription(line.nextToken().trim());
                // Sets the category to the Product
                product.setCategory(category);

                _pdao.update(product);
                System.out.println("\n\tInfo : Product updated. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Product not updated. Press enter to continue...");
            }

        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Product doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Product !!! \n\t" + e.getMessage());
        }
    }

    private static void updateItem() {

        boolean nbArgumentsOK = false;
        while (!nbArgumentsOK) {
            System.out.println("\n\n\t---   Update a Item   ---");
            System.out.println("\tUsage : Item Id\n");

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
            final Item item = (Item)_idao.select(line.nextToken().trim());
            System.out.println("\n" + item + '\n');

            // Deletes the displayed object
            nbArgumentsOK = false;
            while (!nbArgumentsOK) {
                System.out.print("\tDo you want to update this Item (y/n) : ");
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
                    System.out.println("\tUsage   : Product Id - Name             - Price");
                    System.out.println("\tExample : PROD1      - Male Golden Fish - 10.5\n");
                    line = readsInputLine();
                    if (line.countTokens() != 3) {
                        System.out.println("\n\tError : Illegal number of arguments !!!. Press enter to continue...");
                        readsInputLine();
                    } else {
                        nbArgumentsOK = true;
                    }
                }
                final Product product = (Product)_pdao.select(line.nextToken().trim());

                item.setName(line.nextToken().trim());
                item.setUnitCost(Double.parseDouble(line.nextToken().trim()));
                // Sets the product to the item
                item.setProduct(product);

                _idao.update(item);
                System.out.println("\n\tInfo : Item updated. Press enter to continue...");
            } else {
                System.out.println("\n\tInfo : Item not updated. Press enter to continue...");
            }

        } catch (ObjectNotFoundException e) {
            System.out.println("\n\tWarning : This Item doesn't exist !");
        } catch (Exception e) {
            System.out.println("\n\tError : Cannot find this Item !!! \n\t" + e.getMessage());
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
