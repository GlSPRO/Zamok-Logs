import java.io.IOException;
import java.util.Scanner;
import org.example.Log;

public class Main {
    static Log myLogMain;
    static {
        try {
            myLogMain = new Log("myLogMain.log", "Main");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите ширину бункера (от 3 до 15):");
            String rowsString = scanner.nextLine();
            int rows;

            try {
                rows = Integer.parseInt(rowsString);
                if (rows < 3 || rows > 15) {
                    myLogMain.logger.severe("Введите правильную ширину бункера (от 3 до 15)");
                    continue;
                }
            } catch (NumberFormatException e) {
                myLogMain.logger.severe("Введите правильную ширину бункера (от 3 до 15). Пожалуйста..");
                continue;
            }

            System.out.println("Введите длину бункера (от 3 до 20):");
            String colsString = scanner.nextLine();
            int cols;

            try {
                cols = Integer.parseInt(colsString);
                if (cols < 3 || cols > 20) {
                    myLogMain.logger.severe("Введите правильную длину бункера (от 3 до 20)");
                    continue;
                }
            } catch (NumberFormatException e) {
                myLogMain.logger.severe("Введите правильную длину бункера (от 3 до 20). Пожалуйста..");
                continue;
            }

            Bunker bunker = new Bunker(rows, cols);

            System.out.println("Бункер размера:" + rows + " * " + cols);
            bunker.draw();

            while (true){
                System.out.println("""
                  Выберите, что хотите сделать с бункером:
                   1. Разрушить бункер
                   2. Восстановить бункер
                   3. Проверить запасы продовольствия
                   4. Обновить запасы продовольствия
                   5. Проверить состояние бункера
                   6. Добавить людей в бункер
                   7. Выход""");

                if (!scanner.hasNextInt()) {
                    myLogMain.logger.severe("Введите корректное значение.");
                    scanner.nextLine(); // Consume the invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Разрушенный бункер:");
                        bunker.destroyBunker();
                        bunker.draw();
                        break;
                    case 2:
                        System.out.println("Восстановленный бункер:");
                        bunker.restoreBunker();
                        bunker.draw();
                        break;
                    case 3:
                        int[] supplies = new int[]{100, 50, 75};
                        int consumptionPerDay = 10;
                        int remainingDays = bunker.howMuchDays(supplies, consumptionPerDay);
                        myLogMain.logger.info("Запасы продержатся еще " + remainingDays + " дней");
                        break;
                    case 4:
                        int[] restockAmounts = new int[]{20, 30, 10};
                        bunker.restockSupplies(restockAmounts);
                        myLogMain.logger.info("Запасы обновлены");
                        break;
                    case 5:
                        System.out.println("Состояние бункера: " + bunker.checkBunkerStatus());
                        break;
                    case 6:
                        System.out.println("Введите количество людей, которое вы хотите добавить в бункер:");
                        if (!scanner.hasNextInt()) {
                            myLogMain.logger.severe("Вы ввели не корректное количество людей.");
                            break;
                        }
                        int numPeople = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        bunker.populateBunker(numPeople);
                        break;
                    case 7:
                        System.out.println("Выход");
                        return;
                    default:
                        myLogMain.logger.severe("Выберите что-то из доступных действий!");
                        break;
                }
            }
        }
    }
}
