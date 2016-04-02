package com.friend_map.business_layer.image;

import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.FileTypes;
import com.friend_map.persistence_layer.entities.image.UserImage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ImageUtils {

    FileOutputStream fileOutputStream;
    BufferedOutputStream bufferedOutputStream;

    /** СОХРАНЯЕТ ИЗОБРАЖЕНИЕ НА ДИСКЕ */
    public CommandStatus save(byte[] bytes, File file) {
        try {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
        return CommandStatus.OK;
    }

    /** ИЗМЕНЯЕТ РАЗМЕР ИЗОБРАЖЕНИЯ */
    @Async
    public void processImage(int maxWidth, int maxHeight, BufferedImage bi, File file, FileTypes fileType) throws ServletException {
        double max = 0;
        int size = 0;
        int ww = maxWidth - bi.getWidth();
        int hh = maxHeight - bi.getHeight();

        if (ww<0 || hh<0) {
            if(ww < hh) {
                max = maxWidth;
                size = bi.getWidth();
            } else {
                max = maxHeight;
                size = bi.getHeight();
            }
            if(size > 0 && size > max) {
                double trans=1.0/(size/max);

                AffineTransform tr = new AffineTransform();
                tr.scale(trans, trans);
                AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
                Double w = bi.getWidth() * trans;
                Double h = bi.getHeight() * trans;
                BufferedImage bi2 = new BufferedImage(w.intValue(), h.intValue(), bi.getType());
                op.filter(bi, bi2);
                try {
                    switch (fileType) {
                        case png:
                            ImageIO.write(bi2, "png", file);
                            break;
                        case jpeg:
                            ImageIO.write(bi2, "jpeg", file);
                            break;
                        case jpg:
                            ImageIO.write(bi2, "jpg", file);
                            break;
                    }
                    bi = null;
                    bi2 = null;
                    file = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** УДАЛЯЕТ ИЗОБРАЖЕНИЕ С ДИСКА */
    public CommandStatus delete(File file) {
        try {
            if(!file.exists()) {
                return CommandStatus.OK;
            }
            if(file.isDirectory()) {
                for(File f : file.listFiles()) {
                    delete(f);
                }
                file.delete();
            }
            else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
        return CommandStatus.OK;
    }

    /** УДАЛЯЕТ ИЗОБРАЖЕНИИ */
    public CommandStatus deleteImages(UserImage image, String image_category) {
        try {
            File dir = new File(image_category + image.getFull_directory());
            if (dir.exists()) {
                return delete(dir);
            }
            return CommandStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommandStatus.ERROR;
    }

    /** ПОЛУЧИТЬ ТИП ИЗОБРАЖЕНИЯ */
    public FileTypes getType(String type) {
        String name = type.substring(type.length() - 3, type.length());
        switch (name.toLowerCase()) {
            case "jpg":
                return FileTypes.jpg;
            case "png":
                return FileTypes.png;
            case "gif":
                return FileTypes.gif;
            case "peg":
                if (type.substring(type.length() - 4, type.length()).equalsIgnoreCase("jpeg")) {
                    return FileTypes.jpeg;
                }
            default:
                return FileTypes.OTHER;
        }
    }

    public ImageUtils() {
    }
}
