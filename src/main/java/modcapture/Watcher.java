/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modcapture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 107310
 */
public class Watcher extends Thread{
    File file;
    
    Watcher(File a) {
        file=a;
    }
    
     @Override
     public void run() {
     //file.deleteOnExit();    
     File file2= new File("D:\\Screenshots\\0\\LIVE\\live.jpg");
    // try {
    // FileChannel src = new FileInputStream(file).getChannel();
    // FileChannel dest = new FileOutputStream(file2).getChannel();
     //dest.transferFrom(src, 0, src.size());
    // }
     //catch (IOException e) { }
     // file.renameTo(file2);
     }
   public static BasicFileAttributes awaitFile(Path target, long timeout, File file) 
    throws IOException, InterruptedException
{
    final Path name = target.getFileName();
    final Path targetDir = target.getParent();

    // Se il path esiste esci prima
    try {
        return Files.readAttributes(target, BasicFileAttributes.class);
    } catch (NoSuchFileException ex) {}

    final WatchService watchService = FileSystems.getDefault().newWatchService();
    try {
        final WatchKey watchKey = targetDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        // The file could have been created in the window between Files.readAttributes and Path.register
        try {
            System.out.println("entering watcher");
            //Arrays.stream(new File("D:\\Screenshots\\0\\LIVE\\").listFiles()).forEach(File::delete);
            File file2= new File("D:\\Screenshots\\0\\LIVE\\live.jpg");
            file2.delete();
           if ( file.renameTo(file2)) {
               System.out.println("cambiato");
               return Files.readAttributes(target, BasicFileAttributes.class);
                }
           else System.out.println("nnon è stato possibile cambiare");
            
        } catch (NoSuchFileException ex) {}
        // The file is absent: watch events in parent directory 
        WatchKey watchKey1 = null;
        boolean valid = true;
        do {
            long t0 = System.currentTimeMillis();
            watchKey1 = watchService.poll(timeout, TimeUnit.MILLISECONDS);
            if (watchKey1 == null) {
                return null; // timed out
            }
            // Examine events associated with key
            for (WatchEvent<?> event: watchKey1.pollEvents()) {
                Path path1 = (Path) event.context();
                if (path1.getFileName().equals(name)) {
                    return Files.readAttributes(target, BasicFileAttributes.class);
                }
            }
            // Did not receive an interesting event; re-register key to queue
            long elapsed = System.currentTimeMillis() - t0;
            timeout = elapsed < timeout? (timeout - elapsed) : 0L;
            valid = watchKey1.reset();
        } while (valid);
    } finally {
        watchService.close();
    }

    return null;
}
     
     
     
     
     
     
     
     
     
     
}
