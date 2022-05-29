package fpt.edu.capstone.common;

import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {
    public ResponseData readCV(){
        ITesseract image = new Tesseract();
        try {
            String str = image.doOCR(new File("E:\\Do An 2022\\Study Spring Boot\\testCV\\CV2.pdf"));
           return  new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), str);
        }catch (TesseractException e){
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "");
        }
    }
}
