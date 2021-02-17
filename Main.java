import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    final static String savedGamesPath = "/Users/wisnap/Games/savegames";
    final static String zipPath = "/Users/wisnap/Games/savegames/zip.zip";
    public static List<String> list = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        GameProgress[] savedGames = {
                new GameProgress(50, 3, 55, 4.5),
                new GameProgress(43, 7, 55, 4.9),
                new GameProgress(98, 4, 78, 3.5),
        };
        for (int i = 0; i < savedGames.length; i++) {
            String fileName = "savedGame " + i;
            saveGame(savedGamesPath + "/" + fileName, savedGames[i]);
            list.add(savedGamesPath + "/" + fileName);
        }
        zipFiles(zipPath, list);
        for (String s : list) {
            File file = new File(s);
            if (file.delete()) {
                System.out.println("Файл удален");
            } else {
                System.out.println("Файла не обнаружено");
            }
        }
    }

    public static void saveGame(String path, GameProgress gp) throws IOException {
        FileOutputStream fs = new FileOutputStream(path);
        try (ObjectOutputStream os = new ObjectOutputStream(fs);) {
            os.writeObject(gp);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> list) throws IOException {
        {
            try (ZipOutputStream zos = new ZipOutputStream(
                    new FileOutputStream(path));
                 FileInputStream fis = new FileInputStream(path);
            ) {
                for (String s : list) {
                    ZipEntry entry = new ZipEntry(s);
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                }
            }
            catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
           }
       }
}
