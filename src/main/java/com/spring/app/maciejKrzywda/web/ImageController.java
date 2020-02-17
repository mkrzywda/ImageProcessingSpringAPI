package com.spring.app.maciejKrzywda.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
public class ImageController {
    private ImageProcess imageProcessorController = new ImageProcess();

    @RequestMapping(method = RequestMethod.POST, value = "/image") //mapuje adres URL i/lub metodę HTTP na metodę kontrolera
    public String setImage(HttpServletRequest requestEntity) throws IOException {
        Integer id = imageProcessorController.setImage(requestEntity.getInputStream());
        return "ID : " + id.toString();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/image/{id}")
    public String deleteImage(@PathVariable int id) {  //obsługuje zmienną przekazaną w postaci fragmentu adresu url
        imageProcessorController.deleteImage(id);
        return "Image " + id + " has been deleted";
    }

    @RequestMapping(value = "/image/{id}/crop/{x}/{y}/{w}/{h}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getGrayImage(@PathVariable int id, @PathVariable int x, @PathVariable int y,
                               @PathVariable int w, @PathVariable int h) throws Exception {
        return imageProcessorController.getSubImage(id, x, y, w, h);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image/{id}/size")
    public String getImageSize(@PathVariable int id) throws notFound, JSONException {
        JSONObject imageSize = imageProcessorController.getImageSize(id);
        return "Image " + id + " size : " + imageSize;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image/{id}/histogram")
    public String getHistogramImage(@PathVariable int id) throws JSONException {
        JSONObject histogramOfImage = imageProcessorController.getHistogramImage(id);
        return "Histogram of image {" + id + "} : " + histogramOfImage;
    }


}
