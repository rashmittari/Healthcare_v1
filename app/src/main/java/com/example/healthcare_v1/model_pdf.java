package com.example.healthcare_v1;

public class model_pdf {
    String FileName,URL;

    public model_pdf(){}

    public model_pdf(String fileName, String URL) {
        FileName = fileName;
        this.URL = URL;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
