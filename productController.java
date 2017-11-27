package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import model.Product;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Customer;
import model.Greenhouse;
import model.OrderedProduct;
import model.Orders;
import model.Users;
import view.MainFrame;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class productController {
    
    static Session session = null;
    
    
    /* Otwiera sesje dodawania produktu do bazy danych tabela Greenhouse
    */
    public static void insertGh(Greenhouse e) {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("INSERTED TO GH");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
    }
        
    
    
    /* Otwiera sesje usuwania produktu do bazy danych tabela Greenhouse
    */
    public static void deleteGh(Greenhouse e) {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(e);
            tx.commit();
            System.out.println("DELETED FROM GH");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
        
    }
    
    /* Otwiera sesje dodawania produktu do bazy danych tabela Product
    */
    public static void insertProduct(Product e) {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("Product INSERTED!");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
    }
    
    
    /* Otwiera sesje usuwania produktu z bazy danych tabela Product
    */
    public static void deleteProduct(Product e) {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(e);
            tx.commit();
            System.out.println("Product DELETED!");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
        
    }
    
    
    /* Otwiera sesje dodawania zamówienia do bazy danych tabeli Orders
    */
    public static void insertOrder(Orders e) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("Order INSERTED!");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
    }
    
    
    
    /* Otwiera sesje dodawania zamówionych prodkuktów z zamówienia w bazie danych tabeli OrederedProduct
    */
    public static void insertOrderedProduct(OrderedProduct e) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("OrderProduct INSERTED!");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
    }
    
    
    /* Otwiera sesje dodawania Klienta do bazy danych tabeli Customer
    */
    public static void insertCustomer(Customer e) {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("Customer INSERTED!");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
    }
    
    
    /* Otwiera sesje dodawania danych logowania klienta do bazy danych tabeli Users
    */
    public static void insertUser(Users e) {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("User INSERTED!");
        }
        catch (Exception x) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
    }
    
    
    /* Zwraca dowolny obiekt z bazy danych w postaci rekordu
    */
    public static Object fetchValueFromDB(String hql) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List list = session.createQuery(hql).list();
        Iterator iterator = list.iterator();
        Object result = iterator.next();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    
    /* Zwraca listę kwiatów z bazy danych 
    *LISTA KWIATÓW pierwsza pozycja dodana przed listą 
    */
    public static List getFlowerListFromDB() {

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List list = session.createQuery("SELECT name FROM Product where name like '%'").list();
        List fetchList = new ArrayList();
        fetchList.add("LISTA KWIATÓW");
        Iterator iterator = list.iterator();
        while(iterator.hasNext())
            fetchList.add((String)iterator.next());  
        session.getTransaction().commit();
        session.close();
        return fetchList;
    }
    
    
    /* Sesja update bazy danych
    */
    public static void updateDB(String hql) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery(hql).executeUpdate();   
        System.out.println("UPDATE! 1 row affected");
        session.getTransaction().commit();
        session.close();
    }   
    
    
    /* Zwraca wybraną kolumne z bazy danych
    */
    public static List getColumnFromDB(String hql) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List list = session.createQuery(hql).list();
        List fetchList = new ArrayList();
        fetchList.add(0);
        Iterator iterator = list.iterator();
        while(iterator.hasNext())
            fetchList.add(iterator.next());  
        session.getTransaction().commit();
        session.close();
        return fetchList;
    }
    
    
    /* odświeżanie obrazków
    */
    public static void updateLabelPicture(String name, JLabel Lpicture) {
        
        ImageIcon icon = createImageIcon("Images/" + name + ".jpg");
        Lpicture.setIcon(icon);
        if (icon != null) {
            Lpicture.setText(null);
        } else {
            Lpicture.setText("Nie znaleziono obrazu");
        }       
    }
    
    
    
    /* odświeżanie stanów magazynowych na głównej zakładce
    */
    public static void updateStockLabels(Product product, Greenhouse productGH, JLabel LpriceShow, JLabel LquantityShow, JLabel LavailabilityShow) {
        
        double price = 0.00;
        int quantity = 0;
        int GHquantity = 0;
        
        if(!(product.getName().equals("LISTA KWIATÓW"))) {
            price = product.getUnitprice();
            quantity = product.getQuantity();
            GHquantity = productGH.getQuantity();
        }
        //wyswietlenie ceny kwiatow w label
        LpriceShow.setText(String.valueOf(price + " zł/szt"));
        //wyswietlenie ilosc kwiatow na stanie w label
        LquantityShow.setText(String.valueOf(quantity + " szt"));
        //wyswietlenie ilosc kwiatow na magazynie w label
        LavailabilityShow.setText(String.valueOf(GHquantity + " szt"));
        
    }

    
    
    /* Tworzenie obrazków
    */
    public static ImageIcon createImageIcon(String path) {
        
        java.net.URL imgURL = MainFrame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Nie odnaleziono pliku: " + path);
            return null;
        }
    }
    
    
    /* dodawanie wybranego produktu do listy zakupów
    */
    public static void orderAction(String flower,List<Product> orderedProductList,DefaultListModel LOrderList) {
        int count = 0;
        int check = -1;
        if(flower != null && !flower.equals("LISTA KWIATÓW")) { // sprawdza czy pozycja jest juz na zamówieniu
            for(int i=0 ; i < orderedProductList.size();i++)
                if(orderedProductList.get(i).getName().equals(flower)) {
                    check = i;
                }
            
            Product product = (Product)fetchValueFromDB("FROM Product WHERE name='"+flower+"'");
            try {
                count = Integer.parseInt(JOptionPane.showInputDialog("ilość (sztuk):"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),"Podaj liczbę!.", JOptionPane.ERROR_MESSAGE);
            }
            
            if(count <= 0)
                count = 0;
            if (count >= product.getQuantity())
                count = product.getQuantity();

            product.setQuantity(count);
            
            if(count > 0) {
                if(check == -1)
                    orderedProductList.add(product);
                else
                    orderedProductList.set(check, product);      
            }
            
            LOrderList.clear();
            for(int i = 0; i < orderedProductList.size(); i++) {
                LOrderList.addElement(
                        i+1+". cena: "+orderedProductList.get(i).getUnitprice() +
                        " zł/szt.   ilość: "+orderedProductList.get(i).getQuantity() +
                        " szt.   nazwa: "+orderedProductList.get(i).getName());
            }
        }
    }
    
    
    
    /* Zwraca Integer z pola tekstowego
    */
    public static int getIntFromTextField(JTextField tf) {
        int count = 0;
        try {
            count = Integer.parseInt(tf.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error, not a number!.", JOptionPane.ERROR_MESSAGE);
        }
        return count;
    }
    
    
    
    /* przeniesienie produktów ze szklarni na sklep
    */
    public static void orderFromGH(JList LGHStock) {
        int count = 0;
        try {
            count = Integer.parseInt(JOptionPane.showInputDialog("ilość (sztuk): "));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Wprowadzony znak nie jest liczbą!.", JOptionPane.ERROR_MESSAGE);
        }
        
        
        int selectedItem = LGHStock.getSelectedIndex()+1;
        int selId = (Integer)fetchValueFromDB("SELECT productId FROM Product WHERE id="+selectedItem);
        int quant = (Integer)fetchValueFromDB("SELECT quantity from Greenhouse where productId="+selId);
        int quantInProduct = (Integer)fetchValueFromDB("SELECT quantity from Product where productId="+selId);
        
        if(count>quant)
            count = quant;
        if(count < 1)
            count = 0;
        
        String quantUpdToGH = "UPDATE Greenhouse set quantity ="+(quant - count)+" where productId="+selId;
        String quantUpdToProduct = "UPDATE Product set quantity ="+(quantInProduct+count)+" where productId="+selId;
        int ret = JOptionPane.showConfirmDialog (null,"Czy dodać do stanu Sklepu: "+count+" sztuk?", "Potwierdzenie zamówienia",JOptionPane.OK_CANCEL_OPTION);

            if(ret == -1) { } //wyjscie x
            else if(ret == 0) { //ok
                updateDB(quantUpdToGH);
                updateDB(quantUpdToProduct);
            } 
            else if(ret == 2) { }//cancel
        
        DefaultListModel LGHStockDefaultList = (DefaultListModel)LGHStock.getModel();    
        getGHList(LGHStockDefaultList);
    }
    
    
    
    /* zatwierdzenie zamówienia
    */
    public static int confirmationAction(String userEmail, List<Product> orderedProductList) {
        double sum = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        for(int i = 0; i < orderedProductList.size(); i++) {
            double price = Double.parseDouble(df.format(orderedProductList.get(i).getUnitprice() * orderedProductList.get(i).getQuantity()));
            sum+=price;
        }
        if(sum > 0) {
            int ret = JOptionPane.showConfirmDialog (null,"Całkowity koszt: " + df.format(sum) + "zł", "Potwierdzenie zamówienia",JOptionPane.OK_CANCEL_OPTION);

            if(ret == -1) { } //wyjscie x
            else if(ret == 0) { //ok
                //createOrder(userEmail, sum, flowerOrdered, quantityOrdered);
                createOrder(userEmail, sum, orderedProductList);
                for(int i = 0; i < orderedProductList.size(); i++) {
                    updateDB("UPDATE Product SET quantity="+((Integer)fetchValueFromDB("SELECT quantity FROM Product WHERE name='"+orderedProductList.get(i).getName()+"'")-orderedProductList.get(i).getQuantity())+" where name='"+ orderedProductList.get(i).getName()+"'");

                }
                return 0;
            } 
            else if(ret == 2) { }//cancel
        }
        return 1;
    }
    
    
    
    /* Tworzenie zamówienia z listy zakupów i Insert do bazy danych
    */
    public static void createOrder(String userEmail, Double sum, List<Product> orderedProductList) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Date date = new Date();
        int rethql = (Integer)fetchValueFromDB("SELECT customerId FROM Customer where email like '"+ userEmail +"'");
        Orders order = new Orders();
        order.setCustomerId(rethql);
        order.setDateOfOrder(date);
        order.setTotalprice(Double.parseDouble((df.format(sum))));
        insertOrder(order);
        
        for(int i = 0; i < orderedProductList.size(); i++) {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setOrdersId(order.getOrdersId());
            orderedProduct.setProductId(orderedProductList.get(i).getProductId());
            orderedProduct.setQuantity(orderedProductList.get(i).getQuantity());
            insertOrderedProduct(orderedProduct);
        }
        
    }
    
    
    
    /*Logowanie użytkownika
    */
    public static boolean login(boolean logged, JLabel LloginInfo, boolean admin, JLabel LadminInfo, JLabel jLabel4, JTabbedPane jTabbedPane1, JButton BTNlogout, JButton BTNlogin) {
        jLabel4.setText(String.valueOf(logged));
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("e-mail", SwingConstants.RIGHT));
        label.add(new JLabel("hasło", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);
        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);
        
        int ret = JOptionPane.showConfirmDialog(null, panel, "login", JOptionPane.OK_CANCEL_OPTION);
        
        if(ret == -1) { 
            logged = false; 
            admin = false;
            jLabel4.setText(String.valueOf(logged));
            return logged;
        } //wyjscie x
        else if(ret == 0) { //ok
            String hql = "select u.password from Users u, Customer c WHERE u.customerId=c.customerId and c.email ='" + String.valueOf(username.getText()) + "'";
            String hql2 = "select u.admin from Users u, Customer c WHERE u.customerId=c.customerId and c.email ='" + String.valueOf(username.getText()) + "'";
            
            try {
                String compatiblePass = (String)fetchValueFromDB(hql);
                boolean adminRights = (Boolean)fetchValueFromDB(hql2);

                if(compatiblePass.equals(String.valueOf(password.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "zalogowano!.", "login", JOptionPane.INFORMATION_MESSAGE);
                    logged = true;
                    BTNlogout.setVisible(true);
                    BTNlogin.setVisible(false);
                    LloginInfo.setText(String.valueOf(username.getText()));
                    if(adminRights==true) {
                        admin = true;
                        LadminInfo.setForeground(Color.red);
                        LadminInfo.setText("ADMIN");
                        jTabbedPane1.setEnabled(true);
                    }
                    jLabel4.setText(String.valueOf(logged));
                    return logged;
                } else {
                    JOptionPane.showMessageDialog(null, " Błędny login/hasło.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog(null, " Błędny login/hasło.", "ERROR", JOptionPane.ERROR_MESSAGE);
                jLabel4.setText(String.valueOf(logged));
                return logged;
            }
        } 
        else if(ret == 2) { 
            logged = false; 
            admin = false;
            jLabel4.setText(String.valueOf(logged));
            return logged;
        }//cancel
        jLabel4.setText(String.valueOf(logged));
        return logged;
    }
    
    
    
    /* Rejestracja użytkownika
    */
    public static void registerUser() {
        JPanel panel = new JPanel(new BorderLayout(5, 30));
        JPanel label = new JPanel(new GridLayout(9, 1, 10, 10));
        label.add(new JLabel("Imie (*)", SwingConstants.RIGHT));
        label.add(new JLabel("Nazwisko (*)", SwingConstants.RIGHT));
        label.add(new JLabel("Kod-pocztowy (*)", SwingConstants.RIGHT));
        label.add(new JLabel("Miasto (*)", SwingConstants.RIGHT));
        label.add(new JLabel("Ulica (*)", SwingConstants.RIGHT));
        label.add(new JLabel("Telefon (*)", SwingConstants.RIGHT));
        label.add(new JLabel("E-mail", SwingConstants.RIGHT));
        label.add(new JLabel("Hasło (*)", SwingConstants.RIGHT));
        label.add(new JLabel("Powtórz hasło (*)", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);
        JPanel controls = new JPanel(new GridLayout(9, 1, 10, 10));
        JTextField userName = new JTextField();
        controls.add(userName);
        JTextField lastName = new JTextField();
        controls.add(lastName);
        JTextField zip = new JTextField();
        controls.add(zip);
        JTextField city = new JTextField();
        controls.add(city);
        JTextField street = new JTextField();
        controls.add(street);
        JTextField phone = new JTextField();
        controls.add(phone);
        JTextField email = new JTextField();
        controls.add(email);
        JPasswordField userPass = new JPasswordField();
        controls.add(userPass);
        JPasswordField userPassCheck = new JPasswordField();
        controls.add(userPassCheck);
        panel.add(controls, BorderLayout.CENTER);
        panel.setSize(new Dimension(400, 10));
        panel.setPreferredSize(new Dimension(400, panel.getPreferredSize().height));
        int ret = JOptionPane.showConfirmDialog(null, panel, "Rejestracja", JOptionPane.OK_CANCEL_OPTION);
        
        if(ret == 0) {
            if(!(userName.getText().equals("") ||
                    lastName.getText().equals("") ||
                    city.getText().equals("") ||
                    street.getText().equals("") ||
                    phone.getText().equals("") ||
                    zip.getText().equals("") ||
                    userPass.getPassword().equals("") ||
                    userPassCheck.getPassword().equals(""))) {
                
                if(Arrays.equals(userPass.getPassword(), userPassCheck.getPassword())) {
                    Customer customer = new Customer();
                    customer.setFirstname(String.valueOf(userName.getText()));
                    customer.setLastname(String.valueOf(lastName.getText()));
                    customer.setEmail(email.getText());
                    customer.setCity(String.valueOf(city.getText()));
                    customer.setStreet(String.valueOf(street.getText()));
                    customer.setPhone(String.valueOf(phone.getText()));
                    customer.setPostcode(String.valueOf(zip.getText()));
                    insertCustomer(customer);

                    Users user = new Users();
                    user.setPassword(String.valueOf(userPass.getPassword()));
                    user.setCustomerId(customer.getCustomerId());
                    insertUser(user);   
                } else 
                    JOptionPane.showMessageDialog(null, "Podane hasła są różne");
            } else
                JOptionPane.showMessageDialog(null, "nie podano wszystkich danych rejestracyjnych!");
        }
    }
    
    
    /* Wypełnia listę stanów magazynowych*/
    public static void getGHList(DefaultListModel stockList) {
        stockList.clear();
        List ghq = getColumnFromDB("SELECT quantity FROM Greenhouse");
        List id = getColumnFromDB("SELECT productId FROM Greenhouse");
        List pName = getFlowerListFromDB();
        for(int i = 1; i < ghq.size(); i++) {
            stockList.addElement("id."+id.get(i)+"   "+pName.get(i) + "   " + ghq.get(i)+" szt.");
        }
    }
    
    
    /* Wypełnia tabele wszystkimi zarejestrowanymi użytkownikami
    */
    public static void getCustomerTable(JTable table) {
        int customersCount = Integer.parseInt(fetchValueFromDB("select count(*) from Customer").toString());
        List list = getColumnFromDB("select customerId from Customer");
        for(int i = 0; i < customersCount;i++) {
            Customer customer = (Customer)fetchValueFromDB("FROM Customer where customerId="+list.get(i+1));
            table.setValueAt(customer.getCustomerId(), i, 0);
            table.setValueAt(customer.getFirstname(), i, 1);
            table.setValueAt(customer.getLastname(), i, 2);
            table.setValueAt(customer.getPostcode(), i, 3);
            table.setValueAt(customer.getCity(), i, 4);
            table.setValueAt(customer.getStreet(), i, 5);
            table.setValueAt(customer.getPhone(), i, 6);
            table.setValueAt(customer.getEmail(), i, 7);
        }
    }
    
    /*Wypełnia historie wszystkich zamówien 
    * dostępna tylko w dla administratora
    */
    public static void getOrderHistoryForAdministration(JTable Thistory,JTable TOrderHistory) {
        for(int i = 0; i < 6;i++)
            for(int j = 0; j < 50;j++)
                TOrderHistory.setValueAt(null, j, i);
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        int ordersCount = Integer.parseInt(fetchValueFromDB("select count(*) from Orders").toString());
        int selectedRow = 0;
        int k = 0;
        List idList = new ArrayList();
            idList = getColumnFromDB("select customerId from Customer");
        int selectedCustomerId = (Integer)Thistory.getValueAt(Thistory.getSelectedRow(), 0);
        List orderIdList = getColumnFromDB("select ordersId from Orders where customerId="+selectedCustomerId);
        if(selectedRow>-1 && selectedRow < ordersCount) {
            
            for(int j = 0; j < orderIdList.size(); j++) {      
                int selectedOrderId = (Integer)orderIdList.get(j);
                List list = getColumnFromDB("select opId FROM OrderedProduct where ordersId="+selectedOrderId);
                for(int i = 0; i < list.size()-1; i++) {
                    String quantity = (String.valueOf(fetchValueFromDB("select quantity from OrderedProduct where ordersId="+selectedOrderId+" and opId="+list.get(i+1))));
                    String productId = (String.valueOf(fetchValueFromDB("select productId from OrderedProduct where ordersId="+selectedOrderId+" and opId="+list.get(i+1))));
                    String nameOfProduct = (String.valueOf(fetchValueFromDB("select name from Product where productId="+productId)));
                    Double priceOfProduct = Double.parseDouble((df.format(fetchValueFromDB("select unitprice from Product where productId="+productId))));
                    String dateOfBuy = String.valueOf(fetchValueFromDB("select dateOfOrder from Orders where ordersId="+selectedOrderId));

                    TOrderHistory.setValueAt(selectedOrderId, k, 0);
                    TOrderHistory.setValueAt(productId, k, 1);
                    TOrderHistory.setValueAt(nameOfProduct, k, 2);
                    TOrderHistory.setValueAt(quantity, k, 3);
                    TOrderHistory.setValueAt(priceOfProduct, k, 4);
                    TOrderHistory.setValueAt(dateOfBuy, k, 5);
                    k++;
                }
            }
        }
    }
    
    
    /* Zwraca double z pola tekstowego
    
    */
    public static double getDoubleFromTextField(JTextField tf) {
        double count = 0;
        try {
            count = Double.parseDouble(tf.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Wprowadzony znak nie jest liczbą!.", JOptionPane.ERROR_MESSAGE);
        }
        return count;
    }
    
    public static void changePrice(JList LGHStock) {
        double price = Double.parseDouble(JOptionPane.showInputDialog("nowa cena: "));
        int selectedItem = LGHStock.getSelectedIndex()+1;
        int selId = (Integer)fetchValueFromDB("SELECT productId FROM Product WHERE id="+selectedItem);

        if(price < 0)
            price = 0;
        
        String priceUpdToProduct = "UPDATE Product set unitprice ="+price+" where productId="+selId;
        int ret = JOptionPane.showConfirmDialog (null,"Czy zmienić cenę produktu na: "+price+"?", "Potwierdzenie",JOptionPane.OK_CANCEL_OPTION);

            if(ret == -1) { } //wyjscie x
            else if(ret == 0) { //ok
                updateDB(priceUpdToProduct);
            } 
            else if(ret == 2) { }//cancel
    }
    
    
    
    /* Dodanie nowego produktu do magazynu
    *  + przetwarzanie dowolnego obrazka na format .jpg i pasującego do ustalonego formatu
    */
    public static void addNewProduct() {
        
        JPanel panel = new JPanel(new BorderLayout(5, 30));
        JPanel label = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton btn = new JButton("Dodaj zdjęcie");
        
        label.add(new JLabel("Nazwa", SwingConstants.RIGHT));
        label.add(btn);
        
        panel.add(label, BorderLayout.WEST);
        panel.add(btn, BorderLayout.WEST);
        JPanel controls = new JPanel(new GridLayout(2, 1, 10, 10));
        
        JTextField name = new JTextField();
        controls.add(name);
        controls.add(btn);
        final JFileChooser fc = new JFileChooser();
        
        btn.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Wskaż plik ze zdjęciem dodawanego produktu.","Info", JOptionPane.INFORMATION_MESSAGE);
                int returnVal = fc.showOpenDialog(btn);
                 if (returnVal == JFileChooser.APPROVE_OPTION && !name.equals("")) {
                    File file = fc.getSelectedFile();
                    try {
                        BufferedImage bufferedImage = ImageIO.read(file);
                        Image scaledImage = bufferedImage.getScaledInstance(200, 200, bufferedImage.SCALE_DEFAULT);
                        Image toolkitImage = scaledImage.getScaledInstance(200, 200,scaledImage.SCALE_SMOOTH);
                        int width = toolkitImage.getWidth(null);
                        int height = toolkitImage.getHeight(null);
                        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        Graphics g = newImage.getGraphics();
                        g.drawImage(toolkitImage, 0, 0, null);
                        g.dispose();
                        ImageIO.write(newImage, "jpg", new File(System.getProperty("user.dir")+"/src/view/Images/"+name.getText()+".jpg"));
                    } catch (IOException s) {}
                 }
            }
        });
        

        panel.add(controls, BorderLayout.CENTER);
        panel.setSize(new Dimension(100, 10));
        panel.setPreferredSize(new Dimension(100, panel.getPreferredSize().height));
        
        
        int ret = JOptionPane.showConfirmDialog(null, panel, "Nowy produkt", JOptionPane.OK_CANCEL_OPTION);
        
        if(ret == 0) {
            if(!name.getText().equals("")) {
                Greenhouse newGh = new Greenhouse();
                newGh.setQuantity(0);
                insertGh(newGh);
                
                Product product = new Product();
                product.setName(name.getText());
                product.setQuantity(0);
                product.setUnitprice(0.0);
                product.setProductId(newGh.getProductId());
                insertProduct(product);
            } else 
                JOptionPane.showMessageDialog(null,"Wprowadzona wartość nie jest słowem!.","Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    /*Wypełnia tabele Historia (zakupów) dla uzytkownika podanego w argumencie
    * Historia dostępna po zalogowaniu!
    */
    public static void getPrivateHistory(String login) {
        JTable table = new JTable();
        JScrollPane jsp = new JScrollPane();
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID ZAMÓWIENIA", "ID PRODUKTU", "NAZWA", "ZAMÓWIONA ILOŚĆ", "CENA JEDNOSTKOWA", "DATA ZAMÓWIENIA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        jsp.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        /***/

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        for(int i = 0; i < 6;i++)
            for(int j = 0; j < 50;j++)
                table.setValueAt(null, j, i);
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        
        int ordersCount = Integer.parseInt(fetchValueFromDB("select count(*) from Orders").toString());
        int selectedRow = 0;
        int k = 0;
        List idList = getColumnFromDB("select customerId from Customer");
        int selectedCustomerId = (Integer)fetchValueFromDB("SELECT customerId FROM Customer where email='"+login+"'");
        List orderIdList = getColumnFromDB("select ordersId from Orders where customerId="+selectedCustomerId);
        if(selectedRow>-1 && selectedRow < ordersCount) {
            
            for(int j = 0; j < orderIdList.size(); j++) {      
                int selectedOrderId = (Integer)orderIdList.get(j);
                List list = getColumnFromDB("select opId FROM OrderedProduct where ordersId="+selectedOrderId);
                for(int i = 0; i < list.size()-1; i++) {
                    String quantity = (String.valueOf(fetchValueFromDB("select quantity from OrderedProduct where ordersId="+selectedOrderId+" and opId="+list.get(i+1))));
                    String productId = (String.valueOf(fetchValueFromDB("select productId from OrderedProduct where ordersId="+selectedOrderId+" and opId="+list.get(i+1))));
                    String nameOfProduct = (String.valueOf(fetchValueFromDB("select name from Product where productId="+productId)));
                    Double priceOfProduct = Double.parseDouble((df.format(fetchValueFromDB("select unitprice from Product where productId="+productId))));
                    String dateOfBuy = String.valueOf(fetchValueFromDB("select dateOfOrder from Orders where ordersId="+selectedOrderId));

                    table.setValueAt(selectedOrderId, k, 0);
                    table.setValueAt(productId, k, 1);
                    table.setValueAt(nameOfProduct, k, 2);
                    table.setValueAt(quantity, k, 3);
                    table.setValueAt(priceOfProduct, k, 4);
                    table.setValueAt(dateOfBuy, k, 5);
                    k++;
                }
            }
        }
    }
}
    