package com.ccs.testapp;

public class Data {
        private String number;
        private String amount;
        private String price;

        public Data(String number, String amount,  String price) {
            this.number = number;
            this.amount = amount;
            this.price = price;
        }



        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
}
