import java.util.*;

public class Main {

    static class Command {
        int existing;
        int newCube;
        String direction;

        Command(int existing, int newCube, String direction) {
            this.existing = existing;
            this.newCube = newCube;
            this.direction = direction;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        List<Command> commands = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int existing = sc.nextInt();
            int newCube = sc.nextInt();
            String direction = sc.next();

            commands.add(new Command(existing, newCube, direction));
        }

        int target = sc.nextInt();

        
        commands.sort((a, b) -> {
            if (a.existing != b.existing) {
                return Integer.compare(a.existing, b.existing);
            }
            return Integer.compare(a.newCube, b.newCube);
        });

        
        Map<Integer, int[]> position = new HashMap<>();

        
        Map<String, Integer> grid = new HashMap<>();

        
        if (!commands.isEmpty()) {
            int firstCube = commands.get(0).existing;
            position.put(firstCube, new int[]{0, 0});
            grid.put("0,0", firstCube);
        }

        for (Command cmd : commands) {

            
            if (!position.containsKey(cmd.existing)) {
                continue;
            }

            int[] p = position.get(cmd.existing);

            int x = p[0];
            int y = p[1];

            int nx = x;
            int ny = y;

            switch (cmd.direction.toLowerCase()) {
                case "top":
                    ny++;
                    break;

                case "down":
                    ny--;
                    break;

                case "left":
                    nx--;
                    break;

                case "right":
                    nx++;
                    break;
            }

            String newKey = nx + "," + ny;

            
            if (grid.containsKey(newKey)) {
                int oldCube = grid.get(newKey);
                position.remove(oldCube);
            }

            
            if (position.containsKey(cmd.newCube)) {
                int[] oldPos = position.get(cmd.newCube);
                grid.remove(oldPos[0] + "," + oldPos[1]);
            }

            
            position.put(cmd.newCube, new int[]{nx, ny});
            grid.put(newKey, cmd.newCube);
        }

        
        if (!position.containsKey(target)) {
            System.out.println("-1 -1 -1 -1");
            return;
        }

        int[] p = position.get(target);

        int x = p[0];
        int y = p[1];

        
        int up = getCube(grid, x, y + 1);
        int down = getCube(grid, x, y - 1);
        int left = getCube(grid, x - 1, y);
        int right = getCube(grid, x + 1, y);

        System.out.println(up + " " + down + " " + left + " " + right);
    }

    static int getCube(Map<String, Integer> grid, int x, int y) {
        return grid.getOrDefault(x + "," + y, -1);
    }
}