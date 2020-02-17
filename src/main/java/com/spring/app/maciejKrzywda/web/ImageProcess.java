package com.spring.app.maciejKrzywda.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.NOT_FOUND)
class notFound extends RuntimeException {
    public notFound(String exception) {
        super(exception);
    }
}

public class ImageProcess {

    private Map<Integer, BufferedImage> map = new HashMap<>();
    private int counter = 0;


    void deleteImage(int id) throws notFound {
        if (!map.containsKey(id) || map.get(id) == null) {
            throw new notFound("Image not found !");
        }

        map.put(id, null);
    }

    int setImage(ServletInputStream inputStream) throws IOException {
        InputStream imageStream = new BufferedInputStream(inputStream);
        BufferedImage bufferedImage = ImageIO.read(imageStream);
        BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        bufferedImage1.createGraphics().drawImage(bufferedImage, 0, 0, null);

        map.put(counter, bufferedImage1);
        counter++;
        return counter - 1;
    }

    JSONObject getImageSize(int id) throws notFound, JSONException {
        if (!map.containsKey(id) || map.get(id) == null)
            throw new notFound("Image not found !");
        JSONObject sizeData = new JSONObject();

        sizeData.put("Width", map.get(id).getWidth());
        sizeData.put("Height", map.get(id).getHeight());

        return sizeData;
    }


    byte[] getSubImage(int id, int x, int y, int w, int h) throws IOException {
        if (!map.containsKey(id) || map.get(id) == null) {
            throw new notFound("Image not found !");
        }

        if ((x + w) > map.get(id).getWidth() || (y + h) > map.get(id).getHeight()) {
            throw new IOException("Out of size !");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(map.get(id).getSubimage(x, y, w, h), "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();

        byte[] bytesImage = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        return bytesImage;
    }

    JSONObject getHistogramImage(int id) throws JSONException {
        if (!map.containsKey(id) || map.get(id) == null) {
            throw new notFound("Image not found !");
        }

        JSONObject histrogramData = new JSONObject();
        int blackColour = 0;
        int whiteColour = 0;
        for (int i = 0; i < map.get(id).getWidth(); i++) {
            for (int j = 0; j < map.get(id).getHeight(); j++) {
                if (map.get(id).getRGB(i, j) == -1) {
                    whiteColour++;
                } else {
                    blackColour++;
                }
            }
        }

        histrogramData.put("Black", blackColour);
        histrogramData.put("White", whiteColour);

        return histrogramData;
    }
}
