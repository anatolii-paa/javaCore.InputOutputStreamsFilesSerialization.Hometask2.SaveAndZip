import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(10, 100, 1, 10.100);
        GameProgress gameProgress2 = new GameProgress(20, 200, 2, 20.200);
        GameProgress gameProgress3 = new GameProgress(30, 300, 3, 30.300);

        String  path1 = "/Users/anatolii/Games/savegames/gameProgress1.dat",
                path2 = "/Users/anatolii/Games/savegames/gameProgress2.dat",
                path3 = "/Users/anatolii/Games/savegames/gameProgress3.dat",
                pathZip = "/Users/anatolii/Games/savegames/gameProgress.zip";

        saveGame(path1, gameProgress1);
        saveGame(path2, gameProgress2);
        saveGame(path3, gameProgress3);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(path1);
        arrayList.add(path2);
        arrayList.add(path3);

        zipFiles(pathZip, arrayList);

        File gameProgressFile1 = new File(path1),
             gameProgressFile2 = new File(path2),
             gameProgressFile3 = new File(path3);

        if (gameProgressFile1.delete())
            System.out.println("Файл gameProgress1 удален");
        if (gameProgressFile2.delete())
            System.out.println("Файл gameProgress2 удален");
        if (gameProgressFile3.delete())
            System.out.println("Файл gameProgress3 удален");
    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String path, List<String> arrayList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String arr : arrayList) {
                try (FileInputStream fis = new FileInputStream(arr)) {
                    ZipEntry entry = new ZipEntry(arr);
                    zout.putNextEntry(entry);
                    while (fis.available() > 0) {
                        zout.write(fis.read());
                    }
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}