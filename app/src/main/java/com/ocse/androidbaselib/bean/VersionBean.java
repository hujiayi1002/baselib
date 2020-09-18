package com.ocse.androidbaselib.bean;

public class VersionBean {

    /**
     * status : true
     * message :
     * data : {"THR_APPVERSION_ID":105714,"VERSIONCODE":1,"VERSIONNAME":"1.0.0","LJ":"5f51e79a6f50be1ba4a4b5b4"}
     */

    private boolean status;
    private String message;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * THR_APPVERSION_ID : 105714
         * VERSIONCODE : 1
         * VERSIONNAME : 1.0.0
         * LJ : 5f51e79a6f50be1ba4a4b5b4
         */

        private int THR_APPVERSION_ID;
        private int VERSIONCODE;
        private String VERSIONNAME;
        private String LJ;

        public int getTHR_APPVERSION_ID() {
            return THR_APPVERSION_ID;
        }

        public void setTHR_APPVERSION_ID(int THR_APPVERSION_ID) {
            this.THR_APPVERSION_ID = THR_APPVERSION_ID;
        }

        public int getVERSIONCODE() {
            return VERSIONCODE;
        }

        public void setVERSIONCODE(int VERSIONCODE) {
            this.VERSIONCODE = VERSIONCODE;
        }

        public String getVERSIONNAME() {
            return VERSIONNAME;
        }

        public void setVERSIONNAME(String VERSIONNAME) {
            this.VERSIONNAME = VERSIONNAME;
        }

        public String getLJ() {
            return LJ;
        }

        public void setLJ(String LJ) {
            this.LJ = LJ;
        }
    }
}
