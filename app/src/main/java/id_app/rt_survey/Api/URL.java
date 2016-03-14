package id_app.rt_survey.Api;

import android.net.Uri;

/**
 * Created by Carlos_Lopez on 3/12/16.
 */
public enum  URL  {

    LOGIN(1,"token","POST");


    private final String MAIN_URL="http://rtsurvey.golddata.net/service/";
    private Integer mTYPE;
    private String  mFINAL;
    private String  mNAME_TYPE;

    private URL(Integer TYPE,String FINAL,String NAME_TYPE) {
        this.mTYPE=TYPE;
        this.mFINAL=(MAIN_URL+FINAL);
        this.mNAME_TYPE=NAME_TYPE;
    }

    public String getURL() {
        return mFINAL;
    }

    public String getRequestName() {
        return mNAME_TYPE;
    }

    public Integer getRequestType() {
        return mTYPE;
    }
}
