package com.example.mainservice.migrations;

import com.example.mainservice.models.entities.*;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.repositories.*;
import com.example.mainservice.services.RoleService;
import com.example.mainservice.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class Migration {
    @Value("${init-db}")
    private boolean initDb;
    private final ProductRepo productRepo;
    private final ProductOrderRepo productOrderRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    private final RoleService roleService;
    private final RegionRepo regionRepo;
    public boolean inited = false;
    @Async
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() throws InterruptedException {
        initDb();
        if(userRepo.count() < 99){
            initUsers();
        }
        initRegions();
        inited = true;
    }
    public void initRegions(){
        if(!regionRepo.existsByName("Москва")) {
            List<Region> regions = new ArrayList<>();
            List<String> russianRegionsData = Arrays.asList(
                    "Москва", "Санкт-Петербург", "Московская область", "Свердловская область",
                    "Новосибирская область", "Республика Татарстан", "Красноярский край",
                    "Башкортостан", "Пермский край", "Приморский край", "Ростовская область",
                    "Ставропольский край", "Ханты-Мансийский автономный округ — Югра",
                    "Самарская область", "Челябинская область", "Иркутская область",
                    "Нижегородская область", "Волгоградская область", "Омская область",
                    "Кемеровская область", "Краснодарский край", "Воронежская область",
                    "Тюменская область", "Саратовская область", "Тульская область",
                    "Калининградская область", "Белгородская область", "Ульяновская область",
                    "Ленинградская область", "Ярославская область"
            );

            for (String regionName : russianRegionsData) {
                regions.add(new Region(regionName));
            }
            regionRepo.saveAll(regions);
        }
    }
    @Scheduled(fixedRate = 15000)
    @Transactional
    public void runInitOrders() {
        if(inited) {
            initOrders();
        }
    }
    @Transactional
    public void initOrders() {
        List<Product> products = productRepo.findAll();
        List<User> users = userRepo.findAll();
        List<Region> regions = regionRepo.findAll();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            List<ProductOrder> productOrders = new ArrayList<>();

            int numOrders = random.nextInt(5) + 1;
            for (int j = 0; j < numOrders; j++) {
                Product product = products.get(random.nextInt(products.size()));
                User user = users.get(random.nextInt(users.size()));
                Region region = regions.get(random.nextInt(regions.size()));
                Integer amount = (int) (random.nextInt(10) + 1);
                Double totalPrice = amount * product.getPrice();
                Order order = Order.builder()
                        .user(user)
                        .orderTime(LocalDateTime.now())
                        .region(region)
                        .orderStatus(OrderStatus.COMPLETED)
                        .build();
                user.getOrders().add(order);
                ProductOrder productOrder = ProductOrder.builder()
                        .product(product)
                        .order(order)
                        .amount(amount)
                        .totalPrice(totalPrice)
                        .build();
                productOrders.add(productOrder);
            }
            productOrderRepo.saveAll(productOrders);
        }
    }

    public void initUsers(){
        List<User> users = new ArrayList<>();
        Random random = new Random();
        List<String> names = Arrays.asList("Иван", "Петр", "Александр", "Сергей", "Дмитрий", "Михаил", "Андрей", "Николай", "Алексей", "Владимир");
        List<String> middleNames = Arrays.asList("Иванович", "Петрович", "Александрович", "Сергеевич", "Дмитриевич", "Михайлович", "Андреевич", "Николаевич", "Алексеевич", "Владимирович");
        List<String> surnames = Arrays.asList("Иванов", "Петров", "Александров", "Сергеев", "Дмитриев", "Михайлов", "Андреев", "Николаев", "Алексеев", "Владимиров");
        Set<String> usedUsernames = new HashSet<>();
        Set<String> usedPhoneNumbers = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            String name = names.get(random.nextInt(names.size()));
            String middleName = middleNames.get(random.nextInt(middleNames.size()));
            String surname = surnames.get(random.nextInt(surnames.size()));

            String username = generateUniqueUsername(name, middleName, surname, usedUsernames);
            String phoneNumber = generateUniquePhoneNumber(usedPhoneNumbers);

            User user = new User(username, name, middleName, surname, phoneNumber);
            user.getRoles().add(roleService.getUserRole());
            users.add(user);
        }

        userRepo.saveAll(users);
    }

    private String generateUniqueUsername(String name, String middleName, String surname, Set<String> usedUsernames) {
        String username = name.toLowerCase().substring(0, 1) + middleName.toLowerCase().substring(0, 1) + surname.toLowerCase();
        if (!usedUsernames.contains(username)) {
            usedUsernames.add(username);
            return username;
        } else {
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            return generateUniqueUsername(name, middleName, surname + randomNumber, usedUsernames);
        }
    }

    private String generateUniquePhoneNumber(Set<String> usedPhoneNumbers) {
        Random random = new Random();
        String phoneNumber = "7";
        for (int i = 0; i < 10; i++) {
            phoneNumber += random.nextInt(10);
        }
        if (!usedPhoneNumbers.contains(phoneNumber)) {
            usedPhoneNumbers.add(phoneNumber);
            return phoneNumber;
        } else {
            return generateUniquePhoneNumber(usedPhoneNumbers);
        }
    }

    public void initDb(){
        if(initDb){
            if(!productRepo.existsByName("Молоко")) {
                List<Product> products = new ArrayList<>();
                products.add(new Product("Молоко", 50.0, "Свежее коровье молоко"));
                products.add(new Product("Хлеб", 30.0, "Пшеничный хлеб"));
                products.add(new Product("Мука", 40.0, "Пшеничная мука высшего сорта"));
                products.add(new Product("Сахар", 25.0, "Белый сахар"));
                products.add(new Product("Макароны", 35.0, "Макароны из твердых сортов пшеницы"));
                products.add(new Product("Яйца", 70.0, "Куриные яйца"));
                products.add(new Product("Мясо", 150.0, "Свежее мясо говядины"));
                products.add(new Product("Рыба", 120.0, "Свежая рыба лосось"));
                products.add(new Product("Овощи", 60.0, "Свежие овощи (помидоры, огурцы, морковь)"));
                products.add(new Product("Фрукты", 80.0, "Свежие фрукты (яблоки, бананы, апельсины)"));
                products.add(new Product("Консервы", 45.0, "Мясные консервы"));
                products.add(new Product("Масло", 90.0, "Растительное масло"));
                products.add(new Product("Чай", 60.0, "Черный чай"));
                products.add(new Product("Кофе", 100.0, "Молотый кофе"));
                products.add(new Product("Сок", 65.0, "Фруктовый сок"));
                products.add(new Product("Вода", 20.0, "Питьевая вода"));
                products.add(new Product("Сыр", 110.0, "Твердый сыр"));
                products.add(new Product("Колбаса", 85.0, "Колбаса вареная"));
                products.add(new Product("Йогурт", 40.0, "Натуральный йогурт"));
                products.add(new Product("Кефир", 30.0, "Простокваша"));
                productRepo.saveAll(products);
            }
            if(!roleService.existByRoleName("ROLE_USER")){
                List<Role> roles = new ArrayList<>();
                roles.add(new Role("ROLE_USER"));
                roles.add(new Role("ROLE_ADMIN"));
                roleService.saveAll(roles);
            }
        }
    }

}
