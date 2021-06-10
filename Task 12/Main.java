package serializable_task12;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws Exception {

        //Дата для логов файла temp.txt
        Date currentDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyy hh:mm:ss a");

        stringBuilder.append(formatDate.format(currentDate) + "\n");

        //В папке Games создаем несколько директорий: src, res, savegames, temp.
        File src = new File("D:\\Games\\src");
        File res = new File("D:\\Games\\res");
        File savegames = new File("D:\\Games\\savegames");
        File temp = new File("D:\\Games\\temp");

        //В каталоге src создаем две директории: main, test.
        File main = new File("D:\\Games\\src\\main");
        File test = new File("D:\\Games\\src\\test");

        //В подкаталоге main создаем два файла: Main.java, Utils.java.
        File mainJava = new File("D:\\Games\\src\\main\\Main.java");
        File utilsJava = new File("D:\\Games\\src\\main\\Utils.java");

        //В каталог res создаем три директории: drawables, vectors, icons.
        File drawables = new File("D:\\Games\\res\\drawables");
        File vectors = new File("D:\\Games\\res\\vectors");
        File icons = new File("D:\\Games\\res\\icons");

        //В директории temp создаем файл temp.txt.
        File tempFile = new File("D:\\Games\\temp\\temp.txt");
        try {
            temp.mkdir();
            tempFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Ошибка создания директория или файла temp:");
            System.out.println(e.getMessage());
        }

        //Создание директорий и файлов с записью в стрингбилдер :)
        try (FileWriter writer = new FileWriter(tempFile, true)) {
            makeDir(src);
            makeDir(res);
            makeDir(savegames);
            makeDir(main);
            makeDir(test);
            makeDir(drawables);
            makeDir(vectors);
            makeDir(icons);
            createFile(mainJava);
            createFile(utilsJava);
            writer.append(stringBuilder.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        GameProgress user1 = new GameProgress(100, 2, 10, 244.32);
        GameProgress user2 = new GameProgress(87, 10, 4, 255.51);
        GameProgress user3 = new GameProgress(84, 4, 24, 335.31);

        saveGame("D:\\Games\\savegames\\save1.dat", user1);
        saveGame("D:\\Games\\savegames\\save2.dat", user2);
        saveGame("D:\\Games\\savegames\\save3.dat", user3);

        List<String> files = Arrays.asList("D:\\Games\\savegames\\save1.dat",
                "D:\\Games\\savegames\\save2.dat",
                "D:\\Games\\savegames\\save3.dat");

        zipFiles("D:\\Games\\savegames\\saves.zip", files);
        deleteFile("D:\\Games\\savegames\\save1.dat");
        deleteFile("D:\\Games\\savegames\\save2.dat");
        deleteFile("D:\\Games\\savegames\\save3.dat");

    }

    public static void saveGame(String address, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(address);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile(String address) {
        File save = new File(address);
        save.delete();
    }

    public static void makeDir(File file) {
        if (file.mkdir()) {
            stringBuilder.append("Каталог " + file.getName() + " успешно создан!");
        } else {
            stringBuilder.append("Каталог " + file.getName() + " не создан!");
        }
        stringBuilder.append('\n');
    }

    public static void createFile(File file) throws Exception {
        if (file.createNewFile()) {
            stringBuilder.append("Файл " + file.getName() + " успешно создан!");
        } else {
            stringBuilder.append("Файл " + file.getName() + " не создан!");
        }
        stringBuilder.append('\n');
    }

    public static void zipFiles(String address, List<String> files) throws Exception {
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(address));
        for (int i = 0; i < files.size(); i++) {
            FileInputStream fis = new FileInputStream(files.get(i));
            ZipEntry entry = new ZipEntry("Save" + i + ".dat");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
            fis.close();
        }
        zout.close();
    }
}