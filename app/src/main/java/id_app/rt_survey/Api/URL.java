package id_app.rt_survey.Api;

/**
 * Created by Carlos_Lopez on 3/12/16.
 */
public enum  URL  {

    LIST(0,"api/mobile/order/getbyuser/"),
    LOGIN(1,"token");

    //Method Type Volley  GET=0,POST=1,PUT=2,DELETE=3

    private final String MAIN_URL="http://190.52.97.182/service/";
    private Integer mTYPE;
    private String  mFINAL;

    URL(Integer TYPE, String FINAL) {
        this.mTYPE=TYPE;
        this.mFINAL=(MAIN_URL+FINAL);
    }

    public String getURL() {
        return mFINAL;
    }

    public Integer getRequestType() {
        return mTYPE;
    }

}
