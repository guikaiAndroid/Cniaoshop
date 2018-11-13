package com.guikai.cniaoshop.city;

import com.guikai.cniaoshop.city.model.CityModel;
import com.guikai.cniaoshop.city.model.DistrictModel;
import com.guikai.cniaoshop.city.model.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParserHandler extends DefaultHandler {
    /**
     * 存储所有的解析对象
     */
    private List<ProvinceModel> provincelList = new ArrayList<ProvinceModel>();

    public XmlParserHandler() {

    }

    public List<ProvinceModel> getDataList() {
        return provincelList;
    }

    @Override
    public void startDocument() throws SAXException {
        //读到第一个开始标签的时候，会触发这个方法
    }

    ProvinceModel provinceModel = new ProvinceModel();
    CityModel cityModel = new CityModel();
    DistrictModel districtModel = new DistrictModel();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //遇到开始标签的时候 调用这个方法
        if (qName.equals("province")) {
            provinceModel = new ProvinceModel();
            provinceModel.setName(attributes.getValue(0));
            provinceModel.setCityList(new ArrayList<CityModel>());
        } else if (qName.equals("city")) {
            cityModel = new CityModel();
            cityModel.setName(attributes.getValue(0));
            cityModel.setDistrictList(new ArrayList<DistrictModel>());
        } else if (qName.equals("district")) {
            districtModel = new DistrictModel();
            districtModel.setName(attributes.getValue(0));
            districtModel.setZipcode(attributes.getValue(1));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //遇到结束标记的时候，会调用这个方法
        if (qName.equals("district")) {
            cityModel.getDistrictList().add(districtModel);
        } else if (qName.equals("city")) {
            provinceModel.getCityList().add(cityModel);
        } else if (qName.equals("province")) {
            provincelList.add(provinceModel);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }
}
