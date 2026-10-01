package org.sadovodBase;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите задачу:");
        System.out.println("1 - Следующая большая цена");
        System.out.println("2 - Кратчайший путь на карте");
        System.out.println("3 - Проверка цикла");
        System.out.println("4 - Максимальный прямоугольник");
        System.out.println("5 - Карта с ключами и дверями");
        System.out.println("6 - Повторяющееся число");
        System.out.print("Введите номер задачи: ");

        int task = scanner.nextInt();

        switch (task) {
            case 1:
                task1(scanner);
                break;
            case 2:
                task2(scanner);
                break;
            case 3:
                task3(scanner);
                break;
            case 4:
                task4(scanner);
                break;
            case 5:
                task5(scanner);
                break;
            case 6:
                task6(scanner);
                break;
            default:
                System.out.println("Такой задачи нет");
        }
    }

    static void task1(Scanner scanner) {
        System.out.print("Введите количество дней n: ");
        int n = scanner.nextInt();

        int[] prices = new int[n];

        System.out.println("Введите цены:");
        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }

        int[] answer = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            while (top >= 0 && prices[i] > prices[stack[top]]) {
                int oldIndex = stack[top--];
                answer[oldIndex] = i - oldIndex;
            }

            stack[++top] = i;
        }

        for (int i = 0; i < n; i++) {
            System.out.print(answer[i] + " ");
        }

        System.out.println();
    }

    static void task2(Scanner scanner) {
        System.out.print("Введите n и m: ");

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        char[][] map = new char[n][m];

        int startRow = -1;
        int startCol = -1;

        System.out.println("Введите карту:");

        for (int i = 0; i < n; i++) {
            String line = scanner.next();

            for (int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j);

                if (map[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        int[][] distance = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(distance[i], -1);
        }

        int[] queueRow = new int[n * m];
        int[] queueCol = new int[n * m];

        int head = 0;
        int tail = 0;

        queueRow[tail] = startRow;
        queueCol[tail] = startCol;
        tail++;

        distance[startRow][startCol] = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (head < tail) {
            int row = queueRow[head];
            int col = queueCol[head];
            head++;

            if (map[row][col] == 'T') {
                System.out.println(distance[row][col]);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = row + dr[i];
                int newCol = col + dc[i];

                if (newRow < 0 || newRow >= n ||
                        newCol < 0 || newCol >= m) {
                    continue;
                }

                if (map[newRow][newCol] == '#') {
                    continue;
                }

                if (distance[newRow][newCol] != -1) {
                    continue;
                }

                distance[newRow][newCol] = distance[row][col] + 1;

                queueRow[tail] = newRow;
                queueCol[tail] = newCol;
                tail++;
            }
        }

        System.out.println(-1);
    }

    static void task3(Scanner scanner) {
        System.out.print("Введите n и start: ");

        int n = scanner.nextInt();
        int start = scanner.nextInt();

        int[] next = new int[n + 1];

        System.out.println("Введите next:");

        for (int i = 1; i <= n; i++) {
            next[i] = scanner.nextInt();
        }

        int slow = start;
        int fast = start;

        while (true) {
            slow = getNext(next, slow);
            fast = getNext(next, fast);

            if (fast == -1) {
                System.out.println("NO");
                return;
            }

            fast = getNext(next, fast);

            if (slow == -1 || fast == -1) {
                System.out.println("NO");
                return;
            }

            if (slow == fast) {
                System.out.println("YES");
                return;
            }
        }
    }

    static int getNext(int[] next, int node) {
        if (node == -1) {
            return -1;
        }

        return next[node];
    }

    static void task4(Scanner scanner) {
        System.out.print("Введите n и m: ");

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] heights = new int[m];
        long maxArea = 0;

        System.out.println("Введите матрицу:");

        for (int i = 0; i < n; i++) {
            String line = scanner.next();

            for (int j = 0; j < m; j++) {
                if (line.charAt(j) == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0;
                }
            }

            long area = maxRectangle(heights);

            if (area > maxArea) {
                maxArea = area;
            }
        }

        System.out.println(maxArea);
    }

    static long maxRectangle(int[] heights) {
        int n = heights.length;

        int[] stack = new int[n];
        int top = -1;

        long maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight;

            if (i == n) {
                currentHeight = 0;
            } else {
                currentHeight = heights[i];
            }

            while (top >= 0 && heights[stack[top]] > currentHeight) {
                int height = heights[stack[top--]];

                int width;

                if (top == -1) {
                    width = i;
                } else {
                    width = i - stack[top] - 1;
                }

                long area = (long) height * width;

                if (area > maxArea) {
                    maxArea = area;
                }
            }

            if (i < n) {
                stack[++top] = i;
            }
        }

        return maxArea;
    }

    static void task5(Scanner scanner) {
        System.out.print("Введите n и m: ");

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        char[][] map = new char[n][m];

        int startRow = -1;
        int startCol = -1;

        System.out.println("Введите карту:");

        for (int i = 0; i < n; i++) {
            String line = scanner.next();

            for (int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j);

                if (map[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        int keyStates = 1 << 10;

        boolean[][][] visited = new boolean[n][m][keyStates];

        int maxStates = n * m * keyStates;

        int[] queueRow = new int[maxStates];
        int[] queueCol = new int[maxStates];
        int[] queueKeys = new int[maxStates];
        int[] queueDistance = new int[maxStates];

        int head = 0;
        int tail = 0;

        queueRow[tail] = startRow;
        queueCol[tail] = startCol;
        queueKeys[tail] = 0;
        queueDistance[tail] = 0;
        tail++;

        visited[startRow][startCol][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (head < tail) {
            int row = queueRow[head];
            int col = queueCol[head];
            int keys = queueKeys[head];
            int distance = queueDistance[head];

            head++;

            if (map[row][col] == 'T') {
                System.out.println(distance);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = row + dr[i];
                int newCol = col + dc[i];

                if (newRow < 0 || newRow >= n ||
                        newCol < 0 || newCol >= m) {
                    continue;
                }

                char cell = map[newRow][newCol];

                if (cell == '#') {
                    continue;
                }

                int newKeys = keys;

                if (cell >= 'a' && cell <= 'j') {
                    int keyNumber = cell - 'a';
                    newKeys = keys | (1 << keyNumber);
                }

                if (cell >= 'A' && cell <= 'J') {
                    int doorNumber = cell - 'A';

                    boolean hasKey =
                            (keys & (1 << doorNumber)) != 0;

                    if (!hasKey) {
                        continue;
                    }
                }

                if (visited[newRow][newCol][newKeys]) {
                    continue;
                }

                visited[newRow][newCol][newKeys] = true;

                queueRow[tail] = newRow;
                queueCol[tail] = newCol;
                queueKeys[tail] = newKeys;
                queueDistance[tail] = distance + 1;

                tail++;
            }
        }

        System.out.println(-1);
    }

    static void task6(Scanner scanner) {
        System.out.print("Введите n: ");

        int n = scanner.nextInt();

        int[] nums = new int[n + 1];

        System.out.println("Введите " + (n + 1) + " чисел:");

        for (int i = 0; i <= n; i++) {
            nums[i] = scanner.nextInt();
        }

        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        System.out.println(slow);
    }
}