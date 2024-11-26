import java.util.*;

public class PlantWateringReminder {
    private static List<User> users = new ArrayList<>();
    private static Map<User, List<Plant>> userPlants = new HashMap<>();
    private static Timer timer = new Timer();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("1. Register a user");
            System.out.println("2. Register a plant");
            System.out.println("3. Set watering reminder");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    registerPlant();
                    break;
                case 3:
                    setWateringReminder();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        timer.cancel();
        System.out.println("Plant watering reminder stopped.");
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        User user = new User(username, email);
        users.add(user);
        userPlants.put(user, new ArrayList<>());
        System.out.println("User registered successfully.");
    }

    private static void registerPlant() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        User user = findUserByUsername(username);

        if (user == null) {
            System.out.println("User not found. Please register the user first.");
            return;
        }

        System.out.print("Enter plant name: ");
        String plantName = scanner.nextLine();
        System.out.print("Enter watering interval (in hours): ");
        int interval = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Plant plant = new Plant(plantName, interval);
        userPlants.get(user).add(plant);
        System.out.println("Plant registered successfully.");
    }

    private static void setWateringReminder() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        User user = findUserByUsername(username);

        if (user == null) {
            System.out.println("User not found. Please register the user first.");
            return;
        }

        List<Plant> plants = userPlants.get(user);
        if (plants.isEmpty()) {
            System.out.println("No plants registered for this user.");
            return;
        }

        System.out.println("Choose a plant to set a reminder for:");
        for (int i = 0; i < plants.size(); i++) {
            System.out.println((i + 1) + ". " + plants.get(i).getName());
        }
        int plantChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (plantChoice < 1 || plantChoice > plants.size()) {
            System.out.println("Invalid plant choice. Please try again.");
            return;
        }

        Plant selectedPlant = plants.get(plantChoice - 1);

        System.out.println("Choose a reminder option:");
        System.out.println("1. In 15 minutes");
        System.out.println("2. In 1 hour");
        System.out.println("3. At a specific time");
        int reminderChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (reminderChoice) {
            case 1:
                scheduleReminder(selectedPlant, 15 * 60 * 1000); // 15 minutes
                break;
            case 2:
                scheduleReminder(selectedPlant, 60 * 60 * 1000); // 1 hour
                break;
            case 3:
                System.out.print("Enter the number of hours from now: ");
                int hours = scanner.nextInt();
                scanner.nextLine(); // consume newline
                scheduleReminder(selectedPlant, hours * 60 * 60 * 1000); // specific time
                break;
            default:
                System.out.println("Invalid reminder option. Please try again.");
        }
    }

    private static void scheduleReminder(Plant plant, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Reminder: It's time to water your " + plant.getName() + "!");
            }
        }, delay);
        System.out.println("Reminder set successfully.");
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
