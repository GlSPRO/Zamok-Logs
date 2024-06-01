import org.example.Log;
import java.io.IOException;
import java.util.Random;

public class Bunker {
    public int[][] layout;
    public int[] supplies;

    public Bunker(int rows, int cols) {
        this.layout = this.generateRandomLayout(rows, cols);
        this.supplies = new int[3];
    }

    static Log myLogBunker;
    static {
        try {
            myLogBunker = new Log("myLogBunker.log", "Bunker");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int[][] generateRandomLayout(int rows, int cols) {
        int[][] layout = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) {
                    layout[i][j] = 1;
                }
            }
        }
        return layout;
    }

    public void draw() {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {
                if (layout[i][j] == 1) {
                    System.out.print("# ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
    boolean isDestroyed;
    public void destroyBunker() {
        try {
            Random random = new Random();
            int symbolsToChange = (int) (layout.length * layout[0].length * 0.1);
            int changedSymbols = 0;
            // Меняем символы на пробелы
            while (changedSymbols < symbolsToChange) {
                int randomRow = random.nextInt(layout.length - 2);
                int randomCol = random.nextInt(layout[0].length - 2);
                if (layout[randomRow][randomCol] == 1) {
                    layout[randomRow][randomCol] = 0;
                    changedSymbols++;
                }
            }


            boolean isBunkerDestroyed = true;
            for (int i = 0; i < layout.length; i++) {
                for (int j = 0; j < layout[0].length; j++) {
                    if (layout[i][j] == 1) {
                        isBunkerDestroyed = false;
                        break;
                    }
                }
                if (!isBunkerDestroyed) {
                    break;
                }
            }

            if (isBunkerDestroyed) {
                isDestroyed = true;
                myLogBunker.logger.severe("Бункер разрушен полностью.");
            } else {
                myLogBunker.logger.info("Бункер частично разрушен.");
            }
        } catch (IllegalStateException e) {
            myLogBunker.logger.severe("Ошибка: " + e.getMessage());
        }
    }

    public void restoreBunker() {
        for (int i = 0; i < layout.length; ++i) {
            for (int j = 0; j < layout[0].length; ++j) {
                if (i == 0 || j == 0 || i == layout.length - 1 || j == layout[0].length - 1) {
                    this.layout[i][j] = 1;
                }
            }
        }
    }

    public int howMuchDays(int[] supplies, int consumptionPerDay) {
        int totalSupplies = 0;
        int[] var4 = supplies;
        int var5 = supplies.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            int supply = var4[var6];
            totalSupplies += supply;
        }
        for (int i = 0; i < supplies.length; i++) {
            supplies[i] -= totalSupplies / supplies.length;
        }
        return totalSupplies / consumptionPerDay;
    }

    public void restockSupplies(int[] restockAmounts) {
        for(int i = 0; i < this.supplies.length && i < restockAmounts.length; ++i) {
            int[] var10000 = this.supplies;
            var10000[i] += restockAmounts[i];
        }
    }

    public String checkBunkerStatus() {
        boolean isIntact = this.isBunkerIntact();
        return isIntact ?  "Бункер поврежден" :"Бункер не поврежден";
    }

    private boolean isBunkerIntact() {
        for(int i = 0; i < this.layout.length; ++i) {
            for(int j = 0; j < this.layout[0].length; ++j) {
                if (this.layout[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void populateBunker(int numPeople) {
        int maxPeople = layout.length * layout[0].length;
        if (numPeople < 0 || numPeople > maxPeople) {
            myLogBunker.logger.severe("Недопустимое количество людей для заполнения бункера.");
        }
        else {
            int currentPopulation = 0;
            Random random = new Random();

            while (currentPopulation < numPeople) {
                int row = random.nextInt(layout.length);
                int col = random.nextInt(layout[0].length);

                if (layout[row][col] == 1) {
                    layout[row][col] = 2;
                    currentPopulation++;
                }
            }
        }
    }
}

