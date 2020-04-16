package com.god2dog.addresspickerview;

import java.util.List;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/16
 * 描述：CommonWidget
 */
public class AddressModel {
    private String name;
    private int id;
    private String value;
    private boolean status;
    private List<CityModel> cityModels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CityModel> getCityModels() {
        return cityModels;
    }

    public void setCityModels(List<CityModel> cityModels) {
        this.cityModels = cityModels;
    }

    public static class CityModel{
        private String name;
        private int id;
        private String value;
        private boolean status;
        private List<AreaModel> areaModels;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public List<AreaModel> getAreaModels() {
            return areaModels;
        }

        public void setAreaModels(List<AreaModel> areaModels) {
            this.areaModels = areaModels;
        }

       public static class AreaModel{
            private String name;
            private int id;
            private String value;
            private boolean status;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }
    }


}
